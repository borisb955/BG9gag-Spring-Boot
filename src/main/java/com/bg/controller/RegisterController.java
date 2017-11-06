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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bg.model.Profile;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.model.User.ChangeAccount;
import com.bg.model.User.Register;
import com.bg.util.NotificationService;


@Controller
@RequestMapping(value = "/register")
public class RegisterController {
	
	private Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	UserDao ud;
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * Returning the sign up page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String register(Model m) {
		User u = new User();
		m.addAttribute("user", u);
		return "register";
	}
	
	/**
	 * If user details are valid insert the user in DB, send him welcome email and
	 * redirect to login
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String signUp(@Validated({Register.class}) @ModelAttribute("user") User u, 
			BindingResult result, Model m) {

		if(result.hasErrors()) {
			return "register";
		}
		
		try {
			ud.insertUser(u);
			notificationService.sendNotification(u);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		m.addAttribute("success", "You have been successfully registered. Please login");
		
		return "login";
	}
}
