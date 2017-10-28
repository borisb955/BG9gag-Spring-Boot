package com.bg.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import javax.validation.constraints.Size;

import com.bg.util.isValidEmail;

public class User {
	private long id;
	private String username;
	private String password;
	@isValidEmail
	@Size(min=5)
	private String email;
	private Profile profile;
	private HashSet<Post> likedPosts;
	private ArrayList<Post> posts;
	private ArrayList<Comment> comments;
	
	public User() {
		
	}
	
	public User(long id, String username) {
		this.id = id;
		this.username = username;
	}
	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	public User(Long id, String username, String password, String email, HashSet<Post> likedPosts) {
		this(username, password, email);
		this.profile = profile;
		this.id = id;
		this.likedPosts=likedPosts;
	}
	public User(Long id, String username, String password, String email, Profile profile, HashSet<Post> likedPosts) {
		this(username, password, email);
		this.profile = profile;
		this.id = id;
		this.likedPosts=likedPosts;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	
	public void setLikedPosts(HashSet<Post> likedPosts) {
		this.likedPosts = likedPosts;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public long getId() {
		return id;
	}
	public Profile getProfile() {
		return profile;
	}
	public HashSet<Post> getLikedPosts() {
		return likedPosts;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
