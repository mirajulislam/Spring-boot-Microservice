package com.email.server.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.email.server.kafka.cons.KafkaConstants;
import com.email.server.model.SendEmail;
import com.email.server.service.FileService;

@RestController
public class EmailController {
	private static Logger log = LogManager.getLogger(EmailController.class);
	@Autowired
	private FileService fileService;
	
	@Autowired
	private KafkaTemplate<String, SendEmail> kafkaTemplate;
	
	@RequestMapping(value = "/send-email/attach/", method = RequestMethod.POST)
	@ResponseBody
	public String sendEmail(@RequestParam("file") MultipartFile file,HttpServletRequest httpServletRequest, SendEmail reqBody) throws MessagingException, IOException {
		log.info("Requested Api Name :[{}] Api Body: [{}]","/send-email/",reqBody.toString());
		return fileService.sendEmailProcess(reqBody,file);
	}	

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
