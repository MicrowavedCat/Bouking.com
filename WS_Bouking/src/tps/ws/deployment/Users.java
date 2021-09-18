package tps.ws.deployment;

import model.BodyParser;
import model.Booking;
import model.Database;
import model.Token;
import model.User;
import utils.BasicReturn;
import utils.TokenCheck;
import utils.WebService;

public class Users {	
	public String connect(String mail, String password) {
		Database db = new Database();
		
		if(mail == null)
			return BasicReturn.make(false, "The email must be given");
		
		if(password == null)
			return BasicReturn.make(false, "The email password be given");
		
		User user = null;
		try {	
			user = db.getUserInfo(mail);
		} catch (Exception e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		if(user == null)
			return BasicReturn.make(false, "Unknown user email");
		
		if(!mail.equals(user.getMail()) || !password.equals(user.getPassword()))
			return BasicReturn.make(false, "Wrong credentials");
		
		Token token = null;
		try {	
			token = db.createToken();
		} catch (Exception e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		db.close();
		
		if(token == null)
			return BasicReturn.make(false, "Could not create token");

		return BasicReturn.make(true, token.getAccessToken());
	}

	public String user(String accessToken, String mail) {
		Database db = new Database();
		
		if(accessToken == null)
			return BasicReturn.make(false, "The accessToken must be given");
		
		if(mail == null)
			return BasicReturn.make(false, "The email must be given");

		BodyParser bpt = new BodyParser(TokenCheck.check(accessToken));
		if(bpt.get("success") == null || bpt.get("success").equals("false"))
			return bpt.make();
		
		User user = null;
		try {	
			user = db.getUserInfo(mail);
		} catch (Exception e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		if(user == null)
			return BasicReturn.make(false, "Unknown user email");
		
		db.close();
		
		return user.toResponseBodyFormat();
	}

	public String userTickets(String accessToken, String mail) {
		Database db = new Database();
		
		if(accessToken == null)
			return BasicReturn.make(false, "The accessToken must be given");
		
		if(mail == null)
			return BasicReturn.make(false, "The email must be given");

		BodyParser bpt = new BodyParser(TokenCheck.check(accessToken));
		if(bpt.get("success") == null || bpt.get("success").equals("false"))
			return bpt.make();
		
		Booking[] bookings = null;
		try {	
			bookings = db.getUserBookingsInfo(mail);
		} catch (Exception e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		db.close();

		StringBuilder s = new StringBuilder();
		
		if(bookings != null) {
			for(Booking b : bookings) { 
				try {
					String res = WebService.GET(b.getWebService(), "/tickets", Integer.toString(b.getTicketID()));
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
