package resources;

import java.sql.SQLException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.TicketClass;
import model.Travel;
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
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		Travel[] travels = null;
		try {
			int numberOfTickets = 1;
			if(param.isSet("numberOfTickets")) {
				numberOfTickets = param.getInt("numberOfTickets");
				
				if(numberOfTickets < 1)
					throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "The number of tickets cannot be inferior to 1"));
			}
			
			TicketClass ticketClass = null;
			if(param.isSet("ticketClass")) {
				ticketClass = param.getTicketClass("ticketClass");
				
				if(ticketClass == null)
					throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "The ticket class is unknown. Allowed classes : " + TicketClass.toString(TicketClass.FIRST) + " or " + TicketClass.toString(TicketClass.BUSINESS) + " or " + TicketClass.toString(TicketClass.STANDARD)));
			}

			String departureStation = param.isSet("departureStation") ? param.getString("departureStation") : null;
			String arrivalStation = param.isSet("arrivalStation") ? param.getString("arrivalStation") : null;
			String departureDate = param.isSet("departureDate") ? param.getString("departureDate") : null;
			String arrivalDate = param.isSet("arrivalDate") ? param.getString("arrivalDate") : null;

			System.out.println("not = " + numberOfTickets);
			System.out.println("tc = " + ticketClass);
			System.out.println("ds = " + departureStation);
			System.out.println("as = " + arrivalStation);
			System.out.println("dd = " + departureDate);
			System.out.println("ad = " + arrivalDate);

			travels = db.getTravelsInfo(numberOfTickets, ticketClass, departureStation, arrivalStation, departureDate, arrivalDate);
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
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
