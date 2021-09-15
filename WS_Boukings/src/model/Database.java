package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	private final static String URL = "jdbc:mariadb://192.168.192.5:3306/";
	private final static String USER = "wsclient";
	private final static String PASSWORD = "wsclient";
	private final static String DB = "Boukings";
	
	private static Connection CONNECTION;
	
	private static HashMap<String, User> USERS = new HashMap<>();
	private static HashMap<Integer, Booking> BOOKINGS = new HashMap<>();
	
	public Database() throws SQLException {
		//CONNECTION = DriverManager.getConnection(URL + DB + "?user=" + USER + "&password=" + PASSWORD);
		USERS.put("louka.doz@ensiie.fr", new User("louka.doz@ensiie.fr", "Louka", "DOZ", "louka"));
		USERS.put("julien.carcau@ensiie.fr", new User("julien.carcau@ensiie.fr", "Julien", "CARCAU", "julien"));
		
		//BOOKINGS.put(1, new Booking(1, 1, "louka.doz@ensiie.fr", "localhost:8081"));
		//BOOKINGS.put(2, new Booking(2, 2, "louka.doz@ensiie.fr", "localhost:8081"));
		BOOKINGS.put(3, new Booking(3, 3, "julien.carcau@ensiie.fr", "localhost:8081"));
	}
	
	public User getUserInfo(String mail) throws SQLException {
		/*Statement stmt = CONNECTION.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE mail = " + mail);
		User user = null;
		
		while(rs.next()) {
			user = new User(rs);
		}
		
		rs.close();
		stmt.close();*/
		
		return USERS.get(mail);
	}
	
	public Booking getBookingInfo(int bookingID) throws SQLException {
		/*Statement stmt = CONNECTION.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Bookings WHERE booking_id = " + bookingID);
		Booking booking = null;
		
		while(rs.next()) {
			booking = new Booking(rs);
		}
		
		rs.close();
		stmt.close();*/
		
		return BOOKINGS.get(bookingID);
	}
	
	public Booking[] getUserBookingsInfo(String mail) throws SQLException {
		/*StringBuilder sql = new StringBuilder("SELECT * FROM Bookings WHERE mail = " + mail);
		
		Statement stmt = CONNECTION.createStatement();
		ResultSet rs = stmt.executeQuery(sql.toString());
		ArrayList<Booking> bookings = new ArrayList<>();
		
		while(rs.next()) {
			bookings.add(new Booking(rs));
		}
		
		rs.close();
		stmt.close();
		
		return (Booking[]) bookings.toArray(new Booking[bookings.size()]);*/
		
		ArrayList<Integer> ids = new ArrayList<>();
		
		BOOKINGS.forEach((k, v) -> {
			System.out.println("FOREACH =========== "+k);
			System.out.println(v);
			if(v.getUserID().equals(mail))
				ids.add(k);
		});
		
		Booking[] bookings = new Booking[ids.size()];
		
		for(int i = 0; i < ids.size(); i++)
			bookings[i] = BOOKINGS.get(ids.get(i));
		
		return bookings;
	}
	
	public String addBooking(int ticketID, String userID, String webService) throws SQLException {
		BOOKINGS.put(BOOKINGS.size() + 1, new Booking(BOOKINGS.size() + 1, ticketID, userID, webService));
		System.out.println("ADD =========== "+BOOKINGS.size() + 1+" "+ticketID+" "+userID+" "+webService);
		
		return Integer.toString(BOOKINGS.size() + 1);
	}
	
	public void close() {
		/*try {
			CONNECTION.close();
		} catch (SQLException e) {
			System.err.println("Database close error");
			e.printStackTrace();
		}*/
	}
}
