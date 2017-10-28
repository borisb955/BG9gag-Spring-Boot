package com.bg.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileDao {
	@Autowired
	DBManager db;
	@Autowired
	SocialNetworkingDao snd;
	@Autowired
	UserDao ud;
	
	public void insertProfile(Profile p, User u) throws SQLException {
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO 9gag.profiles(avatar_url, full_name, gender, "
				+ "date_of_birth, info ) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		java.util.Date utilDate = p.getDateOfBirth();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		ps.setString(1, p.getAvatarUrl());
		ps.setString(2, p.getFullName());
		ps.setString(3, p.getGender());
		ps.setDate(4, sqlDate);
		ps.setString(5, p.getInfo());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		long profileId = rs.getLong(1);
		p.setProfileId(profileId);
		
		System.out.println("profile");
		System.out.println("profile");
		System.out.println("profile");
		
		ud.insertProfileId(u, profileId);
	}
	
	
	public Profile getProfile(long profileID) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("SELECT profile_id, avatar_url, full_name, gender, info, date_of_birth"
				+ ", social_net_id FROM 9gag.profiles WHERE profile_id = ?");
		ps.setLong(1, profileID);
		ResultSet rs = ps.executeQuery();
	
		
		if(rs.next()) {
			//TODO: is there a better way to check if empty
			return new Profile(rs.getLong("profile_id") != 0 ? rs.getLong("profile_id") : null,
					rs.getString("avatar_url") != null ? rs.getString("avatar_url") : null, 
					rs.getString("full_name") != null ? rs.getString("full_name") : null, 
					rs.getString("gender") != null ? rs.getString("gender") : null,
					rs.getString("date_of_birth") != null ? rs.getDate("date_of_birth") : null, 
					rs.getString("info") != null ? rs.getString("info") : null);
		}
		return null;
	}


	public void changeAvatar(String avatarUrl, User u) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.profiles "
												   + "SET avatar_url = ? "
												   + "WHERE profile_id = ?");
		ps.setString(1, avatarUrl);
		ps.setLong(2, u.getProfile().getProfileId());
		ps.executeUpdate();
	}


	public void changeFullName(String fullName, User u) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.profiles "
												   + "SET full_name = ? "
												   + "WHERE profile_id = ?");
		System.out.println(fullName);
		System.out.println(u.getProfile().getProfileId());
		ps.setString(1, fullName);
		ps.setLong(2, u.getProfile().getProfileId());
		ps.executeUpdate();
		
	}


	public void changeGender(String gender, User u) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.profiles "
												   + "SET gender = ? "
												   + "WHERE profile_id = ?");
		ps.setString(1, gender);
		ps.setLong(2, u.getProfile().getProfileId());
		ps.executeUpdate();
		
	}


	public void changeDateOfBirth(Date dateOfBirth, User u) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.profiles "
												   + "SET date_of_birth = ? "
												   + "WHERE profile_id = ?");
		java.util.Date utilDate = dateOfBirth;
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		ps.setDate(1, sqlDate);
		ps.setLong(2, u.getProfile().getProfileId());
		ps.executeUpdate();
	}


	public void changeInfo(String info, User u) throws SQLException {
		Connection conn = db.getConn();
		
		PreparedStatement ps = conn.prepareStatement("UPDATE 9gag.profiles "
												   + "SET info = ? "
												   + "WHERE profile_id = ?");
		ps.setString(1, info);
		ps.setLong(2, u.getProfile().getProfileId());
		ps.executeUpdate();
		
	}
}
