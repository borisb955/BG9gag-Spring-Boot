package com.bg.controller;

import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Profile;
import com.bg.model.User;
import com.bg.model.UserDao;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {
	@Autowired
	UserDao ud;
	
	@RequestMapping(method = RequestMethod.GET)
	public String register(Model m) {
		User u = new User();
		m.addAttribute("user", u);
		return "register";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String registered(@Valid @ModelAttribute("user") User u, BindingResult result) {
		
		System.out.println("Username " + u.getUsername());
		System.out.println("Email " + u.getEmail());
		System.out.println("Password " + u.getPassword());
		
		if(result.hasErrors()) {
			return "register";
		}
		
		if(!u.getUsername().isEmpty() && !u.getEmail().isEmpty() && !u.getPassword().isEmpty()) {
			try {
				ud.insertUser(u);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return "register";
	}
}
