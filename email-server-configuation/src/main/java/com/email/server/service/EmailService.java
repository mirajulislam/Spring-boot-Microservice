package com.email.server.service;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {
	private static Logger log = LogManager.getLogger(EmailService.class);
	@Autowired
	private JavaMailSender mailSender;

	public String sendEmailProcess(String formEmail, String toEmail, String[] bcc,String[] cc,String subject, String body) throws MessagingException {
		return sendEmailWithOutAttachment(formEmail, toEmail, bcc ,cc , subject, body);
	}

	public String sendEmailProcess(String formEmail, String toEmail, String bcc[], String cc[], String subject, String body,File file)
			throws MessagingException {
		return sendEmailWithAttachment(formEmail, toEmail, bcc,cc, subject, body,file);
	}

	public String sendEmailProcess(String formEmail, String toEmail, String subject, String body) throws MessagingException {
		return sendEmailWithOutAttachment(formEmail, toEmail, subject, body);
	}

	public String sendEmailProcess(String formEmail, String toEmail, String subject, String body, File file)
			throws MessagingException {
		return sendEmailWithAttachment(formEmail, toEmail, subject, body, file);
	}

	private String sendEmailWithAttachment(String formEmail, String toEmail, String subject, String body,
			File attachment) throws MessagingException {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(formEmail);
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(body, true);
		
		if(attachment != null) {
			FileSystemResource file = new FileSystemResource(attachment);
			String attachmentName = file.getFilename();		
			attachmentName = prepareFileName(attachmentName);
			helper.addAttachment(attachmentName, file);
		}
		return mailSender(mimeMessage);		
	}	
	
	private String sendEmailWithAttachment(String formEmail, String toEmail, String[] bcc, String[] cc,String subject, String body,
			File attachment) throws MessagingException {
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(formEmail);
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(body, true);
			
			if(cc != null) {
				helper.setCc(cc);
			}
			if(bcc != null) {
				helper.setBcc(bcc);	
			}			
			if(attachment != null) {
				FileSystemResource file = new FileSystemResource(attachment);	
				String attachmentName = file.getFilename();		
				attachmentName = prepareFileName(attachmentName);
				helper.addAttachment(attachmentName, file);
			}			
			return mailSender(mimeMessage);
		}

	private String sendEmailWithOutAttachment(String formEmail, String toEmail, String subject, String body) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(formEmail);
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(body, true);

		return mailSender(mimeMessage);
	}
	
	private String sendEmailWithOutAttachment(String formEmail, String toEmail, String bcc[], String cc[], String subject, String body) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(formEmail);
		helper.setTo(toEmail);
		helper.setSubject(subject);
		
		helper.setText(body, true);
		if(cc != null) {
			helper.setCc(cc);
		}
		if(bcc != null) {
			helper.setBcc(bcc);	
		}
		return mailSender(mimeMessage);
	}
	
	private String mailSender(MimeMessage mimeMessage){
		log.info("mailSender Method call for Send Mail");
		mailSender.send(mimeMessage);
		return "Send Email Successfully";
	}
	
	private String prepareFileName(String attachmentName) {
		String fileNameArray[] = attachmentName.split("\\.");
		attachmentName = X()+"." + fileNameArray[1];
		log.info("File name && File Extension Process : [{}]",attachmentName);
		return attachmentName;
	}
	
	private String X() {
		int leftLimit = 48;
		int rightLimit = 122;
		int targetStringLength = 6;
		Random random = new Random();
		String x1 = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		int x2 = ThreadLocalRandom.current().nextInt(100000, 1000000);
		String x = x1.concat(String.valueOf(x2));
		return x;
	}
}
