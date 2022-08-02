package com.email.server.kafka.consumer;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.email.server.kafka.cons.KafkaConstants;
import com.email.server.model.SendEmail;
import com.email.server.service.EmailService;

@Component
public class EmailSendListener {
	private static final Logger logger = LoggerFactory.getLogger(EmailSendListener.class);
	@Value("${spring.mail.username}")
	private String userName;	
	@Autowired
	private EmailService emailService;
//		 @Autowired
//	    SimpMessagingTemplate template;

	@KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID)
	public void listen(SendEmail message) throws MessagingException {
		logger.info(String.format("Message sent -> %s", message));
		emailService.sendEmailProcess(userName, message.getToEmail(),message.getBcc(),message.getCc(), message.getSubject(), message.getBody());
//	        template.convertAndSend("/topic/group", message);
	}
}
