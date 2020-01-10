package io.reactiveprogramming.crm.api.services;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import brave.Tracer;
import io.reactiveprogramming.commons.email.EmailDTO;
import io.reactiveprogramming.commons.exceptions.GenericServiceException;
import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.crm.api.dao.IOrderDAO;
import io.reactiveprogramming.crm.api.dao.IProductDAO;
import io.reactiveprogramming.crm.converters.SaleOrderConverter;
import io.reactiveprogramming.crm.dto.ApplyPaymentRequest;
import io.reactiveprogramming.crm.dto.CardDTO;
import io.reactiveprogramming.crm.dto.MessageDTO;
import io.reactiveprogramming.crm.dto.NewOrderDTO;
import io.reactiveprogramming.crm.dto.NewOrderLineDTO;
import io.reactiveprogramming.crm.dto.SaleOrderDTO;
import io.reactiveprogramming.crm.entity.OrderLine;
import io.reactiveprogramming.crm.entity.OrderStatus;
import io.reactiveprogramming.crm.entity.Payment;
import io.reactiveprogramming.crm.entity.PaymentMethod;
import io.reactiveprogramming.crm.entity.Product;
import io.reactiveprogramming.crm.entity.SaleOrder;
import io.reactiveprogramming.crm.rabbit.RabbitSender;

