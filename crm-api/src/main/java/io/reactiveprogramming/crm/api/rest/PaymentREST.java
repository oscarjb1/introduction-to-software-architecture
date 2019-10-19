package io.reactiveprogramming.crm.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.crm.api.services.OrderService;
import io.reactiveprogramming.crm.dto.ApplyPaymentRequest;

@RestController
@RequestMapping("payments")
public class PaymentREST {
	
	@Autowired
	private OrderService orderService;

	@PostMapping
	public WrapperResponse applyPayment(@RequestBody ApplyPaymentRequest request) {
		try {
			orderService.applyPayment(request);
			return new WrapperResponse<>(true, "Payment applay successfuly");
		} catch(ValidateServiceException e) {
			return new WrapperResponse<>(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new WrapperResponse<>(false, "Internal server error");
		}
	}
}
