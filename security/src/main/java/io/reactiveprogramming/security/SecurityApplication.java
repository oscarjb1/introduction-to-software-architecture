package io.reactiveprogramming.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class SecurityApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
}