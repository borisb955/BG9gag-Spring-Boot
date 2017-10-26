package com.example.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.DBManager;
import com.example.model.Post;
import com.example.model.PostDao;
import com.example.model.TagDao;
import com.example.model.User;
import com.example.model.UserDao;


@Controller
public class WelcomeController {
	
	@Autowired
	PostDao pd;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String welcome(Model m, HttpSession s) {
		Object o = s.getAttribute("logged");
		boolean logged = (o != null && ((boolean) o));
		
		try {
			HashSet<Post> allPosts = pd.getAllPosts();
			m.addAttribute("allPosts", allPosts);
			System.out.println(allPosts);
			
		} catch (SQLException e) {
			e.getMessage();
//			req.setAttribute("error", e.getMessage());
//			req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
		}
		
		if(s.isNew() || !logged) {
			return "notLogged";
		}else {
			return "logged";  
		}
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public String welcome2(Model m, HttpSession s) {
		Object o = s.getAttribute("logged");
		boolean logged = (o != null && ((boolean) o));
		
		try {
			HashSet<Post> allPosts = pd.getAllPosts();
			m.addAttribute("allPosts", allPosts);
			System.out.println(allPosts);
			
		} catch (SQLException e) {
			e.getMessage();
//			req.setAttribute("error", e.getMessage());
//			req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
		}
		
		if(s.isNew() || !logged) {
			return "notLogged";
		}else {
			return "logged";  
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	

	@Autowired
	UserDao ud;
	
	@RequestMapping(value = "/logged", method = RequestMethod.POST)
	public String logged(HttpServletRequest req, HttpSession s) throws SQLException {
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
	
		try {
			if (!ud.emailExists(email)) {
//				resp.getWriter().append("This email does not exist.");
			} else if (!ud.passwordMatch(email, pass)) {
//				resp.getWriter().append("Incorrect password.");
			} else {
				User u = ud.getFullUserByEmail(email);
				
				s.setAttribute("user", u);
				s.setAttribute("logged", true);
				return "forward : /main ";
			}
		} catch (SQLException e) {
			req.setAttribute("error", e.getMessage());
//			req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
		}
	

		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession s) {
		s.invalidate();
		return "forward : /main";
	}
	
	
}
