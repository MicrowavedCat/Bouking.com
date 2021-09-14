package tps.ws.deployment;

import java.util.HashMap;

import model.BodyParser;
import model.TicketClass;
import utils.BasicReturn;
import utils.WebService;

public class Bookings {
	public String travels(String departureStation, String arrivalStation, long departureDate, long arrivalDate, int nbFirstCLass, int nbBusinessClass, int nbStandardClass){
		HashMap<String, String> params = new HashMap<>();
		if(departureStation != null && !departureStation.equals("")) params.put("departureStation", departureStation);
		if(arrivalStation != null && !arrivalStation.equals("")) params.put("arrivalStation", arrivalStation);
		if(departureDate > 0) params.put("departureDate", Long.toString(departureDate));
		if(arrivalDate > 0) params.put("arrivalDate", Long.toString(arrivalDate));
		if(nbFirstCLass > 0) params.put("nbFirstCLass", Integer.toString(nbFirstCLass));
		if(nbBusinessClass > 0) params.put("nbBusinessClass", Integer.toString(nbBusinessClass));
		if(nbStandardClass > 0) params.put("nbStandardClass", Integer.toString(nbStandardClass));
		
		try {
			String res = WebService.GET(WebService.SNCF, "/travels", null, params);			
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
	
	public String travel(int trainID){		
		try {
			String res = WebService.GET(WebService.SNCF, "/travels", Integer.toString(trainID), null);
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
	
	public String buy(int trainID, String ticketClass, String flexible){		
		boolean flexibleParse = Boolean.parseBoolean(flexible);
		TicketClass ticketClassParse = TicketClass.toEnum(ticketClass);

		if(ticketClassParse == null)
			return BasicReturn.make(false, "Unknown ticket class");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("ticketClass", TicketClass.toString(ticketClassParse));
		params.put("flexible", Boolean.toString(flexibleParse));
		
		try {
			//Cannot use POST because of org.restlet.resource.ResourceException: Method Not Allowed (405) - The method specified in the request is not allowed for the resource identified by the request URI
			String res = WebService.GET(WebService.SNCF, "/travels", Integer.toString(trainID), params);
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
}
