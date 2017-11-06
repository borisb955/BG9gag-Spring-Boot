package com.bg.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bg.model.Comment;
import com.bg.model.CommentDao;
import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.UpvoteDao;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.util.Validator;

@RestController
public class LikeController {

	@Autowired
	CommentDao cd; 
	@Autowired
	UpvoteDao ud;
	@Autowired
	PostDao pd;
	@Autowired
	UserDao usd;
	
	
	@RequestMapping(value="/likePost", method=RequestMethod.POST)
	@ResponseBody
	public String likePost(HttpServletResponse resp, HttpSession session, HttpServletRequest request){
		
		
		if(Validator.notLogged(session)) {
			resp.setStatus(401);
		}
		Post post = null;
		int postId = Integer.parseInt(request.getParameter("postId"));
		int points =1;
		try {
			User user = (User) session.getAttribute("user");
			 post = pd.getPost(postId, user);
			ud.insertLikedPost(user, post);	
			((HashSet<Post>) session.getAttribute("likedPosts")).add(post);
			HashSet<Post> dislikedPosts = (HashSet<Post>) session.getAttribute("dislikedPosts");
			if(dislikedPosts.contains(post)){
				dislikedPosts.remove(post);
				session.setAttribute("dislikedPosts", dislikedPosts);
				points++;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			resp.setStatus(500);
		}
		resp.setStatus(200);
		return String.valueOf(post.getPoints()+points);
	}
	
	@RequestMapping(value="/unlikePost", method=RequestMethod.POST)
	@ResponseBody
	public String unlikePost(HttpServletResponse resp, HttpSession session, HttpServletRequest request){
		if(Validator.notLogged(session)) {	
			resp.setStatus(401);
		}
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		Post post = null;
		try {
			
			User user = (User) session.getAttribute("user");
			post = pd.getPost(postId, user);
			ud.removeLikedPost(user, post);
			((HashSet<Post>) session.getAttribute("likedPosts")).remove(post);
			} catch (SQLException e) {
				e.printStackTrace();
				resp.setStatus(500);
			}
			resp.setStatus(200);
		return String.valueOf(post.getPoints()-1);		
	}
	
	@RequestMapping(value="/dislikePost", method=RequestMethod.POST)
	@ResponseBody
	public String dislikePost(HttpServletResponse resp, HttpSession session, HttpServletRequest request){
		if(Validator.notLogged(session)) {
			resp.setStatus(401);
		}
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		Post post = null;
		int points=1;
		try {			
			User user = (User) session.getAttribute("user");
			post = pd.getPost(postId, user);
			ud.dislikePost(user, post);
			((HashSet<Post>) session.getAttribute("dislikedPosts")).add(post);
			HashSet<Post> likedPosts = (HashSet<Post>) session.getAttribute("likedPosts");
			if(likedPosts.contains(post)){
				likedPosts.remove(post);
				session.setAttribute("likedPosts", likedPosts);
				points++;
			} 
		}catch (SQLException e) {
				e.printStackTrace();
				resp.setStatus(500);
			}
			resp.setStatus(200);
		return String.valueOf(post.getPoints()-points);		
	}
	
	@RequestMapping(value="/undislikePost", method=RequestMethod.POST)
	@ResponseBody
	public String undislikePost(HttpServletResponse resp, HttpSession session, HttpServletRequest request){
		if(Validator.notLogged(session)) {
			resp.setStatus(401);
		}
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		Post post = null;
		int points=1;
		try {			
			User user = (User) session.getAttribute("user");
			post = pd.getPost(postId, user);
			ud.undislikePost(user, post);
			user=(User) session.getAttribute("user");
			((HashSet<Post>) session.getAttribute("dislikedPosts")).remove(post);
		}catch (SQLException e) {
				e.printStackTrace();
				resp.setStatus(500);
			}
			resp.setStatus(200);
		return String.valueOf(post.getPoints()+points);		
	}
	
	@RequestMapping(value="/likeComment", method=RequestMethod.POST)
	@ResponseBody
	public String likeComment(HttpServletResponse resp, HttpSession session, HttpServletRequest request){
		
		if(Validator.notLogged(session)) {
			resp.setStatus(401);
		}
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		Comment comment = null;
		int points =1;
		try {
			User user=(User) session.getAttribute("user");
			 comment = cd.getCommentById(commentId, user);
			ud.insertLikedComment(user, comment);
			((HashSet<Comment>) session.getAttribute("likedComments")).add(comment);
			HashSet<Comment> dislikedComments = (HashSet<Comment>) session.getAttribute("dislikedComments");
			if(dislikedComments.contains(comment)){
				dislikedComments.remove(comment);
				session.setAttribute("dislikedComments", dislikedComments);
				points++;
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
			resp.setStatus(500);
		}
		resp.setStatus(200);
		return String.valueOf(comment.getPoints()+points);
	}
	
	@RequestMapping(value="/unlikeComment", method=RequestMethod.POST)
	@ResponseBody
	public String unlikeComment(HttpServletResponse resp, HttpSession session, HttpServletRequest request){
		if(Validator.notLogged(session)) {
			resp.setStatus(401);
		}
		Comment comment=null;
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		try {		
			User user = (User) session.getAttribute("user");
			comment = cd.getCommentById(commentId, user);
			ud.removeLikedComment(user, comment);
			((HashSet<Post>) session.getAttribute("likedComments")).remove(comment);
			} catch (SQLException e) {
				e.printStackTrace();
				resp.setStatus(500);
			}
			resp.setStatus(200);
		return String.valueOf(comment.getPoints()-1);		
	}
	
	
	@RequestMapping(value="/dislikeComment", method=RequestMethod.POST)
	@ResponseBody
	public String dislikeComment(HttpServletResponse resp, HttpSession session, HttpServletRequest request){
		if(Validator.notLogged(session)) {
			resp.setStatus(401);
		}
		
		int points=1;
					
			int commentId = Integer.parseInt(request.getParameter("commentId"));
			Comment comment = null;
			try {

				User user=(User) session.getAttribute("user");
				comment = cd.getCommentById(commentId, user);
				ud.dislikeComment(user, comment);
				((HashSet<Comment>) session.getAttribute("dislikedComments")).add(comment);
				HashSet<Comment> likedComment = (HashSet<Comment>) session.getAttribute("likedComments");
				if(likedComment.contains(comment)){
					likedComment.remove(comment);
					session.setAttribute("likedComments", likedComment);
					points++;
				}
			
		}catch (SQLException e) {
				e.printStackTrace();
				resp.setStatus(500);
			}
			resp.setStatus(200);
		return String.valueOf(comment.getPoints()-points);		
	}
	
	@RequestMapping(value="/undislikeComment", method=RequestMethod.POST)
	@ResponseBody
	public String undislikeComment(HttpServletResponse resp, HttpSession session, HttpServletRequest request){
		if(Validator.notLogged(session)) {
			resp.setStatus(401);
		}
		
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		Comment	comment = null;
		int points=1;
		try {			
			User user=(User) session.getAttribute("user");
			 comment = cd.getCommentById(commentId, user);
			ud.undislikeComment(user, comment);
			((HashSet<Comment>) session.getAttribute("dislikedComments")).remove(comment);
		}catch (SQLException e) {
				e.printStackTrace();
				resp.setStatus(500);
			}
			resp.setStatus(200);
		return String.valueOf(comment.getPoints()+points);		
	}
	
}
	
