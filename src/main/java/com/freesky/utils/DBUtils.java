package com.freesky.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBUtils {
//	private static final String url = "jdbc:mysql://138.128.217.166:3306/coins?useSSL=false&serverTimezone=America/New_York";
//	private static final String user = "coin";
//	private static final String password = "Whitecoin^163.com";
	
	private static final String url = "jdbc:mysql://localhost:3306/coins?useSSL=false&serverTimezone=America/New_York";
	private static final String user = "root";
	private static final String password = "Whitecoin@163.com";
	
	private static Logger logger = Logger.getLogger(DBUtils.class);
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (null != conn) {
			return conn;
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			logger.error("Error when connet MySQL: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("com.mysql.jdbc.Driver NOT Found: " + e.getMessage());
		}
		
		return conn;
	}
	
	public static void saveXwc(String last, String high, String low, String bid, String ask, String collectTime) {
		Connection conn = getConnection();
		if (null == conn) {
			logger.warn("Database connection is null. Do nothing!");
			return;
		}
		
		String sql = "insert into whitecoin(last_price, high_price, low_price, bid_price, ask_price, collect_time) "
				+ " values(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, last);
			ps.setString(2, high);
			ps.setString(3, low);
			ps.setString(4, bid);
			ps.setString(5, ask);
			ps.setString(6, collectTime);
			
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			logger.error("Error inserting whitecoin: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		DBUtils.saveXwc("0.00001700", "0.00001740", "0.00001521", "0.00001700", "0.00001704", "2018-06-30 20:03:52");
		System.out.println("done");
	}

}
