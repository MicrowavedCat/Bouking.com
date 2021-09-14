package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	private final static String URL = "jdbc:mariadb://192.168.192.5:3306/";
	private final static String USER = "wsclient";
	private final static String PASSWORD = "wsclient";
	private final static String DB = "Boukings";
	
	private static Connection CONNECTION;
	
	public Database() throws SQLException {
		//CONNECTION = DriverManager.getConnection(URL + DB + "?user=" + USER + "&password=" + PASSWORD);
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
		
		User user = null;
		
		if(mail.equals("louka.doz@ensiie.fr"))
			user = new User("louka.doz@ensiie.fr", "Louka", "DOZ", "louka");
		else if(mail.equals("julien.carcau@ensiie.fr"))
			user = new User("julien.carcau@ensiie.fr", "Julien", "CARCAU", "julien");
		
		return user;
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

		Booking booking = null;
		
		if(bookingID == 1)
			booking = new Booking(bookingID, 4789, "louka.doz@ensiie.fr", "localhost:8081");
		else if(bookingID == 2)
			booking = new Booking(bookingID, 1456, "julien.carcau@ensiie.fr", "localhost:8081");
		
		return booking;
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
		
		Booking[] bookings = new Booking[0];
		
		if(mail.equals("louka.doz@ensiie.fr")) {
			bookings = new Booking[2];
			bookings[0] = new Booking(1, 1, "louka.doz@ensiie.fr", "localhost:8081");
			bookings[1] = new Booking(1, 2, "louka.doz@ensiie.fr", "localhost:8081");
		} else if(mail.equals("julien.carcau@ensiie.fr")) {
			bookings = new Booking[1];
			bookings[0] = new Booking(2, 3, "julien.carcau@ensiie.fr", "localhost:8081");
		}
		
		return bookings;
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
