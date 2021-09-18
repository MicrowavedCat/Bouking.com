package utils;

import model.Database;
import model.Token;

public class TokenCheck {
	public static String check(String accessToken) {
		Database db;
		
		try {
			db = new Database();
		} catch (Exception e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		Token token = null;
		try {	
			token = db.getTokenInfo(accessToken);
		} catch (Exception e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		db.close();
		
		if(token == null)
			return BasicReturn.make(false, "Unknown access token");
		
		if(token.getExpirationDate() <= DateConverter.getToday())
			return BasicReturn.make(false, "Acss token has expired");
		
		return BasicReturn.make(true);
	}
}
