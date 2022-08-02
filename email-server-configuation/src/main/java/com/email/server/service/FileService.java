package com.email.server.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.email.server.model.SendEmail;

@Component
public class FileService {
	
	private static Logger log = LogManager.getLogger(FileService.class);
	
	@Value("${spring.mail.username}")
	private String userName;
	
	@Autowired
	private EmailService emailService;

	public String sendEmailProcess(SendEmail reqBody, MultipartFile file) throws IOException, MessagingException {
		
		if(reqBody.getBody() == null || reqBody.getBody().isEmpty()) {
			reqBody.setBody(emailBody());
		}			
		if(file == null && reqBody.getCc() == null && reqBody.getBcc() == null) {
			return emailService.sendEmailProcess(userName, reqBody.getToEmail(), reqBody.getSubject(), 
					reqBody.getBody());
		}else if(file == null && reqBody.getBcc() != null && reqBody.getCc() == null) {
			return emailService.sendEmailProcess(userName, reqBody.getToEmail(),  reqBody.getBcc(), null,reqBody.getSubject(),
					reqBody.getBody());
		}else if(file == null && reqBody.getCc() != null && reqBody.getBcc() == null) {
			return emailService.sendEmailProcess(userName, reqBody.getToEmail(),  null, reqBody.getCc(),reqBody.getSubject(),
					reqBody.getBody());
		}else if(file == null && reqBody.getCc() != null && reqBody.getBcc() != null) {
			return emailService.sendEmailProcess(userName, reqBody.getToEmail(),  reqBody.getBcc(), reqBody.getCc(),reqBody.getSubject(),
					reqBody.getBody());
		}else if(file != null && reqBody.getCc() == null && reqBody.getBcc() == null) {
			return emailService.sendEmailProcess(userName, reqBody.getToEmail(), reqBody.getSubject(), 
					reqBody.getBody(), multipartToFileConvert(file));
		} else if(file != null && reqBody.getCc() != null && reqBody.getBcc() == null) {
			return emailService.sendEmailProcess(userName, reqBody.getToEmail(),  null, reqBody.getCc(),reqBody.getSubject(),
					reqBody.getBody(), multipartToFileConvert(file));
		} else if(file != null && reqBody.getCc() == null && reqBody.getBcc() != null) {
			return emailService.sendEmailProcess(userName, reqBody.getToEmail(),  reqBody.getBcc(), null,reqBody.getSubject(),
					reqBody.getBody(), multipartToFileConvert(file));
		}
		else return emailService.sendEmailProcess(userName, reqBody.getToEmail(),reqBody.getBcc(),reqBody.getCc(), reqBody.getSubject(), 
				reqBody.getBody(), multipartToFileConvert(file));
	}
	
	private File multipartToFileConvert(MultipartFile file) throws IOException{
		log.info("Multipart to File Convert");
		File convFile = new File( file.getOriginalFilename() );
	    FileOutputStream fos = new FileOutputStream( convFile );
	    fos.write( file.getBytes() );
	    fos.close();
	    return convFile;
	}
	
	private String emailBody() throws MessagingException {
		String body = "<html lang=\"bn\"><body><table>\r\n" + "<tbody>\r\n" + "<tr>\r\n" + "<th>আপনি ইমেইলে কোন বডি ব্যবহার করছেন না। </th>\r\n"
				+"</table></body></html>";
		return body;
	}
}
