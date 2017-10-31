package com.bg.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Comment;
import com.bg.model.CommentDao;
import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.UpvoteDao;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.util.Validator;


@Controller
public class LikeController2 {
	@Autowired
	CommentDao cd; 
	@Autowired
	UpvoteDao ud;
	@Autowired
	PostDao pd;
	@Autowired
	UserDao usd;
	
	
	@RequestMapping(value = "/likePost", method = RequestMethod.GET)//raboti
	public String likePost(HttpSession s, Model m,HttpServletRequest request) {
		if(Validator.notLogged(s)) {
			return "notLogged";
		}
		long userId = Long.parseLong((request.getParameter("userId")));
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		try {
			User user = usd.getUserById(userId);
			Post post = pd.getPost(postId, user);
			ud.insertLikedPost(user, post);
			user=(User) s.getAttribute("user");
			HashSet<Post> likedPosts = (HashSet<Post>) s.getAttribute("likedPosts");
				likedPosts.add(post);
				s.setAttribute("likedPosts", likedPosts);
			user.addLikedPost(post);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "logged";
		
	}
	
	
	@RequestMapping(value = "/unlikePost", method = RequestMethod.GET)//rabotii
	public String unlikePost(HttpSession s, Model m,HttpServletRequest request) {
		if(Validator.notLogged(s)) {
			return "notLogged";
		}
		
		long userId = Long.parseLong((request.getParameter("userId")));
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		try {
			
			User user = usd.getUserById(userId);
			Post post = pd.getPost(postId, user);
			ud.removeLikedPost(user, post);
			user=(User) s.getAttribute("user");
			HashSet<Post> likedPosts = (HashSet<Post>) s.getAttribute("likedPosts");
			likedPosts.remove(post);
			user.removeLikedPost(post);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "logged";
	}
	
	@RequestMapping(value = "/dislikePost", method = RequestMethod.GET)
	public String dislikePost(HttpSession s, Model m,HttpServletRequest request) {
		if(Validator.notLogged(s)) {
			return "notLogged";
		}
		
		long userId = Long.parseLong((request.getParameter("userId")));
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		try {
			
			User user = usd.getUserById(userId);
			Post post = pd.getPost(postId, user);
			ud.dislikePost(user, post);
			user=(User) s.getAttribute("user");
			//user.addLikedPost(post);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "logged";
	}
	
	@RequestMapping(value = "/undislikePost", method = RequestMethod.GET)
	public String undislikePost(HttpSession s, Model m,HttpServletRequest request) {
		if(Validator.notLogged(s)) {
			return "notLogged";
		}
		
		long userId = Long.parseLong((request.getParameter("userId")));
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		try {
			
			User user = usd.getUserById(userId);
			Post post = pd.getPost(postId, user);
			ud.undislikePost(user, post);
			user=(User) s.getAttribute("user");
			//user.addLikedPost(post);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "logged";
	}
	
	
	@RequestMapping(value = "/likeComment", method = RequestMethod.GET)//raboti
	public String likeComment(HttpSession s, Model m,HttpServletRequest request) {
		if(Validator.notLogged(s)) {
			return "notLogged";
		}
		long userId = Long.parseLong((request.getParameter("userId")));
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		
		try {
//			User user = usd.getUserById(userId);
			User user=(User) s.getAttribute("user");
			Comment comment = cd.getCommentById(commentId, user);
			ud.insertLikedComment(user, comment);
//			user=(User) s.getAttribute("user");
//			HashSet<Post> likedPosts = (HashSet<Post>) s.getAttribute("likedPosts");
//				likedPosts.add(post);
//				s.setAttribute("likedPosts", likedPosts);
//			user.addLikedPost(post);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "logged";
		
	}
	
	@RequestMapping(value = "/unlikeComment", method = RequestMethod.GET)//raboti
	public String unlikeComment(HttpSession s, Model m,HttpServletRequest request) {
		if(Validator.notLogged(s)) {
			return "notLogged";
		}
		long userId = Long.parseLong((request.getParameter("userId")));
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		
		try {
//			User user = usd.getUserById(userId);
			User user=(User) s.getAttribute("user");
			Comment comment = cd.getCommentById(commentId, user);
			ud.removeLikedComment(user, comment);
//			user=(User) s.getAttribute("user");
//			HashSet<Post> likedPosts = (HashSet<Post>) s.getAttribute("likedPosts");
//				likedPosts.add(post);
//				s.setAttribute("likedPosts", likedPosts);
//			user.addLikedPost(post);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "logged";
		
	}
	
	
	@RequestMapping(value = "/dislikeComment", method = RequestMethod.GET)//raboti
	public String dislikeComment(HttpSession s, Model m,HttpServletRequest request) {
		if(Validator.notLogged(s)) {
			return "notLogged";
		}
		long userId = Long.parseLong((request.getParameter("userId")));
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		
		try {
//			User user = usd.getUserById(userId);
			User user=(User) s.getAttribute("user");
			Comment comment = cd.getCommentById(commentId, user);
			ud.dislikeComment(user, comment);
//			user=(User) s.getAttribute("user");
//			HashSet<Post> likedPosts = (HashSet<Post>) s.getAttribute("likedPosts");
//				likedPosts.add(post);
//				s.setAttribute("likedPosts", likedPosts);
//			user.addLikedPost(post);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "logged";
		
	}
	
	@RequestMapping(value = "/undislikeComment", method = RequestMethod.GET)//raboti
	public String undislikeComment(HttpSession s, Model m,HttpServletRequest request) {
		if(Validator.notLogged(s)) {
			return "notLogged";
		}
		long userId = Long.parseLong((request.getParameter("userId")));
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		
		try {
//			User user = usd.getUserById(userId);
			User user=(User) s.getAttribute("user");
			Comment comment = cd.getCommentById(commentId, user);
			ud.undislikeComment(user, comment);
//			user=(User) s.getAttribute("user");
//			HashSet<Post> likedPosts = (HashSet<Post>) s.getAttribute("likedPosts");
//				likedPosts.add(post);
//				s.setAttribute("likedPosts", likedPosts);
//			user.addLikedPost(post);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "logged";
		
	}
	
	
	
	
}
