package com.bg.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.WebInitializer;
import com.bg.model.CommentDao;
import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.User;
import com.bg.util.Validator;

@Controller
@RequestMapping(value = "/myProfile")
public class ProfileController {
	@Autowired
	PostDao pd;
	@Autowired
	CommentDao cd;
	
	@RequestMapping(method = RequestMethod.GET)
	public String myProfile(HttpSession s, Model m) {
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User u = (User) s.getAttribute("user");
		m.addAttribute("profile", u.getProfile());
		
		return "profile";
	}
	
	@RequestMapping(value ="/posts", method = RequestMethod.GET)
	public String myPosts(Model m, HttpSession s) {
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User u = (User) s.getAttribute("user");
		ArrayList<Post> posts = new ArrayList<>();
		try {
			posts = pd.getAllPostsForUser(u);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		m.addAttribute("posts", posts);
		return "myPosts";
	}
	
	@RequestMapping(value ="/myCommentedPosts", method = RequestMethod.GET)
	public String myComments(HttpSession s, Model m) {
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User u = (User) s.getAttribute("user");
		
		HashSet<Post> posts = null;
		try {
			posts = cd.getCommentedPostsByUser(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m.addAttribute("posts", posts);
		
		return "myCommentedPosts";
	}
	
	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public void displayAvatar(HttpSession s, HttpServletResponse resp, HttpServletRequest req ) {
		
		User u = (User) req.getSession().getAttribute("user");
		String avatarUrl = null;
		if(u.getProfile() != null && u.getProfile().getAvatarUrl() != null) {
			System.out.println(u.getProfile().getAvatarUrl());
			avatarUrl =WebInitializer.LOCATION 
				 	  +File.separator + "users"
				 	  +File.separator + u.getUsername()
				 	  +File.separator + "avatar"
					  +File.separator + "avatar.jpg";
		}
		if(avatarUrl == null) {
			avatarUrl = WebInitializer.LOCATION 
					 	+File.separator + "Administration"
					 	+File.separator + "userDefaultPic"
					 	+File.separator + "default.jpg";
		}
		
		File file = new File(avatarUrl);
		
		try (OutputStream out = resp.getOutputStream()) {
		    Path path = file.toPath();
		    Files.copy(path, out);
		    out.flush();
		} catch (IOException e) {
//			req.setAttribute("error", e.getMessage());
//			req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
		}
	}
	
}
