package com.bg.model;

import static org.mockito.Matchers.anyDouble;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bg.util.Encrypter;

//import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

@Component
public class UserDao {
	@Autowired
	DBManager db;
	@Autowired
	UpvoteDao ud;
	@Autowired
	PostDao pd;
	@Autowired
	CommentDao cd;
	@Autowired
	ProfileDao profiled;
	
	public void insertUser(User u) throws SQLException {
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO 9gag.users(username, password, email) "
												+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, u.getUsername());
		ps.setString(2, Encrypter.encrypt(u.getPassword()));
		ps.setString(3, u.getEmail());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		u.setId(rs.getLong(1));
	}
	
	public void insertProfileId(User u, long profileId) throws SQLException {
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.users "
												   + "SET profile_id = ? "
												   + "WHERE user_id = ?");
		ps.setLong(1, profileId);
		ps.setLong(2, u.getId());
		ps.executeUpdate();
		
		System.out.println("user");
		System.out.println("user");
		System.out.println("user");
	}
	
	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
		    emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		   return result;
	}
	
	public boolean emailExists(String email) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(1) FROM 9gag.users WHERE email = ?");
		ps.setString(1, email);
		ps.executeQuery();
		
		ResultSet rs = ps.getResultSet();
		rs.next();
		int count = rs.getInt(1);
		
		
		return count > 0;
	}
	
	public boolean userExists(String username) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(1) FROM 9gag.users WHERE username = ?");
		ps.setString(1, username);
		ps.executeQuery();
		
		ResultSet rs = ps.getResultSet();
		rs.next();
		int count = rs.getInt(1);
			
		return count > 0;
	}

	public boolean passwordMatch(String email, String writtenPass) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT password FROM 9gag.users WHERE email = ?");
		ps.setString(1, email);
		ps.executeQuery();
		
		ResultSet rs = ps.getResultSet();
		rs.next();
		String realPass = rs.getString(1);
		
		if(realPass.equals(Encrypter.encrypt(writtenPass))) {
			System.out.println(realPass);
			System.out.println(Encrypter.encrypt(writtenPass));
			return true;
		}
		return false;
	}
	
	public void changeUsername(long userId, String newUserName) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.users "
												   + "SET username = ? "
												   + "WHERE user_id = ?");
		ps.setString(1, newUserName);
		ps.setLong(2, userId);
		ps.executeUpdate();
	}
	
	public void changeEmail(long userId, String newEmail) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.users "
												   + "SET email = ? "
												   + "WHERE user_id = ?");
		ps.setString(1, newEmail);
		ps.setLong(2, userId);
		ps.executeUpdate();
	}
	
	public void changePassword(long userId, String pass) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.users "
												   + "SET password = ? "
												   + "WHERE user_id = ?");
		ps.setString(1, Encrypter.encrypt(pass));
		ps.setLong(2, userId);
		ps.executeUpdate();
	}
	
	public void forgottenPass(String email, String pass) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.users "
												   + "SET password = ? "
												   + "WHERE email = ?");
		ps.setString(1, Encrypter.encrypt(pass));
		ps.setString(2, email);
		ps.executeUpdate();
	}
	
	//TODO: do we really need all the info when reg (collections)?
	public User getFullUser(String username) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT user_id , username, password, email, "
									+ "upvotes_hidden, profile_id FROM 9gag.users WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		User u = getUserById(rs.getLong("user_id"));
		
		u.setLikedPosts(ud.getLikedPosts(u));
		u.setPosts(pd.getAllPostsForUser(u));
		u.setComments(cd.getAllCommentsForUser(u));
		return u;
	}
	
	public User getFullUserByEmail(String email) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT user_id ,username, password, email, "
									+ "upvotes_hidden, profile_id FROM 9gag.users WHERE email = ?");
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		User u = getUserById(rs.getLong("user_id"));
		
		u.setLikedPosts(ud.getLikedPosts(u));
		u.setPosts(pd.getAllPostsForUser(u));
		u.setComments(cd.getAllCommentsForUser(u));
		
		long profileId = rs.getLong("profile_id");
		if(profileId != 0) {
			u.setProfile(profiled.getProfile(profileId));
		}

		return u;
	}
	
	public User getUserById(long userId) throws SQLException {
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT username, password, email, upvotes_hidden,"
				+ " profile_id FROM 9gag.users WHERE user_id = ?");
		ps.setLong(1, userId);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		User u = new User(userId,rs.getString("username"));
		return new User(userId, 
						rs.getString("username"), 
						rs.getString("password"), 
						rs.getString("email"));
	}
}
