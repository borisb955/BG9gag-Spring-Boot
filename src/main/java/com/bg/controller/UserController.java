package com.bg.controller;

import java.sql.SQLException;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.UpvoteDao;
import com.bg.model.User;
import com.bg.model.UserDao;

@Controller
public class UserController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@Autowired
	UserDao ud;
	@Autowired
	UpvoteDao upd;
	
	
	@RequestMapping(value = "/logged", method = RequestMethod.POST)
	public String logged(HttpServletRequest req, HttpSession s) throws SQLException {
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		
		System.out.println(email);
		System.out.println(pass);
	
		try {
			if (!ud.emailExists(email)) {
//				resp.getWriter().append("This email does not exist.");
				System.out.println("nqma takav email");
			} else if (ud.passwordMatch(email, pass)) {
//				resp.getWriter().append("Incorrect password.");
				System.out.println("ne e sushtata parola");
			} else {
				
				User u = ud.getFullUserByEmail(email);
				
				s.setAttribute("user", u);
				s.setAttribute("logged", true);
				s.setAttribute("likedPosts",upd.getLikedPosts(u));
				s.setAttribute("likedComments", upd.getLikedComments(u));
				return "forward:/";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("problem");
//			req.setAttribute("error", e.getMessage());
//			req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
		}
	

		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession s) {
		s.invalidate();
		return "forward:/";
	}

}