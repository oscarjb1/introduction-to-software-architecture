package io.reactiveprogramming.payment.pooling.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleFtpClient {

	@Value("${ftp.host}")
	private String ftpHost;

	@Value("${ftp.port}")
	private int ftpPort;

	@Value("${ftp.user}")
	private String ftpUser;
	
	@Value("${ftp.password}")
	private String ftpPassword;

	private FTPClient ftpClient;
	
	public void connect() {
		this.ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpHost, ftpPort);
			ftpClient.login(ftpUser, ftpPassword);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setPassiveLocalIPAddress("localhost");
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (Exception e) {
			throw new RuntimeException("Connection error", e);
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
	
	
	
	public FTPFile[] listFiles(String path)throws Exception {
		try {
			if(!ftpClient.isConnected()) {
				throw new RuntimeException("No FTP connection alive");
			}
			FTPFile[] files = ftpClient.listFiles(path);
			return files;
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public void disconect() {
		try {
			ftpClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}