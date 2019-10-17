package io.reactiveprogramming.payment.pooling.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.payment.pooling.feign.client.OrderServiceFeignClient;
import io.reactiveprogramming.payment.pooling.feign.client.dto.ApplyPaymentRequest;


@Component
public class FTPPaymentsPolling {
	
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
		System.out.println("pollingFile ==>");
		try { 
			ftpClient.connect();
			FTPFile[] files = ftpClient.listFiles("/");
			
			for(FTPFile file : files) {
				if(file.getType() == FTPFile.FILE_TYPE) {
					
					try {
						String fileContent = ftpClient.readFileAndRemove("/", file);
						System.out.println("file content ==> " + fileContent);
						
						String[] fields = fileContent.split(",");
						if(fields.length != 2) { 
							throw new RuntimeException("Invalid file format");
						}
						
						String refNumber = fields[0];
						float ammount = Float.parseFloat(fields[1]);
						
						ApplyPaymentRequest payment = new ApplyPaymentRequest();
						payment.setAmmount(ammount);
						payment.setRefNumber(refNumber);
						
						WrapperResponse response =  orderService.applyPayment(payment);
						System.out.println(response.getMessage());
						if(response.isOk()) {
						}else {
							
						}
					} catch (Exception e) {
						e.printStackTrace();
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
