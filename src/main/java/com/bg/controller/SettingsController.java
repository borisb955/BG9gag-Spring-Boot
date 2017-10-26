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
		String username = (String)s.getAttribute("username");
		String email = (String)s.getAttribute("email");
		User u = new User(username, email);
		m.addAttribute("user", u);
		return "accountSettings";
	}
	
	@Autowired
	UserDao ud;
	
	@RequestMapping(method = RequestMethod.POST)
	public String saveSettings(@ModelAttribute User u, HttpSession s) {
		String newUsername = u.getUsername();
		String newEmail = u.getEmail();
		boolean uIsChanged = false;
		boolean eIsChanged = false;
		
		if(!newUsername.isEmpty() && newUsername != null) {
			try {
				if(!ud.userExists(newUsername)) {
					ud.changeUsername(u.getId(), newUsername);
					uIsChanged = true;
				}else {
					// User exists, please select another username
				}
			} catch (SQLException e) {
//				req.setAttribute("error", e.getMessage());
//				req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
			}
		}
		
		if(!newEmail.isEmpty() && newEmail != null) {
			try {
				if(!ud.emailExists(newEmail)) {
					ud.changeEmail(u.getId(), newEmail);
					eIsChanged = true;
				}else {
					// User exists, please select another username
				}
			} catch (SQLException e) {
//				req.setAttribute("error", e.getMessage());
//				req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
			}
		}
		
		User sessionUser =(User) s.getAttribute("user");
		
		if(!uIsChanged) {
			newUsername = sessionUser.getUsername();
		}
		if(!eIsChanged) {
			newEmail = sessionUser.getEmail();
		}
		

		u = new User(sessionUser.getId(), newUsername, sessionUser.getPassword(), newEmail, 
				sessionUser.getProfile(), sessionUser.getLikedPosts());
		
		s.removeAttribute("user");
		s.setAttribute("user", u);
		return "accountSettings";
	}
}
