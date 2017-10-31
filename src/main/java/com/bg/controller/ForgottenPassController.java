package com.bg.controller;

import java.sql.SQLException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import com.bg.util.SendNewPass;

@Controller
@RequestMapping(value = "/forgottenPass")
public class ForgottenPassController {
	@Autowired
	UserDao ud;
	@Autowired
	SendNewPass sendNewPass;
	
	@RequestMapping(method=RequestMethod.GET)
	public String forgottenPass(Model m){
		m.addAttribute("user", new User());
		return "forgottenPass";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String forgottenPassSend(@ModelAttribute("user") User u,
							BindingResult result){
		
		
		if(result.hasErrors()) {
			return "forgottenPass";
		}
		

		String email = u.getEmail();
		int randomNum = new Random().nextInt(2_000_000_000);
		String newPass = Integer.toString(randomNum);
		
		
		try {
			ud.forgottenPass(email, newPass);
			sendNewPass.sendNewPass(email, newPass);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "forgottenPass";
	}
}
