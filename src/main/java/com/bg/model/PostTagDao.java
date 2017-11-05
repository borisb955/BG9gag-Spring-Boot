package com.bg.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostTagDao {
	@Autowired
	DBManager db;
	@Autowired
	UserDao ud;
	@Autowired
	TagDao td;
	
	public void insertPostTag(Post p, Tag t) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("INSERT INTO 9gag.posts_tags(post_id, tag_id) "
												   + "VALUES(?, ?)");
		Tag tag = td.getTagByName(t.getTagName());
		System.out.println(p.getPostId());
		System.out.println(p.getPostId());
		System.out.println(tag.getTagId());
		System.out.println(tag.getTagId());
		ps.setLong(1, p.getPostId());
		ps.setLong(2, tag.getTagId());
		ps.executeUpdate();
	}
	
	//TODO: Consider removing
	public ArrayList<Post> getAllPostsForTag(Tag tag) throws SQLException {
		Connection conn = db.getConn();
		//tags <-> posts_tags <-> posts
		PreparedStatement ps = conn.prepareStatement("SELECT p.*"
												+ "FROM 9gag.tags as t "
												+ "JOIN 9gag.posts_tags as pt "
												+ "ON t.tag_id = pt.tag_id "
												+ "WHERE t.tag_name = ? "
												+ "JOIN 9gag.posts as p "
												+ "ON pt.post_id = p.post_id "
												+ "ORDER BY p.upload_date");
		ps.setString(1, tag.getTagName());
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Post> posts = new ArrayList<>();
		while(rs.next()) {
			long postId = rs.getLong("p.post_id");
			posts.add(new Post(postId, 
							   rs.getString("p.description"), 
							   rs.getString("post_url"), 
							   rs.getInt("p.points"), 
							   rs.getTimestamp("p.upload_date").toLocalDateTime(), 
							   rs.getBoolean("p.is_video"),
							   rs.getBoolean("p.youtube"),
							   ud.getUserById(rs.getLong("p.user_id")),
							   getTagsForPost(postId),null));
		}
		return posts;
	}

	public ArrayList<Tag> getTagsForPost(long postId) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT t.tag_id, t.tag_name FROM 9gag.tags as t "
												   + "JOIN 9gag.posts_tags as pt "
												   + "ON t.tag_id = pt.tag_id "
												   + "WHERE pt.post_id = ?");
		ps.setLong(1, postId);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Tag> tags = new ArrayList<>();
		while(rs.next()) {
			tags.add(new Tag(rs.getLong("t.tag_id"), rs.getString("t.tag_name")));
		}
		return tags;
	}
	
	public ArrayList<Post> getPostsForTag(String tagName) throws SQLException{
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT p.* FROM 9gag.tags as t "
													+ "JOIN 9gag.posts_tags as pt "
													+ "ON t.tag_id = pt.tag_id "
													+ "JOIN 9gag.posts as p "
													+ "ON pt.post_id = p.post_id "
													+ "WHERE t.tag_name = ?");
		ps.setString(1, tagName);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Post> posts = new ArrayList<>();

		while(rs.next()) {
			posts.add(new Post(rs.getLong("p.post_id"), 
					  rs.getString("p.description"), 
					  rs.getString("p.post_url"), 
					  rs.getInt("p.points"), 
					  rs.getTimestamp("p.upload_date").toLocalDateTime(),
					  rs.getBoolean("p.is_video"),
					  rs.getBoolean("p.youtube"),
					  ud.getUserById(rs.getLong("p.user_id")), 
					  getTagsForPost(rs.getLong("p.post_id")), null));
		}
		return posts;
		
	}
}
