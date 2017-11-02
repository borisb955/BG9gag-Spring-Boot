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
	public String addComment(HttpServletRequest request){
		HttpSession s = request.getSession();
		Object o = s.getAttribute("logged");
		boolean logged = (o != null && ((boolean) o));
		
		if(s.isNew() || !logged) {
			return "notLogged";
		}else {
//			Post post = (Post) request.getAttribute("postPostPage");
//			User user = (User) request.getAttribute("userPostPage");
			Post post = (Post) s.getAttribute("postPostPage");
			User user = (User) s.getAttribute("userPostPage");
			String text = request.getParameter("commentText");			
			try {
				commentDao.insertComment(new Comment(text,LocalDateTime.now(),null,user,post));
			} catch (SQLException e) {
				request.setAttribute("error", e.getMessage());
			}
				
			return "loggedPostPage";
		
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
			
			Post post = (Post) s.getAttribute("postPostPage");
			User user = (User) s.getAttribute("userPostPage");
						
			try {
				User parentCommentUser = userDao.getUserById(parentCommentUserId);
				Comment parentComment = commentDao.getCommentById(parentCommentId,parentCommentUser);
				commentDao.insertComment(new Comment(commentText,LocalDateTime.now(),parentComment,user,post));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
			return "loggedPostPage";
		
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
		System.out.println(fullURL);

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
			
//			model.addAttribute("userPostPage", user);
//			model.addAttribute("postPostPage", post);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (Validator.notLogged(s)) {
			return "notLoggedPostPage";
		}
		return "loggedPostPage";

	}

}
