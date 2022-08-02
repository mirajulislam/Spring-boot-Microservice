package com.bikoybaba.email.kafka.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bikoybaba.email.kafka.cons.KafkaConstants;
import com.bikoybaba.email.model.SendEmail;

@RestController
public class EmailController {
	private static Logger log = LogManager.getLogger(EmailController.class);
	@Autowired
	private KafkaTemplate<String, SendEmail> kafkaTemplate;

	@RequestMapping(value = "/send-email/", method = RequestMethod.POST)
	@ResponseBody
	public String sendEmail(HttpServletRequest httpServletRequest,@RequestBody SendEmail reqBody)
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
