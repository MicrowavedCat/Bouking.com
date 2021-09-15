package tps.ws.deployment;

import java.sql.SQLException;
import java.util.HashMap;

import model.BodyParser;
import model.DBInstance;
import model.Database;
import model.TicketClass;
import model.Travel;
import model.User;
import utils.BasicReturn;
import utils.DateConverter;
import utils.WebService;

public class Bookings {
	public String travels(String departureStation, String arrivalStation, long departureDate, long arrivalDate, int nbFirstCLass, int nbBusinessClass, int nbStandardClass){
		System.out.println(departureStation);
		System.out.println(arrivalStation);
		System.out.println(departureDate);
		System.out.println(arrivalDate);
		System.out.println(nbFirstCLass);
		System.out.println(nbBusinessClass);
		System.out.println(nbStandardClass);
		
		try {
			String res = WebService.GET(WebService.SNCF, "/travels", null);	
			System.out.println(res);
			BodyParser bp = new BodyParser(res);
			
			boolean go = bp.next();
			if(bp.get("success") != null && bp.get("success").equals("false"))
				return res;
			
			StringBuilder s = new StringBuilder();
			while(go) {
				System.out.println(departureStation + " " + bp.get("departureStation"));
				System.out.println(arrivalStation + " " + bp.get("arrivalStation"));
				System.out.println(departureDate + " " + bp.get("departureDate") + " " + DateConverter.getEndOfDay(departureDate));
				System.out.println(arrivalDate + " " + bp.get("arrivalDate") + " " + DateConverter.getEndOfDay(arrivalDate));
				System.out.println(nbFirstCLass + " " + bp.get("numberOfFirstClass"));
				System.out.println(nbBusinessClass + " " + bp.get("numberOfBusinessClass"));
				System.out.println(nbStandardClass + " " + bp.get("numberOfStandardClass"));
				System.out.println();
				
				if(
					( departureStation == null || departureStation.equals("") || departureStation.equals(bp.get("departureStation")) )
					&& ( arrivalStation == null || arrivalStation.equals("") || arrivalStation.equals(bp.get("arrivalStation")) )
					&& ( departureDate <= 0 || (departureDate <= Long.parseLong(bp.get("departureDate")) && DateConverter.getEndOfDay(departureDate) > Long.parseLong(bp.get("departureDate"))) )
					&& ( arrivalDate <= 0 || (arrivalDate <= Long.parseLong(bp.get("arrivalDate")) && DateConverter.getEndOfDay(arrivalDate) > Long.parseLong(bp.get("arrivalDate"))) )
					&& ( nbFirstCLass <= 0 || nbFirstCLass <= Integer.parseInt(bp.get("numberOfFirstClass")) )
					&& ( nbBusinessClass <= 0 || nbBusinessClass <= Integer.parseInt(bp.get("numberOfBusinessClass")) )
					&& ( nbStandardClass <= 0 || nbStandardClass <= Integer.parseInt(bp.get("numberOfStandardClass")) )
				){
					s.append(new Travel(bp).toResponseBodyFormat());
				}

				go = bp.next();
			}
				
			return s.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return BasicReturn.make(false, "Could not communicate with other services");
		}
	}
	
	public String travel(int trainID){		
		try {
			String res = WebService.GET(WebService.SNCF, "/travels", Integer.toString(trainID));
			BodyParser bp = new BodyParser(res);
			
			bp.next();
			if(bp.get("success") != null && bp.get("success").equals("false"))
				return res;
				
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return BasicReturn.make(false, "Could not communicate with other services");
		}
	}
	
	public String buy(int trainID, String ticketClass, String flexible, String mail){		
		boolean flexibleParse = Boolean.parseBoolean(flexible);
		TicketClass ticketClassParse = TicketClass.toEnum(ticketClass);

		if(ticketClassParse == null)
			return BasicReturn.make(false, "Unknown ticket class");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("ticketClass", TicketClass.toString(ticketClassParse));
		params.put("flexible", Boolean.toString(flexibleParse));
		
		try {
			//Cannot use POST because of org.restlet.resource.ResourceException: Method Not Allowed (405) - The method specified in the request is not allowed for the resource identified by the request URI
			String res = WebService.POST(WebService.SNCF, "/buy", Integer.toString(trainID), params);
			BodyParser bp = new BodyParser(res);
			
			bp.next();
			if(bp.get("success") != null && bp.get("success").equals("true")) {
				Database db;
				try {
					db = DBInstance.getInstance();
				} catch (SQLException e) {
					System.err.println("Database connection error :");
					e.printStackTrace();
					return BasicReturn.make(false, "Database error");
				}
				
				String bookingID = null;
				try {	
					bookingID = db.addBooking(Integer.parseInt(bp.get("reason")), mail, WebService.SNCF);
				} catch (SQLException e) {
					System.err.println("Database request error :");
					e.printStackTrace();
					return BasicReturn.make(false, "Database error");
				}
				
				if(bookingID == null)
					return BasicReturn.make(false, "Unable to create booking's info");
				
				db.close();
				
				return BasicReturn.make(true, bookingID);
			}
				
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return BasicReturn.make(false, "Could not communicate with other services");
		}
	}
}
