package com.bg.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post{
	private long postId;
	private String description;
	private String postUrl;
	private int points;
	private boolean isVideo;
	private LocalDateTime dateTime;
	private User user;
	private ArrayList<Tag> tags;
	private ArrayList<Comment> comments;
	
	public Post(String text, String postUrl, LocalDateTime dateTime, Boolean isVideo ,User user) {
		this.description = text;
		this.postUrl = postUrl;
		this.dateTime = dateTime;
		this.isVideo = isVideo;
		this.user = user;
		this.points = 0;
	}

	public Post(long postId, String text, String postUrl, int points, LocalDateTime dateTime, 
			boolean isVideo, User user, ArrayList<Tag> tags, ArrayList<Comment> comments) {
		this(text, postUrl, dateTime, isVideo, user);
		this.postId = postId;
		this.points = points;
		this.tags = tags;
		this.comments=comments;
	}
	
	
	
	public void setPostId(long post_id) {
		this.postId = post_id;
	}

	public long getPostId() {
		return postId;
	}

	public String getDescription() {
		return description;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public User getUser() {
		return user;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean isVideo() {
		return isVideo;
	}
	
	public ArrayList<Tag> getTags() {
		return tags;
	}
	public ArrayList<Comment> getComments() {
		return comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (postId ^ (postId >>> 32));
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
		Post other = (Post) obj;
		if (postId != other.postId)
			return false;
		return true;
	}

	
	
}
