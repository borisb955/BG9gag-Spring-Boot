package com.bg.controller;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bg.model.Profile;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.util.NotificationService;


@Controller
@RequestMapping(value = "/register")
public class RegisterController {
	
	private Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	UserDao ud;
	
	@RequestMapping(method = RequestMethod.GET)
	public String register(Model m) {
		User u = new User();
		m.addAttribute("user", u);
		return "register";
	}
	
	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String registered(@Valid @ModelAttribute("user") User u, BindingResult result) {
		
		

		if(result.hasErrors()) {
			return "register";
		}
		
		if(!u.getUsername().isEmpty() && !u.getEmail().isEmpty() && !u.getPassword().isEmpty()) {
			try {
				ud.insertUser(u);
				notificationService.sendNotification(u);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return "register";
	}
}
