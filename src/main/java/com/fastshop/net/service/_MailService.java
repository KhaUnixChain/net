package com.fastshop.net.service;

import javax.mail.MessagingException;

import com.fastshop.net.model.Mail;

public interface _MailService {
	void send(String to, String subject, String body) throws MessagingException;

	void send(Mail mail) throws MessagingException;

	void queue(String to, String subject, String body);

	void queue(Mail mail);

	void run();
}