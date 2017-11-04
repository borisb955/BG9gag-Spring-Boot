package com.bg.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//import sun.security.action.GetLongAction;

@Component
public class CommentDao {
	@Autowired
	DBManager db;
	@Autowired
	PostDao pd;
	@Autowired
	UserDao ud;
	@Autowired
	PostTagDao ptd;
	
	public  void insertComment(Comment com) throws SQLException {
		Connection conn = db.getConn();
		if(com.getParrent_comment()!=null){
		PreparedStatement ps = conn.prepareStatement("INSERT INTO 9gag.comments(comment, points, issue_date,"
													+ "parrent_comment, post_id, user_id) "
													+ "VALUES(?, ?, ?, ?, ?, ?)", 
													Statement.RETURN_GENERATED_KEYS);
		
		
		ps.setString(1, com.getComment());
		ps.setInt(2, com.getPoints());
		ps.setTimestamp(3, Timestamp.valueOf(com.getDateTime()));
		ps.setLong(4, com.getParrent_comment().getComment_id());
		ps.setLong(5, com.getPost().getPostId());
		ps.setLong(6, com.getUser().getId());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		com.setComment_id(rs.getLong(1));
		}else{
			
		PreparedStatement ps = conn.prepareStatement("INSERT INTO 9gag.comments(comment, points, issue_date"
									+ ", user_id, post_id) "
									+ "VALUES(?, ?, ?, ?, ?)", 
									Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, com.getComment());
		ps.setInt(2, com.getPoints());
		ps.setTimestamp(3, Timestamp.valueOf(com.getDateTime()));
		ps.setLong(4, com.getUser().getId());
		ps.setLong(5, com.getPost().getPostId());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		com.setComment_id(rs.getLong(1));
		}
	}
	
//	public boolean parrentCommentExists() { check if parent comment is null}

	public ArrayList<Comment> getAllCommentsForUser(User u) throws SQLException {
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT c1.comment_id, c1.comment, c1.points, "
											+ "c1.issue_date, c1.parrent_comment, c1.post_id, "
											+ "c2.comment_id, c2.comment, c2.points, c2.issue_date, "
											+ "c2.parrent_comment, c2.post_id, c2.user_id "
											+ "FROM 9gag.comments as c1 "
											+ "JOIN 9gag.comments as c2 "
											+ "ON c1.parrent_comment = c2.comment_id "
											+ "WHERE c1.user_id = ? "
											+ "ORDER BY c1.issue_date");
		ps.setLong(1, u.getId());
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Comment> comments = new ArrayList<>();
		while(rs.next()) {
			//the parrentComment doesn't have parrent_comment so it's null
			Comment parrentComment = new Comment(rs.getLong("c2.comment_id"), 
												 rs.getString("c2.comment"),
												 rs.getInt("c2.points"),
												 rs.getTimestamp("c2.issue_date").toLocalDateTime(), 
												 null,
												 ud.getUserById(rs.getLong("c2.user_id")), 
												 pd.getPost(rs.getLong("c2.post_id"), u));
			
			comments.add(new Comment(rs.getLong("c1.comment_id"), 
									 rs.getString("c1.comment"),
									 rs.getInt("c1.points"),
									 rs.getTimestamp("c1.issue_date").toLocalDateTime(), 
									 parrentComment, 
									 u, 
									 pd.getPost(rs.getLong("c1.post_id"), u)));
		}
		return comments;
	}
	
		public ArrayList<Comment> getMainCommentsForPost(Post post) throws SQLException{
			
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement("SELECT comment_id, comment, points, issue_date, user_id"
					+ " from comments"
					+ " where post_id=? and parrent_comment IS NULL;");
			
			ps.setLong(1, post.getPostId());
			ResultSet rs = ps.executeQuery();
			ArrayList<Comment> comments = new ArrayList<>();
			while(rs.next()) {
				User user =  ud.getUserById(rs.getLong("user_id"));
				Comment parrentComment = new Comment(rs.getLong("comment_id"), 
													 rs.getString("comment"),
													 rs.getInt("points"),
													 rs.getTimestamp("issue_date").toLocalDateTime(), 
													 null,
													 user,
													post, 
													getChildCommentsForComment(rs.getLong("comment_id"),post));
				
				comments.add(parrentComment);
			}
			return comments;
		
		}
	
		public ArrayList<Comment> getChildCommentsForComment(long id,Post post) throws SQLException{
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement("SELECT comment_id, comment, points, issue_date, user_id, post_id"
					+ " from 9gag.comments"
					+ " where parrent_comment=?;");
			
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
		
			ArrayList<Comment> childComments =new ArrayList<>();
			while(rs.next()) {
				System.out.println(rs.getInt("points"));
				User u = ud.getUserById(rs.getLong("user_id"));
					childComments.add(new Comment(rs.getLong("comment_id"),
															rs.getString("comment"),
															rs.getInt("points"),
															rs.getTimestamp("issue_date").toLocalDateTime(),
															getCommentById(id,u,post),
															u,
															post
															));
			}
			return childComments;
		}
		
		public Comment getCommentById(long id,User u,Post p) throws SQLException{
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement("SELECT  comment, points,issue_date, user_id"
					+ " from comments"
					+ " where comment_id=?;");	
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return new Comment(id,rs.getString("comment"),rs.getInt("points"),rs.getTimestamp("issue_date").toLocalDateTime(),null,u,p);
			}
			return null;
		}
		public Comment getCommentById(long id,User u) throws SQLException{
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement("SELECT  comment, points,issue_date, user_id"
					+ " from comments"
					+ " where comment_id=?;");	
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return new Comment(id,rs.getString("comment"),rs.getInt("points"),rs.getTimestamp("issue_date").toLocalDateTime(),null,u);
			}
			return null;
		}
		
		public HashSet<Post> getCommentedPostsByUser(User u) throws SQLException{
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement("SELECT p.post_id, p.description, p.post_url, p.points, "
					+ "p.upload_date, p.is_video FROM 9gag.posts as p  "
					+ "JOIN 9gag.comments as c "
					+ "ON p.post_id = c.post_id "
					+ "WHERE c.user_id = ? "
					+ "GROUP BY post_id");
			
			ps.setLong(1, u.getId());
			
			ResultSet rs = ps.executeQuery();
			
			HashSet<Post> posts = new HashSet<>();
			while(rs.next()) {
				posts.add(new Post(rs.getLong("p.post_id"), 
								   rs.getString("p.description"), 
								   rs.getString("p.post_url"), 
								   rs.getInt("p.points"), 
								   rs.getTimestamp("p.upload_date").toLocalDateTime(), 
								   rs.getBoolean("is_video"),
								   u, 
								   ptd.getTagsForPost(rs.getLong("p.post_id")),
								   null));
			}
			
			return posts;
		}
}
