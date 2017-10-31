package com.bg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bg.model.User;


@Service
public class SendNewPass {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public SendNewPass(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendNewPass(String email, String newPass) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("bg9gag@gmail.com");
		mail.setSubject("New Password");
		mail.setText("Your new password is " + newPass + ". Please change your password after you log in");
		
		javaMailSender.send(mail);
	}
}
