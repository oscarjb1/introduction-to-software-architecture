package io.reactiveprogramming.party;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookEndpoint {

	@PostMapping
	public void salesOrderEndpoint(@RequestBody Object message) {
		System.out.println("New message ==> " + message );
	}
}
