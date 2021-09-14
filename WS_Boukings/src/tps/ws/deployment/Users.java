package tps.ws.deployment;

import java.sql.SQLException;

import model.BodyParser;
import model.Booking;
import model.Database;
import model.User;
import utils.BasicReturn;
import utils.WebService;

public class Users {
	public String connect(String mail, String password) {
		Database db;
		
		if(mail == null)
			return BasicReturn.make(false, "The email must be given");
		
		if(password == null)
			return BasicReturn.make(false, "The email password be given");
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		User user = null;
		try {	
			user = db.getUserInfo(mail);
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		if(user == null)
			return BasicReturn.make(false, "Unknown user email");
		
		db.close();
		
		if(!mail.equals(user.getMail()) || !password.equals(user.getPassword()))
			return BasicReturn.make(false, "Wrong credentials");
		
		BodyParser bp = new BodyParser();
		bp.newBlock();
		bp.put("token", "no use token");
		
		return bp.make();
	}

	public String user(String mail) {
		Database db;
		
		if(mail == null)
			return BasicReturn.make(false, "The email must be given");
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		User user = null;
		try {	
			user = db.getUserInfo(mail);
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		if(user == null)
			return BasicReturn.make(false, "Unknown user email");
		
		db.close();
		
		return user.toResponseBodyFormat();
	}

	public String userTickets(String mail) {
		Database db;
		
		if(mail == null)
			return BasicReturn.make(false, "The email must be given");
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		Booking[] bookings = null;
		try {	
			bookings = db.getUserBookingsInfo(mail);
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		db.close();

		StringBuilder s = new StringBuilder();
		
		if(bookings != null) {
			for(Booking b : bookings) { 
				try {
					String res = WebService.GET(b.getWebService(), "/tickets", Integer.toString(b.getTicketID()), null);
					BodyParser bp = new BodyParser(res);
					
					bp.next();
					if(bp.get("success") != null && bp.get("success").equals("false"))
						return BasicReturn.make(false, "Failed on ticket " + b.getTicketID() + " -> " + bp.get("reason"));
						
					s.append(res);
				} catch (Exception e) {
					e.printStackTrace();
					return BasicReturn.make(false, "Could not communicate with other services");
				}
			}
		}
		
		return s.toString();
	}
}
