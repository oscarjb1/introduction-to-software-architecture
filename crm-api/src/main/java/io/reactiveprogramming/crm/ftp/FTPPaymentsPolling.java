package io.reactiveprogramming.crm.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class FTPPaymentsPolling {
	
	@Autowired
	private SimpleFtpClient simpleFTPClient;

	
	@Scheduled(fixedRate = 5000)
    public void pollingFile() {
		try {
			simpleFTPClient.connect();
			FTPFile[] files = simpleFTPClient.listFiles("/");
			
			for(FTPFile file : files) {
				String fileContent = simpleFTPClient.readFileAndRemove("/", file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
    }
}
