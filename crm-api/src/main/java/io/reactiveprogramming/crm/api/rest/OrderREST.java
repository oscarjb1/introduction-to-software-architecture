package io.reactiveprogramming.crm.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.commons.dto.LoginResponseDTO;
import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.crm.api.services.OrderService;
import io.reactiveprogramming.crm.dto.NewOrderDTO;
import io.reactiveprogramming.crm.dto.SaleOrderDTO;
import io.reactiveprogramming.crm.feign.clients.SecurityServiceFeignClient;

@RestController
@RequestMapping("orders")
public class OrderREST {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SecurityServiceFeignClient securityService;
	
	@RequestMapping(value = "{orderId}", method = RequestMethod.GET)
	public WrapperResponse<SaleOrderDTO> getOrder(@PathVariable("orderId") Long orderId) {
		try {
			SaleOrderDTO newOrder = orderService.findSaleOrderById(orderId);
			WrapperResponse response = new WrapperResponse(true, "success", newOrder);
			return response;
		} catch(ValidateServiceException e) {
			return new WrapperResponse(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return new WrapperResponse(false, "Internal Server Error");
		}
	}
	
	@GetMapping()
	public WrapperResponse getAllOrders() {
		try {
			List<SaleOrderDTO> orders = orderService.getAllOrders();
			return new WrapperResponse(true, "success", orders);
		} catch(ValidateServiceException e) {
			return new WrapperResponse(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return new WrapperResponse(false, "Internal Server Error");
		}
	}
	
	@PostMapping
	public WrapperResponse createOrder(@RequestHeader("Authorization") String token, @RequestBody NewOrderDTO order) {
		try {
			WrapperResponse<LoginResponseDTO> user = securityService.tokenValidate(token);
			order.setCustomerName(user.getBody().getUsername());
			order.setCustomerEmail(user.getBody().getEmail());
			SaleOrderDTO newOrder = orderService.createOrder(order);
			return new WrapperResponse(true, "success", newOrder);
		} catch(ValidateServiceException e) {
			return new WrapperResponse(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return new WrapperResponse(false, "Internal Server Error");
		}
	}
	
	
}
