package com.bg.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
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
			return "login";
		}
		long userId = Long.parseLong((request.getParameter("userId")));
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		try {
			User user = usd.getUserById(userId);
			Post post = pd.getPost(postId, user);
			ud.insertLikedPost(user, post);
			
			
			((HashSet<Post>) s.getAttribute("likedPosts")).add(post);
			HashSet<Post> dislikedPosts = (HashSet<Post>) s.getAttribute("dislikedPosts");
			if(dislikedPosts.contains(post)){
				dislikedPosts.remove(post);
				s.setAttribute("dislikedPosts", dislikedPosts);
			}
//			printLikedStuff(s);
				
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
			((HashSet<Post>) s.getAttribute("likedPosts")).remove(post);
			//remove from likedPosts
//			printLikedStuff(s);
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
			((HashSet<Post>) s.getAttribute("dislikedPosts")).add(post);
			HashSet<Post> likedPosts = (HashSet<Post>) s.getAttribute("likedPosts");
			if(likedPosts.contains(post)){
				likedPosts.remove(post);
				s.setAttribute("likedPosts", likedPosts);
			}
		
//			printLikedStuff(s);
			
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
			//remove from dislikePosts
			((HashSet<Post>) s.getAttribute("dislikedPosts")).remove(post);
//			printLikedStuff(s);
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

			User user=(User) s.getAttribute("user");
			Comment comment = cd.getCommentById(commentId, user);
			ud.insertLikedComment(user, comment);
			((HashSet<Comment>) s.getAttribute("likedComments")).add(comment);
			HashSet<Comment> dislikedComments = (HashSet<Comment>) s.getAttribute("dislikedComments");
			if(dislikedComments.contains(comment)){
				dislikedComments.remove(comment);
				s.setAttribute("dislikedComments", dislikedComments);
			}
			
//			printLikedStuff(s);
			
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
			User user=(User) s.getAttribute("user");
			Comment comment = cd.getCommentById(commentId, user);
			ud.removeLikedComment(user, comment);
			((HashSet<Post>) s.getAttribute("likedComments")).remove(comment);
//			printLikedStuff(s);
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

			User user=(User) s.getAttribute("user");
			Comment comment = cd.getCommentById(commentId, user);
			ud.dislikeComment(user, comment);
			((HashSet<Comment>) s.getAttribute("dislikedComments")).add(comment);
			HashSet<Comment> likedComment = (HashSet<Comment>) s.getAttribute("likedComments");
			if(likedComment.contains(comment)){
				likedComment.remove(comment);
				s.setAttribute("likedComments", likedComment);
			}
//			printLikedStuff(s);
			
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
			User user=(User) s.getAttribute("user");
			Comment comment = cd.getCommentById(commentId, user);
			ud.undislikeComment(user, comment);
			((HashSet<Comment>) s.getAttribute("dislikedComments")).remove(comment);
//			printLikedStuff(s);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "logged";
		
	}


	private void printLikedStuff(HttpSession s) {
		HashSet<Post> likedPosts = (HashSet<Post>) s.getAttribute("likedPosts");
		HashSet<Post> dislikedPosts = (HashSet<Post>) s.getAttribute("dislikedPosts");
		HashSet<Post> likedComments = (HashSet<Post>) s.getAttribute("likedComments");
		HashSet<Post> dislikedComments = (HashSet<Post>) s.getAttribute("dislikedComments");
		System.out.println("LikePOSTS------------------------------------");
		for (Iterator iterator = likedPosts.iterator(); iterator.hasNext();) {
			Post post = (Post) iterator.next();
			System.out.println(post.getPostId());
		}
		System.out.println("DISLikePOSTS------------------------------------");
		for (Iterator iterator = dislikedPosts.iterator(); iterator.hasNext();) {
			Post post = (Post) iterator.next();
			System.out.println(post.getPostId());
		}
		System.out.println("LikeCOMMENTS------------------------------------");
		for (Iterator iterator = likedComments.iterator(); iterator.hasNext();) {
			Comment post = (Comment) iterator.next();
			System.out.println(post.getComment_id());
		}
		System.out.println("DISLikeCOMMENTS------------------------------------");
		for (Iterator iterator = dislikedComments.iterator(); iterator.hasNext();) {
			Comment post = (Comment) iterator.next();
			System.out.println(post.getComment_id());
		}
		
		
	}
	
	
	
	
}
