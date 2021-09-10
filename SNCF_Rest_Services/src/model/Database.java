package model;

import java.sql.*;

public class Database {
	private final static String URL = "jdbc:mariadb://192.168.192.13:3306/";
	private final static String USER = "wsclient";
	private final static String PASSWORD = "wsclient";
	private final static String COMPANY = "SNCF";
	
	private static Connection CONNECTION;
	
	public Database() throws SQLException {
		CONNECTION = DriverManager.getConnection(URL + "Boukings?user=" + USER + "&password=" + PASSWORD);
	}
	
	public Travel getTravelsInfo(int trainID) throws SQLException {
		Statement stmt = CONNECTION.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Travels WHERE train_id = " + trainID);
		
		Travel travel = null;
		while(rs.next()) {
			travel = new Travel(rs);
		}
		
		rs.close();
		stmt.close();
		
		return travel;
	}
	
	/*public String getName() throws SQLException {
		Statement stmt = CONNECTION.createStatement();
		ResultSet result = stmt.executeQuery("SELECT * FROM Users where mail = 'laprise.cassee@yahoo.com'");
		
		String fn = "";
		String ln = "";
		while(result.next()) {
			ln = result.getString("last_name");
			fn = result.getString("first_name");
		}
		
		stmt.close();
		return fn + " " + ln;
	}*/
	
	public void close() {
		try {
			CONNECTION.close();
		} catch (SQLException e) {
			System.err.println("Database close error");
			e.printStackTrace();
		}
	}
}
