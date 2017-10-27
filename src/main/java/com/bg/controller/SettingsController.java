package com.bg.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Post;
import com.bg.model.User;
import com.bg.model.UserDao;


@Controller
@RequestMapping(value = "/settings")
public class SettingsController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String settings(Model m, HttpSession s) {
		User sessionUser = (User) s.getAttribute("user");
		String username = sessionUser.getUsername();
		String email = sessionUser.getEmail();
		User u = new User(username, email);
		m.addAttribute("user", u);
		return "accountSettings";
	}
	
	@Autowired
	UserDao ud;
	
	@RequestMapping(method = RequestMethod.POST)
	public String saveSettings(@ModelAttribute User user, HttpSession s) {
		String newUsername = user.getUsername();
		String newEmail = user.getEmail();
		
		System.out.println(user);
		System.out.println(user);
		System.out.println(user.getUsername());
		System.out.println(user.getEmail());
		
		boolean uIsChanged = false;
		boolean eIsChanged = false;
		User sessionUser =(User) s.getAttribute("user");
		
		if(newUsername != null && !newUsername.isEmpty()) {
			try {
				if(!ud.userExists(newUsername)) {
					ud.changeUsername(sessionUser.getId(), newUsername);
					uIsChanged = true;
				}else {
					// User exists, please select another username
				}
			} catch (SQLException e) {
//				req.setAttribute("error", e.getMessage());
//				req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
			}
		}
		
		if(newEmail != null && !newEmail.isEmpty()) {
			try {
				if(!ud.emailExists(newEmail)) {
					ud.changeEmail(sessionUser.getId(), newEmail);
					eIsChanged = true;
				}else {
					// User exists, please select another username
				}
			} catch (SQLException e) {
//				req.setAttribute("error", e.getMessage());
//				req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
			}
		}
		
		
		if(!uIsChanged) {
			newUsername = sessionUser.getUsername();
		}
		if(!eIsChanged) {
			newEmail = sessionUser.getEmail();
		}
		

		user = new User(sessionUser.getId(), newUsername, sessionUser.getPassword(), newEmail, 
				sessionUser.getProfile(), sessionUser.getLikedPosts());
		
		s.removeAttribute("user");
		s.setAttribute("user", user);
		return "logged";
	}
}
