package io.reactiveprogramming.payment.pooling.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import brave.Tracer;
import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.payment.pooling.feign.client.OrderServiceFeignClient;
import io.reactiveprogramming.payment.pooling.feign.client.dto.ApplyPaymentRequest;


@Component
public class FTPPaymentsPolling {
	
	private static final Logger logger = LoggerFactory.getLogger(FTPPaymentsPolling.class);
	
	@Autowired
	private Tracer tracer; 
	
	@Value("${ftp.enable}")
	private String enable;
	
	@Autowired
	private SimpleFtpClient ftpClient;
	
	@Autowired
	private OrderServiceFeignClient orderService;
	
	
	@Scheduled(fixedRate = 10000) 
    public void pollingFile() {
		if(!Boolean.valueOf(enable)) {
			return;
		}
		logger.debug("pollingFile ==>");
		try { 
			ftpClient.connect();
			FTPFile[] files = ftpClient.listFiles("/");
			
			for(FTPFile file : files) {
				if(file.getType() != FTPFile.FILE_TYPE) continue;
				
				String fileContent = ftpClient.readFileAndRemove("/", file);
				logger.info("New file ==> " + fileContent);
				tracer.currentSpan().tag("file.new", file.getName());
				
				String[] lines = fileContent.split("\n");
				for(String line : lines) {
					String[] linesField = line.split(",");
					if(linesField.length != 2) { 
						tracer.currentSpan().tag("file.error", "Invalid file format");
						throw new RuntimeException("Invalid file format");
					}
					String refNumber = linesField[0];
					float ammount = Float.parseFloat(linesField[1]);
					
					ApplyPaymentRequest payment = new ApplyPaymentRequest();
					payment.setAmmount(ammount);
					payment.setRefNumber(refNumber);
					
					WrapperResponse response =  orderService.applyPayment(payment);
					logger.info(response.getMessage());
					tracer.currentSpan().tag("file.process", response.getMessage());
					if(response.isOk()) {
						logger.info("Payment successfully applied");
					}else {
						logger.error("Error " + response.getMessage());
					}
				}
			}
			ftpClient.disconect();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
    }
}
