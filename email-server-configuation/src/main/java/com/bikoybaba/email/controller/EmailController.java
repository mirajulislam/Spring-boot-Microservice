//package com.bikoybaba.email.controller;
//
//import java.io.IOException;
//
//import javax.mail.MessagingException;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.bikoybaba.email.model.SendEmail;
//import com.bikoybaba.email.service.FileService;
//
//@RestController
//public class EmailController {
//	private static Logger log = LogManager.getLogger(EmailController.class);
//	@Autowired
//	private FileService fileService;
//	
//	@RequestMapping(value = "/send-email/", method = RequestMethod.POST)
//	@ResponseBody
//	public String sendEmail(@RequestParam("file") MultipartFile file,HttpServletRequest httpServletRequest, SendEmail reqBody) throws MessagingException, IOException {
//		log.info("Requested Api Name :[{}] Api Body: [{}]","/send-email/",reqBody.toString());
//		return fileService.sendEmailProcess(reqBody,file);
//	}
//}
