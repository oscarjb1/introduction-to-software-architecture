package io.reactiveprogramming.crm.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.crm.api.services.OrderService;
import io.reactiveprogramming.crm.dto.SaleOrderDTO;
import io.reactiveprogramming.crm.entity.SaleOrder;

@RestController
@RequestMapping("orders")
public class OrderREST {

	@Autowired
	private OrderService orderService;
	
	@GetMapping()
	public WrapperResponse getAllOrders() {
		try {
			List<SaleOrderDTO> orders = orderService.getAllOrders();
			return new WrapperResponse(true, "success", orders);
		} catch(ValidateServiceException e) {
			return new WrapperResponse(false, e.getMessage());
		}catch (Exception e) {
			return new WrapperResponse(false, "Internal Server Error");
		}
		
	}
}
