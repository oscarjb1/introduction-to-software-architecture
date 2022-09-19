package io.reactiveprogramming.payment.pooling.config;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

@Configuration
public class FTPConfiguration {
	
	@Value("${ftp.host}")
	private String ftpHost;

	@Value("${ftp.port}")
	private int ftpPort;

	@Value("${ftp.user}")
	private String ftpUser;
	
	@Value("${ftp.password}")
	private String ftpPassword;

	@Bean
	public DefaultFtpSessionFactory ftpSessionFactory() {
		DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
		sf.setHost(ftpHost);
		sf.setPort(ftpPort);
		sf.setUsername(ftpUser);
		sf.setPassword(ftpPassword);
		return sf;
	}
	
	@Bean
	public FTPClient ftpClient( ) {
		return ftpSessionFactory().getSession().getClientInstance();
	}
	
}
