package resources;

import java.sql.SQLException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.Ticket;
import model.Travel;
import utils.BasicReturn;
import utils.Parameters;

public class TicketResources extends ServerResource {
	@Get
	public String present(){
		Database db = null;
		Parameters param = new Parameters(this);
		
		if(!param.isSet("ticketID"))
			return BasicReturn.make(false, "The ticket ID must be given");
		
		int trainID = param.getInt("ticketID");
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		Ticket ticket;
		try {
			ticket = db.getTicketInfo(trainID);
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		if(ticket == null)
			return BasicReturn.make(false, "The provided ticket ID does not correspond to any ticket");
		
		db.close();
		return ticket.toResponseBodyFormat();
	}
}
