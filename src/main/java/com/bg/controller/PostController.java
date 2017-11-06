package com.bg.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bg.WebInitializer;
import com.bg.model.Comment;
import com.bg.model.CommentDao;
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
	@Autowired
	CommentDao commentDao; 
		
	private static String URL = "D:" + File.separator + "postPics" + File.separator;
	@RequestMapping(value="/addComment",method=RequestMethod.POST)
	public String addComment(HttpServletRequest request, Model m){
		HttpSession s = request.getSession();
		Object o = s.getAttribute("logged");
		boolean logged = (o != null && ((boolean) o));
		
		if(s.isNew() || !logged) {
			return "login";
		}else {

			Post post = (Post) s.getAttribute("postPostPage");
			User user = (User) s.getAttribute("user");
			String text = request.getParameter("commentText");
			long postId =  Long.parseLong(request.getParameter("postId"));
			long userId =  Long.parseLong(request.getParameter("userId"));
			
			if(text == null || text.trim().length() == 0){	
				s.setAttribute("error",  "You have not entered a comment");
				return "redirect:/postWithComments/postId="+postId+"/userId="+userId;
			}else{
			try {
				commentDao.insertComment(new Comment(text,LocalDateTime.now(),null,user,post));
			} catch (SQLException e) {
				request.setAttribute("error", e.getMessage());
			}
				s.removeAttribute("error");
			return "redirect:/postWithComments/postId="+postId+"/userId="+userId;
			}
		}		
	}
	
	@RequestMapping(value="/addChildComment",method=RequestMethod.POST)
	public String addChildComment(HttpServletRequest request){
		HttpSession s = request.getSession();
		Object o = s.getAttribute("logged");
		boolean logged = (o != null && ((boolean) o));
		
		if(s.isNew() || !logged) {
			return "notLogged";
		}else {
			
			int parentCommentId =Integer.parseInt(request.getParameter("parentCommentId"));
			int parentCommentUserId = Integer.parseInt(request.getParameter("parentCommentUserId"));
			String commentText = request.getParameter("commentText");
			
			long postId =  Long.parseLong(request.getParameter("postId"));
			long userId =  Long.parseLong(request.getParameter("userId"));
			
			if(commentText == null || commentText.trim().length() == 0){	
				s.removeAttribute("error");
				return "redirect:/postWithComments/postId="+postId+"/userId="+userId;
			}else{
			
			Post post = (Post) s.getAttribute("postPostPage");
			User user = (User) s.getAttribute("userPostPage");
						
			try {
				User parentCommentUser = userDao.getUserById(parentCommentUserId);
				Comment parentComment = commentDao.getCommentById(parentCommentId,parentCommentUser);
				commentDao.insertComment(new Comment(commentText,LocalDateTime.now(),parentComment,user,post));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			s.removeAttribute("error");
			return "redirect:/postWithComments/postId="+postId+"/userId="+userId;
			}
		}
			
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/postpic", method = RequestMethod.GET)
	public void showPostWithComment(HttpSession s, Model model, HttpServletResponse response,
			HttpServletRequest request) {

		String userName = request.getParameter("userName");
		String pictureUrl = request.getParameter("pictureUrl");
		String fullURL =WebInitializer.LOCATION 
				 		+File.separator+ "users"
				 		+File.separator + userName
				 		+File.separator + "postPics"
				 		+ File.separator + pictureUrl;

		if (pictureUrl == null || pictureUrl.isEmpty()) {
			pictureUrl = "D:/BG9gag/defaultPic.png";
		}

		File myFile = new File(fullURL);
		try {
			OutputStream out = response.getOutputStream();
			Path path = myFile.toPath();
			Files.copy(path, out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/postWithComments/postId={postId}/userId={userId}", method = RequestMethod.GET)
	public String showPostWithComment(@PathVariable("postId") int postId, @PathVariable("userId") int userId, HttpSession s, Model model) {
		

		try {
			
			User user = userDao.getUserById(userId);
			Post post = postDao.getPost(postId, user);
			s.setAttribute("userPostPage", user);
			s.setAttribute("postPostPage", post);
			
			System.out.println(userId);
			System.out.println(postId);
			System.out.println(userId);
			System.out.println(postId);
			System.out.println(userId);
			System.out.println(postId);
//			model.addAttribute("userPostPage", user);
//			model.addAttribute("postPostPage", post);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (Validator.notLogged(s)) {
			return "notLoggedPostPage";
		}
		

		System.out.println("vliza");
		System.out.println("vliza");
		return "loggedPostPage";

	}

}
