package com.bg.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.UpvoteDao;
import com.bg.model.User;
import com.bg.model.UserDao;

@Controller
public class UserController {
	@Autowired
	UserDao ud;
	@Autowired
	UpvoteDao upd;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	/**
	 * After validating login set the User in the session and Logged is True
	 */
	@RequestMapping(value = "/logged", method = RequestMethod.POST)
	public String logged(HttpServletRequest req, HttpSession s, Model m) throws SQLException {
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
	
		try {
			if (!ud.emailExists(email)) {
				m.addAttribute("error", "This email does not exist");
				return "login";
			} else if (!ud.passwordMatch(email, pass)) {
				m.addAttribute("error", "Password is incorect");
				return "login";
			} else {
				User user = ud.getFullUserByEmail(email);
				
				s.setAttribute("user", user);
				s.setAttribute("logged", true);
				s.setAttribute("likedPosts", upd.getLikedPosts(user));
				s.setAttribute("likedComments", upd.getLikedComments(user));
				s.setAttribute("dislikedPosts", upd.getDislikedPosts(user));
				s.setAttribute("dislikedComments", upd.getDislikedComments(user));
						
				return "forward:/";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	

		return "login";
	}
	
	/**
	 * Invalidating session and logging out
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession s) {
		s.invalidate();
		return "forward:/";
	}

}