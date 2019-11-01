package com.freesky.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MySqlConnector {

	public static void main(String[] args) {
		// String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//		String url = "jdbc:mysql://138.128.217.166:3306/coins?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST";
		String url = "jdbc:mysql://138.128.217.166:3306/coins?serverTimezone=America/New_York";
		String user = "coin";
		String password = "Whitecoin^163.com";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Connection conn = null;
		Statement stat = null;
		try {
			// Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(url, user, password);
			stat = conn.createStatement();
			// insert
			Date current = new Date();
//			String sql = String.format("insert into whitecoin(last_price, high_price, low_price, collect_time, collect_timestamp) "
//					+ " VALUES('1600', '1600', '1500',  '%s', '%s')" , sdf.format(current), sdf.format(current));
//			stat.execute(sql);
			
			
			String sql = "insert into whitecoin(last_price, high_price, low_price, collect_time, collect_timestamp) "
					+ " VALUES('1900', '1900', '1500',  ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
		
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(current);
//			ps.setDate(1, new java.sql.Date(current.getTime()));
//			ps.setTimestamp(2, new java.sql.Timestamp(current.getTime()), calendar);
//			ps.execute();
			
			
			ResultSet rs = stat.executeQuery("select * from whitecoin");
			while (rs.next()) {
				String last = rs.getString("Last_price");
				java.sql.Date date = rs.getDate("collect_time");
				java.sql.Timestamp timestamp = rs
						.getTimestamp("collect_timestamp");

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(timestamp.getTime());

				System.out.println("Last: " + last);
//				System.out.println("Collect Time: " + sdf.format(date));
				System.out.println("Collect Time: " + rs.getString("collect_time"));
				System.out
						.println("Collect TimeStamp: " + sdf.format(cal.getTime()));
			}

			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != stat) {
					stat.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
