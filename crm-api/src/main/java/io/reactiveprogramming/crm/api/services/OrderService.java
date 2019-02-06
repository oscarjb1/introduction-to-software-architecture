package io.reactiveprogramming.crm.api.services;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.reactiveprogramming.commons.exceptions.GenericServiceException;
import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
import io.reactiveprogramming.crm.api.dao.IOrderDAO;
import io.reactiveprogramming.crm.api.dao.IProductDAO;
import io.reactiveprogramming.crm.converters.SaleOrderConverter;
import io.reactiveprogramming.crm.dto.CardDTO;
import io.reactiveprogramming.crm.dto.NewOrderDTO;
import io.reactiveprogramming.crm.dto.NewOrderLineDTO;
import io.reactiveprogramming.crm.dto.OrderLineDTO;
import io.reactiveprogramming.crm.dto.SaleOrderDTO;
import io.reactiveprogramming.crm.entity.OrderLine;
import io.reactiveprogramming.crm.entity.OrderStatus;
import io.reactiveprogramming.crm.entity.Payment;
import io.reactiveprogramming.crm.entity.PaymentMethod;
import io.reactiveprogramming.crm.entity.Product;
import io.reactiveprogramming.crm.entity.SaleOrder;

@Service
public class OrderService {

	@Autowired
	private IOrderDAO orderDAO;
	
	@Autowired
	private IProductDAO productDAO;
	
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

	public SaleOrderDTO createOrder(NewOrderDTO order)throws ValidateServiceException, GenericServiceException {
		try {
			
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
				
				if(cvc == null || cvc.isEmpty() || cvc.length() < 3 || cvc.length() > 4) {
					throw new ValidateServiceException("CVC is required, is need 3 or 4 numbers");
				}
				if(expiry == null || expiry.isEmpty() || expiry.length() < 4 || expiry.length() > 4) {
					throw new ValidateServiceException("Expiry date id required, formato mm/yy");
				}
				if(name == null || name.isEmpty() || name.length() > 30) {
					throw new ValidateServiceException("Named is requiered, 30 characters max");
				}
				if(number == null || number.isEmpty() || number.length() < 15 || number.length() > 16) {
					throw new ValidateServiceException("Invalid credit card number, is need 15 or 16 numbers");
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
				//Valida if product exist
				Optional<Product> productOpt = productDAO.findById(lineDTO.getProductId());
				if(!productOpt.isPresent()) {
					throw new ValidateServiceException(String.format("Product %s not found", lineDTO.getProductId()));
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
			
			SaleOrderConverter orderConverter = new SaleOrderConverter();
			return orderConverter.toDTO(newSaleOrder);
		} catch(ValidateServiceException e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new GenericServiceException(e.getMessage(),e);
		}
	}
	
	public SaleOrderDTO findSaleOrderById(Long orderId)throws ValidateServiceException, GenericServiceException{
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
}
