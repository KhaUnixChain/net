package com.fastshop.net.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
	private String from;
	private String to;
	private String[] cc = null;
	private String[] bcc = null;
	private String subject;
	private String body;
	private String[] attachments = null;
	
	public Mail(String to, String subject, String body) {
		this.from = "nhomchung1999@gmail.com";  // thường là gmail của công ty
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}