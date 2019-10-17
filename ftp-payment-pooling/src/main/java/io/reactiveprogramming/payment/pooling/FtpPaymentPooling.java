package io.reactiveprogramming.payment.pooling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.reactiveprogramming.payment.pooling.ftp.FTPPaymentsPolling;
import io.reactiveprogramming.payment.pooling.ftp.SimpleFtpClient;

@EnableEurekaClient
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class FtpPaymentPooling {

	public static void main(String[] args) {
		SpringApplication.run(FtpPaymentPooling.class, args);
	}
}
