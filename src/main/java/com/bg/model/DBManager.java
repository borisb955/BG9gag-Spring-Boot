package com.bg.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBManager {
	

	public DBManager() {
	}
	
	
	public Connection getConn() {
		final String DB_IP = "localhost";
		final String DB_PORT = "3306";
		final String DB_DBNAME = "9gag";
		final String DB_USER = "root";
		final String DB_PASS = "vipmilan";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_DBNAME
						,DB_USER, DB_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("VLizash li bret");
		return conn;
	}
	
//	public void closeConnection() {
//		if(conn != null) {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
