package io.reactiveprogramming.payment.pooling.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.payment.pooling.feign.client.dto.ApplyPaymentRequest;

@FeignClient(name="crm/payments")
public interface OrderServiceFeignClient {

	@PostMapping
	public WrapperResponse applyPayment(@RequestBody ApplyPaymentRequest request);
}
