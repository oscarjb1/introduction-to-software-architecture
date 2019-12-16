package io.reactiveprogramming.payment.pooling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class FtpPaymentPooling {

	public static void main(String[] args) {
		SpringApplication.run(FtpPaymentPooling.class, args);
	}
}
