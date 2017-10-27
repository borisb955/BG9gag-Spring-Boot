package com.bg.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Post;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.util.Validator;


@Controller
@RequestMapping(value = "/settings")
public class SettingsController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String settings(Model m, HttpSession s) {
		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
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
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User sessionUser = (User) s.getAttribute("user");
		
		String newUsername = user.getUsername();
		String newEmail = user.getEmail();
		
		boolean uIsChanged = false;
		boolean eIsChanged = false;
		
		if(newUsername != null && !newUsername.isEmpty()) {
			try {
				if(!ud.userExists(newUsername) && ud.isValidEmailAddress(newEmail)) {
					ud.changeUsername(sessionUser.getId(), newUsername);
					uIsChanged = true;
				}else {
					//error pages ->
					// User exists, please select another username
					//email not valid
				}
			} catch (SQLException e) {
				e.getMessage();
//				req.setAttribute("error", e.getMessage());
//				req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
			}
		}
		
		if(newEmail != null && !newEmail.isEmpty()) {
			try {
				if(!ud.emailExists(newEmail) && ud.isValidEmailAddress(newEmail)) {
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
		return "forward:/";
	}
	
	@RequestMapping(value="/password", method = RequestMethod.GET)
	public String password(HttpSession s) {
		if(Validator.notLogged(s)) {
			return "forward:/";
		}

		return "passwordSettings";
	}
	
	@RequestMapping(value="/test", method = RequestMethod.POST)
	public String passwordChange(HttpSession s, HttpServletRequest req) {
		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User sessionUser = (User) s.getAttribute("user");
		
		String password1 = req.getParameter("pass1");
		String password2 = req.getParameter("pass2");
		
		//Password verification needed
		if(password1.equals(password2)) {
			try {
				System.out.println(password1);
				System.out.println(password2);
				ud.changePassword(sessionUser.getId(), password1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			//error page -> passes don't match
		}

		return "forward:/";
	}
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public String profileSettings(HttpSession s) {
		if(Validator.notLogged(s)) {
			return "forward:/";
		}

		return "profileSettings";
	}
	
}