@Service
public class OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private Tracer tracer;

	@Autowired
	private IOrderDAO orderDAO;
	
	@Autowired
	private IProductDAO productDAO;
	
	@Autowired
	private RabbitSender sender;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<SaleOrderDTO> getAllOrders() throws GenericServiceException, ValidateServiceException{
		try {
			List<SaleOrder> orders = orderDAO.findAll();
			SaleOrderConverter orderConverter = new SaleOrderConverter();
			List<SaleOrderDTO> ordersDTO = orderConverter.toDtoList(orders);
			return ordersDTO;
		}catch (Exception e) {
			throw new GenericServiceException("Internal Server Error");
		}
	}
	
	private SaleOrderDTO queueOrder(NewOrderDTO order)throws ValidateServiceException, GenericServiceException{
		tracer.currentSpan().tag("queue.order", "Queue order from the customer " + order.getCustomerName());
		logger.error("Queue order from the customer " + order.getCustomerName());
		try {
			order.setRefNumber(UUID.randomUUID().toString());
			sender.send("newOrders", null, order);
			
			SaleOrderDTO response = new SaleOrderDTO();
			response.setQueued(true);
			response.setRefNumber(order.getRefNumber());
			
			EmailDTO mail = new EmailDTO();
			mail.setFrom("no-reply@crm.com");
			mail.setSubject("Hemos recibido tu pedido");
			mail.setTo(order.getCustomerEmail());
			mail.setMessage(String.format("Hola %s,<br> Hemos recibido tu pedido <strong>#%s</strong>, en este momento tu pedido está siendo procesado y una vez validado te contactaremos para darle segumiento a tu pedido",order.getCustomerName(), order.getRefNumber()));
			
			sender.send( "emails", null, mail); 
			
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage());
			tracer.currentSpan().tag("queue.order.error", "Error to queue a new order from the customer " + order.getCustomerName());
			throw new GenericServiceException(e.getMessage(),e);
		}
	}
	
	@HystrixCommand(
			fallbackMethod="queueOrder", 
			ignoreExceptions= {ValidateServiceException.class})
	public SaleOrderDTO createOrder(NewOrderDTO order) 
			throws ValidateServiceException, GenericServiceException {
		logger.info("New order request ==>");
		try {
			
			//if(order.getId() == null) {
			//	throw new GenericServiceException("Dummy error");
			//}
			if(order.getOrderLines() == null || order.getOrderLines().isEmpty()) {
				throw new ValidateServiceException("Need one or more order lines");
			}
			
			PaymentMethod paymentMethod = null;
			try {
				paymentMethod = PaymentMethod.valueOf(order.getPaymentMethod());
			} catch (Exception e) {
				throw new ValidateServiceException("Invalid payment menthod");
			}
			
			SaleOrder saleOrder = new SaleOrder();
			
			Payment payment = new Payment();
			payment.setPaymentMethod(paymentMethod);
			payment.setSaleOrder(saleOrder);
			saleOrder.setPayment(payment);
			
			if(paymentMethod == PaymentMethod.CREDIT_CARD) {
				CardDTO card = order.getCard();
				String cvc = card.getCvc();
				String name = card.getName();
				String number = card.getNumber();
				String expiry = card.getExpiry();
				
				if(cvc == null || cvc.isEmpty() 
						|| cvc.length() < 3 || cvc.length() > 4) {
					throw new ValidateServiceException(
							"CVC is required, is need 3 or 4 numbers");
				}
				if(expiry == null || expiry.isEmpty() 
						|| expiry.length() < 4 || expiry.length() > 4) {
					throw new ValidateServiceException(
							"Expiry date id required, formato mm/yy");
				}
				if(name == null || name.isEmpty() || name.length() > 30) {
					throw new ValidateServiceException(
							"Named is requiered, 30 characters max");
				}
				if(number == null || number.isEmpty() 
						|| number.length() < 15 || number.length() > 16) {
					throw new ValidateServiceException(
							"Invalid credit card number, is need 15 or 16 numbers");
				}
				
				payment.setPaydate(Calendar.getInstance());
				saleOrder.setStatus(OrderStatus.PAYED);
			}else {
				saleOrder.setStatus(OrderStatus.PENDING);
			}
			
			saleOrder.setCustomerEmail(order.getCustomerEmail());
			saleOrder.setCustomerName(order.getCustomerName());
			saleOrder.setRefNumber(UUID.randomUUID().toString());
			saleOrder.setRegistDate(Calendar.getInstance());
			
			Set<OrderLine> lines = new HashSet<>();
			for(NewOrderLineDTO lineDTO : order.getOrderLines()) {
				Optional<Product> productOpt = 
						productDAO.findById(lineDTO.getProductId());
				if(!productOpt.isPresent()) {
					throw new ValidateServiceException(
							String.format("Product %s not found", lineDTO.getProductId()));
				}
				Product product = productOpt.get();
				
				OrderLine line = new OrderLine();
				line.setProduct(product);
				line.setQuantity(lineDTO.getQuantity());
				line.setSaleOrder(saleOrder);
				
				lines.add(line);
			}
			
			saleOrder.setOrderLines(lines);
			
			SaleOrder newSaleOrder = orderDAO.save(saleOrder);
			
			logger.info("New order created ==>" + newSaleOrder.getId() + 
					", refNumber > " + newSaleOrder.getRefNumber());
			tracer.currentSpan().tag("order.new", newSaleOrder.getRefNumber());
			tracer.currentSpan().name(saleOrder.getRefNumber());
			
			SaleOrderConverter orderConverter = new SaleOrderConverter();
			SaleOrderDTO returnOrder = orderConverter.toDTO(newSaleOrder);
			
			EmailDTO mail = new EmailDTO();
			mail.setFrom("no-reply@crm.com");
			mail.setSubject("Hemos recibido tu pedido");
			mail.setTo(newSaleOrder.getCustomerEmail());
			if(paymentMethod==PaymentMethod.CREDIT_CARD) {
				mail.setMessage(String.format("Hola %s,<br> Hemos recibido tu pedido <strong>#%s</strong>, tambien hemos confirmado tu pago, por lo que estarás recibiendo tus productos muy pronto",newSaleOrder.getCustomerName(), newSaleOrder.getRefNumber()));
			}else {
				mail.setMessage(String.format("Hola %s,<br> Hemos recibido tu pedido <strong>#%s</strong>, debido a que has seleccionado la forma de pago por depósito bancario, esperaremos hasta tener confirmado el pago para enviar tus productos.",newSaleOrder.getCustomerName(), newSaleOrder.getRefNumber()));
			}
			
			sender.send( "emails", null, mail); 
			
			// Send Webhook notification
			try {
				if(paymentMethod==PaymentMethod.CREDIT_CARD) {
					MessageDTO message = new MessageDTO();
					message.setEventType(MessageDTO.EventType.NEW_SALES);
					message.setMessage(returnOrder); 
					WrapperResponse response = restTemplate.postForObject(
							"http://webhook/push", message, WrapperResponse.class);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				tracer.currentSpan().tag("webhook.fail", e.getMessage());
				System.out.println("No webhook service instance up!");
			}
			return returnOrder;
		} catch(ValidateServiceException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new GenericServiceException(e.getMessage(),e);
		}
	}
	
	public SaleOrderDTO findSaleOrderById(Long orderId)throws ValidateServiceException, 
	GenericServiceException{
		try {
			Optional<SaleOrder> orderOpt = orderDAO.findById(orderId);
			if(!orderOpt.isPresent()) {
				throw new ValidateServiceException("Order not found");
			}
			
			SaleOrder saleOrder = orderOpt.get();
			SaleOrderConverter converter = new SaleOrderConverter();
			return converter.toDTO(saleOrder);
			
		} catch(ValidateServiceException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new GenericServiceException(e.getMessage(), e);
		}
	}
	
	public void applyPayment(ApplyPaymentRequest request)throws ValidateServiceException, 
	GenericServiceException {
		try {
			tracer.currentSpan().tag("payment.new", request.getRefNumber());
			SaleOrder saleOrder = orderDAO.findByRefNumber(request.getRefNumber());
			if(saleOrder == null) {
				throw new ValidateServiceException("Order by ref number not found");
			}
			
			if(request.getAmmount() < saleOrder.getTotal() ) {
				throw new ValidateServiceException(
						"Payment amount not correspond to the total of the order");
			}
			
			
			if(saleOrder.getStatus() == OrderStatus.PAYED) {
				throw new ValidateServiceException("The order is already paid");
			}
			
			saleOrder.getPayment().setPaydate(Calendar.getInstance());
			saleOrder.setStatus(OrderStatus.PAYED);
			orderDAO.save(saleOrder);
			
			logger.info("new payment applayed > " + request.getRefNumber());
			tracer.currentSpan().tag("payment.process", "Payment applayed successfully");
			
			EmailDTO mail = new EmailDTO();
			mail.setFrom("no-reply@crm.com");
			mail.setSubject("Hemos recibido tu pedido");
			mail.setTo(saleOrder.getCustomerEmail());
			mail.setMessage(String.format("Hola %s,<br> Hemos recibido el pago de la orden No. <strong>#%s</strong>, en este momento estamos gestionando el envío de tu pedido para que lo recibas cuanto antes",saleOrder.getCustomerName(), saleOrder.getRefNumber()));
			
			sender.send(mail);
			
		} catch(ValidateServiceException e) {
			tracer.currentSpan().tag("payment.validate", e.getMessage());
			throw e;
		} catch (Exception e) {
			tracer.currentSpan().tag("payment.error", e.getMessage());
			throw new GenericServiceException(e.getMessage(), e);
		}
		
		
	}
}
