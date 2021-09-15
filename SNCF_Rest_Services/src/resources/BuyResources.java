package resources;

import java.sql.SQLException;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import model.Database;
import model.TicketClass;
import model.Travel;
import utils.BasicReturn;
import utils.Parameters;

public class BuyResources extends ServerResource {
	@Post
	public String accept(Representation entity){	
		Database db = null;
		Form form = new Form(entity);
		Parameters param = new Parameters(this);
		
		if(!param.isSet("trainID"))
			return BasicReturn.make(false, "The train ID must be given");

        int trainID = param.getInt("trainID");
        boolean flexible = Boolean.parseBoolean(form.getFirstValue("flexible"));
        TicketClass ticketClass = TicketClass.toEnum(form.getFirstValue("ticketClass"));

		if(ticketClass == null)
			return BasicReturn.make(false, "Unknown ticket class");
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		Travel travel;
		try {
			travel = db.getTravelInfo(trainID);
		} catch (SQLException e) {
			System.err.println("Database travel request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		if(travel == null)
			return BasicReturn.make(false, "The provided train ID does not correspond to any travel");
		
		if(!travel.hasEnoughPlace(1, ticketClass))
			return BasicReturn.make(false, "Not enough place in this train");
		
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
		
		String ticketID = null;
		try {
			 ticketID = db.buy(trainID, ticketClass, flexible, price);
		} catch (SQLException e) {
			System.err.println("Database buy request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		if(ticketID == null)
			return BasicReturn.make(false, "The ticket could not be bought");
		
		db.close();
		return BasicReturn.make(true, ticketID);
	}
}
