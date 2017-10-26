package com.bg.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpvoteDao {
	@Autowired
	DBManager db;
	@Autowired
	PostDao pd;
	
	public void insertLikedPost(User user,Post post) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("INSERT INTO 9gag.upvotes(user_id, post_id, upvote_date)"
												+ " VALUES(?, ?, ?)");
		ps.setLong(1, user.getId());
		ps.setLong(2, post.getPostId());
		ps.setTimestamp(3, Timestamp.valueOf(post.getDateTime()));
		ps.executeUpdate();
	}
	
	public HashSet<Post> getLikedPosts(User user) throws SQLException{
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT post_id, upvote_date FROM 9gag.upvotes "
													+ "WHERE user_id = ?");
//	TODO:												+ "ORDER BY upvote_date");
		ps.setLong(1, user.getId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		
		HashSet<Post> likedPosts = new HashSet<>();
		while(rs.next()) {
			String ldt = rs.getDate("upvote_date").toString();
			//
			//Is there a point to add the user in the constructor?
			likedPosts.add(pd.getPost(rs.getLong("post_id"), user));
		}
		return likedPosts;
	}
}

//PostDao.getInstance().getPost(rs.getLong("post_id"), u), rs.getTimestamp("upvote_date").toLocalDateTime()
