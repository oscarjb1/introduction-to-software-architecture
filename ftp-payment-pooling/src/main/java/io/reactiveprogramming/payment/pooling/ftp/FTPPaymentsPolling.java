package io.reactiveprogramming.payment.pooling.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTPClient;
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
	private OrderServiceFeignClient orderService;
	
	@Autowired
	private FTPClient ftpClient;
	
	
	@Scheduled(fixedRate = 10000) 
    public void pollingFile() {
		if(!Boolean.valueOf(enable)) {
			return;
		}
		logger.info("pollingFile ==>");
		try { 
			FTPFile[] files = ftpClient.listFiles();

			logger.info("File count: " + files.length);

			for(FTPFile file : files) {
				logger.info("name: " + file.getName());
				logger.info("type: " + file.getType());
				if(file.getType() != FTPFile.FILE_TYPE) continue;
				
				String fileContent = this.readFileAndRemove("/", file);
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
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
    }
	
	public String readFileAndRemove(String path, FTPFile file) throws Exception{
		try {
			ftpClient.changeWorkingDirectory(path);
			
			File tempFile = File.createTempFile("payments-", ".temp");
			FileOutputStream output = new FileOutputStream(tempFile);
			ftpClient.retrieveFile(file.getName(), output);
			output.close();
			
			FileInputStream stream = new FileInputStream(tempFile);
			byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			stream.close();
			
			boolean del = ftpClient.deleteFile(file.getName());
			System.out.println("del => " + del);
			
			return new String(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
}
