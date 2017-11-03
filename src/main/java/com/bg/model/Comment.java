package com.bg.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comment {
	
	private long comment_id;
	private String comment;
	private int points;
	private LocalDateTime dateTime;
	private Comment parrent_comment;
	private User user;
	private Post post;
	private ArrayList<Comment> childComments;
	
	public Comment(String comment, LocalDateTime dateTime, Comment parrent_comment, 
					User user, Post post) {
		this.comment = comment;
//		this.points = points;
		this.dateTime = dateTime;
		this.parrent_comment = parrent_comment;
		this.user = user;
		this.post = post;
	}
	
	public Comment(long comment_id, String comment,int points,LocalDateTime dateTime, Comment parrent_comment, 
			User user, Post post){
		this(comment, dateTime,parrent_comment,user,post);
		this.points=points;
		this.comment_id=comment_id;
	}
	public Comment(long comment_id, String comment,int points,LocalDateTime dateTime, Comment parrent_comment, 
			User user){
		this.comment_id=comment_id;
		this.comment=comment;
		this.points=points;
		this.dateTime=dateTime;
		this.parrent_comment=parrent_comment;
		this.user=user;
	}
	
	public Comment(long comment_id, String comment, int points, LocalDateTime dateTime, Comment parrent_comment, 
			User user, Post post,ArrayList<Comment> childComments) {
		this(comment, dateTime, parrent_comment, user, post);
		this.comment_id = comment_id;
		this.points = points;
		this.childComments=childComments;
	}

	
	
	public long getComment_id() {
		return comment_id;
	}

	public void setComment_id(long comment_id) {
		this.comment_id = comment_id;
	}

	public String getComment() {
		return comment;
	}

	public int getPoints() {
		return points;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public Comment getParrent_comment() {
		return parrent_comment;
	}

	public User getUser() {
		return user;
	}

	public Post getPost() {
		return post;
	}
	public ArrayList<Comment> getChildComments() {
		return childComments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (comment_id ^ (comment_id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (comment_id != other.comment_id)
			return false;
		return true;
	}
	

	
	

}