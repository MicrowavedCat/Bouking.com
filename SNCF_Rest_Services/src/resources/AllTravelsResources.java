package resources;

import java.sql.SQLException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.TicketClass;
import model.Travel;
import utils.BasicReturn;
import utils.Parameters;

public class AllTravelsResources extends ServerResource {
	@Get
	public String present(){
		Database db = null;
		Parameters param = new Parameters(this);
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		Travel[] travels = null;
		try {
			int nbTicketsFirst = 0;
			int nbTicketsBusiness = 0;
			int nbTicketsStandard = 0;
			
			if(param.isSet("nbTicketsFirst")) {
				nbTicketsFirst = param.getInt("nbTicketsFirst");
				
				if(nbTicketsFirst < 1)
					return BasicReturn.make(false, "The number of first class tickets requested cannot be inferior to 1");
			}
			
			if(param.isSet("nbTicketsBusiness")) {
				nbTicketsBusiness = param.getInt("nbTicketsBusiness");
				
				if(nbTicketsBusiness < 1)
					return BasicReturn.make(false, "The number of business class tickets requested cannot be inferior to 1");
			}
			
			if(param.isSet("nbTicketsStandard")) {
				nbTicketsStandard = param.getInt("nbTicketsStandard");
				
				if(nbTicketsStandard < 1)
					return BasicReturn.make(false, "The number of standard class tickets requested cannot be inferior to 1");
			}

			String departureStation = param.isSet("departureStation") ? param.getString("departureStation") : null;
			String arrivalStation = param.isSet("arrivalStation") ? param.getString("arrivalStation") : null;
			long departureDate = param.isSet("departureDate") ? param.getLong("departureDate") : -1;
			long arrivalDate = param.isSet("arrivalDate") ? param.getLong("arrivalDate") : -1;

			System.out.println("nbtf = " + nbTicketsFirst);
			System.out.println("nbtb = " + nbTicketsBusiness);
			System.out.println("nbts = " + nbTicketsStandard);
			System.out.println("ds = " + departureStation);
			System.out.println("as = " + arrivalStation);
			System.out.println("dd = " + departureDate);
			System.out.println("ad = " + arrivalDate);

			travels = db.getTravelsInfo(nbTicketsFirst, nbTicketsBusiness, nbTicketsStandard, departureStation, arrivalStation, departureDate, arrivalDate);
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		db.close();
		
		StringBuilder s = new StringBuilder();
		
		if(travels != null) {
			for(Travel t : travels)
				s.append(t.toResponseBodyFormat());
		}
		
		return s.toString();
	}
}
