package io.reactiveprogramming.crm.ftp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import com.jcraft.jsch.ChannelSftp.LsEntry;

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
			
			ftpClient.deleteFile(file.getName());
			
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

}