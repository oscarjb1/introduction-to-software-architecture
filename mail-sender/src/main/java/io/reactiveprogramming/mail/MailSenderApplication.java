package io.reactiveprogramming.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MailSenderApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", MailSenderApplication.class.getResource("/trustore.jks").getFile());
        System.setProperty("javax.net.ssl.trustStorePassword", "1234");
		SpringApplication.run(MailSenderApplication.class, args);
	}
}