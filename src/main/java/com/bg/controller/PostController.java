package com.bg.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.util.Validator;

@Controller
public class PostController {
	@Autowired
	UserDao userDao;
	@Autowired
	PostDao postDao;
	@RequestMapping(value="/postWithComments/postId={postId}/userId={userId}", method = RequestMethod.GET)
	public String showPostWithComment(@PathVariable("postId") int postId, @PathVariable("userId") int userId, HttpSession s, Model model){		
			try {
				User user = userDao.getUserById(userId);
				Post post = postDao.getPost(postId, user);
				model.addAttribute("userPostPage", user);
				model.addAttribute("postPostPage", post);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(Validator.notLogged(s)){
				return "notLoggedPostPage";
			}
			return "loggedPostPage";
			
		
	}
	
	@RequestMapping(value="postpic/postUrl={ postUrl }", method = RequestMethod.GET)
	public String showPostWithComment(@PathVariable("postUrl") String postUrl, HttpSession s, Model model, HttpServletResponse response){
			
			Post post = (Post) s.getAttribute("post");
			System.out.println(post);
			String pictureUrl = postUrl;
			
			if (pictureUrl == null || pictureUrl.isEmpty()) {
				pictureUrl = "defaultPic.png";
			}
			
			File myFile = new File(pictureUrl);
			try {
			OutputStream out = response.getOutputStream();
			Path path = myFile.toPath();	
				Files.copy(path, out); 
			out.flush();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			if(Validator.notLogged(s)){
				return "notLogged";
			}
			
			return "loggedPostPage";
			
		
	}
	
	
}
