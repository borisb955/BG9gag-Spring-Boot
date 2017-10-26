package com.bg.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.User;

@Controller
@RequestMapping(value = "/myProfile")
public class ProfileController {
	@Autowired
	PostDao pd;
	
	@RequestMapping(method = RequestMethod.GET)
	public String myProfile() {
		return "profile";
	}
	
	@RequestMapping(value ="/posts", method = RequestMethod.GET)
	public String myPosts(Model m, HttpSession s) {
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
	
	
}
