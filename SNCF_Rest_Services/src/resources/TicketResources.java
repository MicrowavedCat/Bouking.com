package resources;

import java.sql.SQLException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.Ticket;
import model.Travel;
import utils.Parameters;

public class TicketResources extends ServerResource {
	@Get
	public String present(){
		Database db = null;
		Parameters param = new Parameters(this);
		
		if(!param.isSet("ticketID"))
			throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "The ticket ID must be given"));
		
		int trainID = param.getInt("ticketID");
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		Ticket ticket;
		try {
			ticket = db.getTicketInfo(trainID);
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		if(ticket == null)
			throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "The provided ticket ID does not correspond to any ticket"));
		
		db.close();
		return ticket.toResponseBodyFormat();
	}
}
