package resources;

import java.sql.SQLException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.Travel;
import utils.Parameters;

public class TravelResources extends ServerResource {
	@Get
	public String present(){
		Database db = null;
		Parameters param = new Parameters(this);
		
		if(!param.isSet("trainID"))
			throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "The train ID must be given"));
		
		int trainID = param.getInt("trainID");
		
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
			System.err.println("Database request error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		if(travel == null)
			throw new ResourceException(new Status(Status.CLIENT_ERROR_BAD_REQUEST, "The provided train ID does not correspond to any travel"));
		
		db.close();
		return travel.toResponseBodyFormat();
	}
}
