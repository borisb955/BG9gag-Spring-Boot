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
	@Autowired
	CommentDao cd;
	
	public void undislikePost(User user, Post post) throws SQLException{
			Connection conn = db.getConn();		
			PreparedStatement ps = conn.prepareStatement("DELETE FROM 9gag.upvotes where user_id = ? and post_id = ?; ");
			ps.setLong(1, user.getId());
			ps.setLong(2, post.getPostId());
			ps.executeUpdate();
			
			ps = conn.prepareStatement("SELECT points FROM 9gag.posts WHERE post_id= ?");
			ps.setLong(1, post.getPostId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int points = rs.getInt("points");
			points++;
			ps = conn.prepareStatement("UPDATE 9gag.posts SET points = ? WHERE post_id = ?;");
					ps.setInt(1, points);
					ps.setLong(2, post.getPostId());
					ps.executeUpdate();	
	}
	
	
	public void dislikePost(User user, Post post) throws SQLException{
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(user_id) as number FROM 9gag.upvotes where user_id = ? and post_id = ?; ");
		ps.setLong(1, user.getId());
		ps.setLong(2, post.getPostId());
		ResultSet rs = ps.executeQuery();
		System.out.println("ne gotovo 1");
		rs.next();
		if(rs.getInt("number")>0){
			ps = conn.prepareStatement("SELECT points FROM 9gag.posts WHERE post_id = ?");
			ps.setLong(1, post.getPostId());
			 rs = ps.executeQuery();
			rs.next();
			int points = rs.getInt("points");
			points-=2;
			System.out.println("ne gotovo 2");
			ps = conn.prepareStatement("UPDATE 9gag.posts SET points = ? WHERE post_id = ?;");
					ps.setInt(1, points);
					ps.setLong(2, post.getPostId());
					ps.executeUpdate();
					System.out.println("ne gotovo 3");
					ps = conn.prepareStatement("UPDATE 9gag.upvotes SET type = -1 WHERE user_id = ? and post_id = ?;");
					ps.setLong(1, user.getId());
					ps.setLong(2, post.getPostId());
					ps.executeUpdate();			
				System.out.println("gotovo za DISLIKE");		
		}else{
			ps = conn.prepareStatement("SELECT points FROM 9gag.posts WHERE post_id = ?");
			ps.setLong(1, post.getPostId());
			 rs = ps.executeQuery();
			rs.next();
			int points = rs.getInt("points");
			points--;
			System.out.println("ne gotovo 2");
			ps = conn.prepareStatement("UPDATE 9gag.posts SET points = ? WHERE post_id = ?;");
					ps.setInt(1, points);
					ps.setLong(2, post.getPostId());
					ps.executeUpdate();
					System.out.println("ne gotovo 3");
					 ps = conn.prepareStatement("INSERT INTO 9gag.upvotes(user_id, post_id, upvote_date, type)"
							+ " VALUES(?, ?, ?,?)");
							ps.setLong(1, user.getId());
							ps.setLong(2, post.getPostId());
							ps.setTimestamp(3, Timestamp.valueOf(post.getDateTime()));
							ps.setInt(4, -1);
							ps.executeUpdate();		
				System.out.println("gotovo za DISLIKE");	
		}
	}
	
	
	public void removeLikedPost(User user, Post post) throws SQLException{
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("DELETE FROM 9gag.upvotes where user_id = ? and post_id = ?; ");
		ps.setLong(1, user.getId());
		ps.setLong(2, post.getPostId());
		ps.executeUpdate();
		
		ps = conn.prepareStatement("SELECT points FROM 9gag.posts WHERE post_id= ?");
		ps.setLong(1, post.getPostId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		int points = rs.getInt("points");
		points--;
		ps = conn.prepareStatement("UPDATE 9gag.posts SET points = ? WHERE post_id = ?;");
				ps.setInt(1, points);
				ps.setLong(2, post.getPostId());
				ps.executeUpdate();	
	}
	
	public void insertLikedPost(User user,Post post) throws SQLException {//rabotii
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(user_id) as number FROM 9gag.upvotes where user_id = ? and post_id = ?; ");
		ps.setLong(1, user.getId());
		ps.setLong(2, post.getPostId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		if(rs.getInt("number")>0){
			ps = conn.prepareStatement("SELECT points FROM 9gag.posts WHERE post_id = ?");
			ps.setLong(1, post.getPostId());
			 rs = ps.executeQuery();
			rs.next();
			int points = rs.getInt("points");
			points+=2;
			ps = conn.prepareStatement("UPDATE 9gag.posts SET points = ? WHERE post_id = ?;");
					ps.setInt(1, points);
					ps.setLong(2, post.getPostId());
					ps.executeUpdate();
					ps = conn.prepareStatement("UPDATE 9gag.upvotes SET type = 1 WHERE user_id = ? and post_id = ?;");
					ps.setLong(1, user.getId());
					ps.setLong(2, post.getPostId());
					ps.executeUpdate();	
		}else{		
				 ps = conn.prepareStatement("INSERT INTO 9gag.upvotes(user_id, post_id, upvote_date, type)"
							+ " VALUES(?, ?, ?,?)");
					ps.setLong(1, user.getId());
					ps.setLong(2, post.getPostId());
					ps.setTimestamp(3, Timestamp.valueOf(post.getDateTime()));
					ps.setInt(4, 1);
					ps.executeUpdate();
					
					ps = conn.prepareStatement("SELECT points FROM 9gag.posts WHERE post_id= ?");
					ps.setLong(1, post.getPostId());
					rs = ps.executeQuery();
					rs.next();
					int points = rs.getInt("points");
					points++;
					ps = conn.prepareStatement("UPDATE 9gag.posts SET points = ? WHERE post_id = ?;");
					ps.setInt(1, points);
					ps.setLong(2, post.getPostId());
					ps.executeUpdate();
			
		}
	}
	
	public void insertLikedComment(User user,Comment comment) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(user_id) as number FROM 9gag.upvotes_comments where user_id = ? and comment_id = ?; ");
		ps.setLong(1, user.getId());
		ps.setLong(2, comment.getComment_id());
		ResultSet rs = ps.executeQuery();
		rs.next();
		if(rs.getInt("number")>0){
			ps = conn.prepareStatement("SELECT points FROM 9gag.comments WHERE comment_id = ?");
			ps.setLong(1, comment.getComment_id());
			 rs = ps.executeQuery();
			rs.next();
			int points = rs.getInt("points");
			points+=2;
			ps = conn.prepareStatement("UPDATE 9gag.comments SET points = ? WHERE comment_id = ?;");
			ps.setInt(1, points);
			ps.setLong(2,comment.getComment_id());
			ps.executeUpdate();
			ps = conn.prepareStatement("UPDATE 9gag.upvotes_comments SET type = 1 WHERE user_id = ? and comment_id = ?;");
			ps.setLong(1, user.getId());
			ps.setLong(2, comment.getComment_id());
			ps.executeUpdate();	
		}else{
			 ps = conn.prepareStatement("INSERT INTO 9gag.upvotes_comments(user_id, comment_id, upvote_date, type)"
						+ " VALUES(?, ?, ?, ?)");
				ps.setLong(1, user.getId());
				ps.setLong(2, comment.getComment_id());
				ps.setTimestamp(3, Timestamp.valueOf(comment.getDateTime()));
				ps.setInt(4, 1);
				ps.executeUpdate();
				ps = conn.prepareStatement("SELECT points FROM 9gag.comments WHERE comment_id= ?");
				ps.setLong(1, comment.getComment_id());
				rs = ps.executeQuery();
				rs.next();
				int points = rs.getInt("points");
				points++;
				ps = conn.prepareStatement("UPDATE 9gag.comments SET points = ? WHERE comment_id = ?;");
				ps.setInt(1, points);
				ps.setLong(2, comment.getComment_id());
				ps.executeUpdate();
		}
		
		
		
		
		
		
		
		
		
	}

	
	public void removeLikedComment(User user,Comment comment) throws SQLException{
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("DELETE FROM 9gag.upvotes_comments where user_id = ? and comment_id = ?; ");
		ps.setLong(1, user.getId());
		ps.setLong(2, comment.getComment_id());
		ps.executeUpdate();
		
		ps = conn.prepareStatement("SELECT points FROM 9gag.comments WHERE comment_id= ?;");
		ps.setLong(1, comment.getComment_id());
		ResultSet rs = ps.executeQuery();
		rs.next();
		int points = rs.getInt("points");
		points--;
		ps = conn.prepareStatement("UPDATE 9gag.comments SET points = ? WHERE comment_id = ?;");
				ps.setInt(1, points);
				ps.setLong(2, comment.getComment_id());
				ps.executeUpdate();
	}
	
	public void dislikeComment(User user, Comment comment) throws SQLException{
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(user_id) as number FROM 9gag.upvotes_comments where user_id = ? and comment_id = ?; ");
		ps.setLong(1, user.getId());
		ps.setLong(2, comment.getComment_id());
		ResultSet rs = ps.executeQuery();
		rs.next();
		if(rs.getInt("number")>0){
			ps = conn.prepareStatement("SELECT points FROM 9gag.comments WHERE comment_id = ?");
			ps.setLong(1, comment.getComment_id());
			 rs = ps.executeQuery();
			rs.next();
			int points = rs.getInt("points");
			points-=2;
			ps = conn.prepareStatement("UPDATE 9gag.comments SET points = ? WHERE comment_id = ?;");
					ps.setInt(1, points);
					ps.setLong(2, comment.getComment_id());
					ps.executeUpdate();
					ps = conn.prepareStatement("UPDATE 9gag.upvotes_comments SET type = -1 WHERE user_id = ? and comment_id = ?;");
					ps.setLong(1, user.getId());
					ps.setLong(2, comment.getComment_id());
					ps.executeUpdate();					
		}else{
			ps = conn.prepareStatement("SELECT points FROM 9gag.comments WHERE comment_id = ?");
			ps.setLong(1, comment.getComment_id());
			 rs = ps.executeQuery();
			rs.next();
			int points = rs.getInt("points");
			points--;
			ps = conn.prepareStatement("UPDATE 9gag.comments SET points = ? WHERE comment_id = ?;");
					ps.setInt(1, points);
					ps.setLong(2, comment.getComment_id());
					ps.executeUpdate();
					 ps = conn.prepareStatement("INSERT INTO 9gag.upvotes_comments(user_id, comment_id, upvote_date, type)"
							+ " VALUES(?, ?, ?,?)");
							ps.setLong(1, user.getId());
							ps.setLong(2, comment.getComment_id());
							ps.setTimestamp(3, Timestamp.valueOf(comment.getDateTime()));
							ps.setInt(4, -1);
							ps.executeUpdate();			
		}
	}
	
	public void undislikeComment(User user, Comment comment) throws SQLException{
		Connection conn = db.getConn();		
		PreparedStatement ps = conn.prepareStatement("DELETE FROM 9gag.upvotes_comments where user_id = ? and comment_id = ?; ");
		ps.setLong(1, user.getId());
		ps.setLong(2, comment.getComment_id());
		ps.executeUpdate();
		
		ps = conn.prepareStatement("SELECT points FROM 9gag.comments WHERE comment_id= ?");
		ps.setLong(1, comment.getComment_id());
		ResultSet rs = ps.executeQuery();
		rs.next();
		int points = rs.getInt("points");
		points++;
		ps = conn.prepareStatement("UPDATE 9gag.comments SET points = ? WHERE comment_id = ?;");
				ps.setInt(1, points);
				ps.setLong(2, comment.getComment_id());
				ps.executeUpdate();	
}
	
	public HashSet<Post> getLikedPosts(User user) throws SQLException{
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT post_id, upvote_date FROM 9gag.upvotes "
													+ "WHERE user_id = ?");
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
	public HashSet<Comment> getLikedComments(User user) throws SQLException{
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT comment_id, upvote_date FROM 9gag.upvotes_comments "
													+ "WHERE user_id = ?");
		
		ps.setLong(1, user.getId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		
		HashSet<Comment> likedComments = new HashSet<>();
		while(rs.next()) {
			String ldt = rs.getDate("upvote_date").toString();
			likedComments.add(cd.getCommentById(rs.getLong("comment_id"), user));
		}
		return likedComments;
	}

}

//PostDao.getInstance().getPost(rs.getLong("post_id"), u), rs.getTimestamp("upvote_date").toLocalDateTime()
