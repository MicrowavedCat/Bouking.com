package tps.ws.deployment;


import java.util.HashMap;

import model.BodyParser;
import model.Database;
import model.TicketClass;
import model.Travel;
import utils.BasicReturn;
import utils.DateConverter;
import utils.TokenCheck;
import utils.WebService;

public class Bookings {
	public String travels(String accessToken, String departureStation, String arrivalStation, long departureDate, long arrivalDate, int nbFirstCLass, int nbBusinessClass, int nbStandardClass){
		System.out.println(departureStation);
		System.out.println(arrivalStation);
		System.out.println(departureDate);
		System.out.println(arrivalDate);
		System.out.println(nbFirstCLass);
		System.out.println(nbBusinessClass);
		System.out.println(nbStandardClass);
		
		if(accessToken == null)
			return BasicReturn.make(false, "The accessToken must be given");
		
		BodyParser bpt = new BodyParser(TokenCheck.check(accessToken));
		if(bpt.get("success") == null || bpt.get("success").equals("false"))
			return bpt.make();
		
		try {
			String[] ws = WebService.getWebServices();
			StringBuilder res = new StringBuilder();
			BodyParser bp;
			
			for(String s: ws) {
				String rtrn = WebService.GET(s, "/travels", null);	
				System.out.println(rtrn);
				bp = new BodyParser(rtrn);
				
				boolean go = bp.next();
				if(bp.get("success") != null && bp.get("success").equals("false"))
					continue;
				
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
						res.append(new Travel(s, bp).toResponseBodyFormat());
					}

					go = bp.next();
				}
			}
				
			return res.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return BasicReturn.make(false, "Could not communicate with other services");
		}
	}
	
	public String travel(String accessToken, String webService, int trainID){
		if(accessToken == null)
			return BasicReturn.make(false, "The accessToken must be given");
		
		BodyParser bpt = new BodyParser(TokenCheck.check(accessToken));
		if(bpt.get("success") == null || bpt.get("success").equals("false"))
			return bpt.make();
		
		try {
			String res = WebService.GET(webService, "/travels", Integer.toString(trainID));
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
	
	public String buy(String accessToken, String webService, int trainID, String ticketClass, String flexible, String mail){
		if(accessToken == null)
			return BasicReturn.make(false, "The accessToken must be given");
		
		BodyParser bpt = new BodyParser(TokenCheck.check(accessToken));
		if(bpt.get("success") == null || bpt.get("success").equals("false"))
			return bpt.make();
		
		boolean flexibleParse = Boolean.parseBoolean(flexible);
		TicketClass ticketClassParse = TicketClass.toEnum(ticketClass);

		if(ticketClassParse == null)
			return BasicReturn.make(false, "Unknown ticket class");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("ticketClass", TicketClass.toString(ticketClassParse));
		params.put("flexible", Boolean.toString(flexibleParse));
		
		try {
			//Cannot use POST because of org.restlet.resource.ResourceException: Method Not Allowed (405) - The method specified in the request is not allowed for the resource identified by the request URI
			String res = WebService.POST(webService, "/buy", Integer.toString(trainID), params);
			BodyParser bp = new BodyParser(res);
			
			bp.next();
			if(bp.get("success") != null && bp.get("success").equals("true")) {
				Database db = new Database();
				
				String bookingID = null;
				try {	
					bookingID = db.addBooking(Integer.parseInt(bp.get("reason")), mail, webService);
				} catch (Exception e) {
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
