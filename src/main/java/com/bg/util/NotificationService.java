//package com.bg.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import com.bg.model.User;
//
//
//@Service
//public class NotificationService {
//
//	private JavaMailSender javaMailSender;
//	
//	@Autowired
//	public NotificationService(JavaMailSender javaMailSender) {
//		this.javaMailSender = javaMailSender;
//	}
//	
//	public void sendNotification(User u) throws MailException {
//		SimpleMailMessage mail = new SimpleMailMessage();
//		mail.setTo(u.getEmail());
//		mail.setFrom("bg9gag@gmail.com");
//		mail.setSubject("Zdravei");
//		mail.setText("proba proba");
//		
//		javaMailSender.send(mail);
//	}
//}
