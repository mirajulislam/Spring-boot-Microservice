package com.email.server.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.email.server.model.SendEmail;
import com.email.server.service.ServiceCoordinator;
import com.email.server.util.JwtUtil;

@RestController
public class EmailController {
	@Autowired
	private ServiceCoordinator serviceCoordinator;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping(value = "/email/send-email/attach/", method = RequestMethod.POST)
	public String sendEmail(@RequestParam("file") MultipartFile file,HttpServletRequest httpServletRequest,@RequestBody SendEmail reqBody) throws MessagingException, IOException {
		return serviceCoordinator.sendEmailAttachment(reqBody,file);
	}	

	@RequestMapping(value = "/email/send-email/", method = RequestMethod.POST)
	@ResponseBody
	public String sendEmail(HttpServletRequest httpServletRequest,@RequestBody SendEmail reqBody)
			throws MessagingException, IOException {
		return serviceCoordinator.sendEmail(httpServletRequest, reqBody);
	}
	
	@RequestMapping(value = "/email/test-api/", method = RequestMethod.GET)
	public ResponseEntity<String> testApi(@RequestBody String userName) {
		String token = jwtUtil.generateToken(userName);

		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/email/jwt-api/", method = RequestMethod.GET)
	public String jwtApi() {
		return "With Jwt";
	}
}
