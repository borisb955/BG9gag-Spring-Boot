package com.bg.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocialNetworkingDao {
	@Autowired
	DBManager db;
	
	public void insertUser(SocialNetworking sn) throws SQLException {
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO 9gag.social_networking(facebook, google)"
				+ " VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setBoolean(1, sn.isFacebook());
		ps.setBoolean(1, sn.isGoogle());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		sn.setSocNetId(rs.getLong("soc_net_id"));
	}

	public SocialNetworking getSocNet(long socNetId) throws SQLException {
		Connection conn = db.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT facebook_connected, google_connected "
				+ "FROM 9gag.social_networking WHERE social_net_id = ?");
		ps.setLong(1, socNetId);
		ResultSet rs = ps.executeQuery();
		
		return new SocialNetworking(rs.getBoolean("facebook_connected"), 
									rs.getBoolean("google_connected"));
	}
}
