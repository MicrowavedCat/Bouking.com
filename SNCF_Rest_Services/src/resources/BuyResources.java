package resources;

import java.sql.SQLException;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.TicketClass;
import model.Travel;

public class BuyResources extends ServerResource {
	@Post
	public String accept(Representation entity){
		Database db = null;
		Form form = new Form(entity);
		
		if(!form.contains("trainID"))
			throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "The train ID must be given"));

        int trainID = Integer.parseInt(form.getFirstValue("trainID"));
        boolean flexible = Boolean.parseBoolean(form.getFirstValue("flexible"));
        TicketClass ticketClass = TicketClass.toEnum(form.getFirstValue("ticketClass"));

		if(ticketClass == null)
			throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "Unknown ticket class"));
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		Travel travel;
		try {
			travel = db.getTravelInfo(trainID);
		} catch (SQLException e) {
			System.err.println("Database travel request error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		if(travel == null)
			throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "The provided train ID does not correspond to any travel"));
		
		int price = 0;
		switch(ticketClass) {
			case FIRST:
				price = travel.getFirstClassPrice(flexible);
				break;
			case BUSINESS:
				price = travel.getBusinessClassPrice(flexible);
				break;
			case STANDARD:
				price = travel.getStandardClassPrice(flexible);
				break;
		}
		
		try {
			db.buy(trainID, ticketClass, flexible, price);
		} catch (SQLException e) {
			System.err.println("Database buy request error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		db.close();
		return travel.toResponseBodyFormat();
	}
}
