package io.reactiveprogramming.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.reactiveprogramming.gateway.filter.SecurityFilter;

@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	@Bean
	public SecurityFilter preFilter() {
		return new SecurityFilter();
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}

