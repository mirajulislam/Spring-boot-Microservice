package com.email.server.model;

import java.util.Arrays;

public class SendEmail {
	
	private String fromEmail;
	private String toEmail;
	private String subject;
	private String body;
	private String bcc[];
	private String cc[];
	
	public SendEmail() {

	}
	
	public SendEmail(String fromEmail, String toEmail, String subject, String body, String[] bcc, String[] cc) {
		super();
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
		this.subject = subject;
		this.body = body;
		this.bcc = bcc;
		this.cc = cc;
	}
	
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String[] getBcc() {
		return bcc;
	}
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}

	@Override
	public String toString() {
		return "SendEmail [fromEmail=" + fromEmail + ", toEmail=" + toEmail + ", subject=" + subject + ", body=" + body
				+ ", bcc=" + Arrays.toString(bcc) + ", cc=" + Arrays.toString(cc) + "]";
	}

}
