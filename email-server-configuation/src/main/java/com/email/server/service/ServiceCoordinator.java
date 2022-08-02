package com.email.server.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.email.server.kafka.cons.KafkaConstants;
import com.email.server.model.SendEmail;

public class ServiceCoordinator {
	
	private static Logger log = LogManager.getLogger(ServiceCoordinator.class);
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private KafkaTemplate<String, SendEmail> kafkaTemplate;
	
	public String sendEmailAttachment(SendEmail reqBody,MultipartFile file) throws MessagingException, IOException {
		log.info("Requested Api Name :[{}] Api Body: [{}]","/send-email/",reqBody.toString());
		return fileService.sendEmailProcess(reqBody,file);
	}
	
	public String sendEmail(HttpServletRequest httpServletRequest, SendEmail reqBody)
			throws MessagingException, IOException {
		log.info("Requested Api Name :[{}] Api Body: [{}]", "/send-email/", reqBody.toString());
		try {
			kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, reqBody).get();
			return "Send Email Successfully";
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
