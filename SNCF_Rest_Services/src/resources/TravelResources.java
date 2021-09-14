package resources;

import java.sql.SQLException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.Travel;
import utils.BasicReturn;
import utils.Parameters;

public class TravelResources extends ServerResource {
	@Get
	public String present(){
		Database db = null;
		Parameters param = new Parameters(this);
		
		if(!param.isSet("trainID"))
			return BasicReturn.make(false, "The train ID must be given");
		
		int trainID = param.getInt("trainID");
		
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
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		if(travel == null)
			return BasicReturn.make(false, "The provided train ID does not correspond to any travel");
		
		db.close();
		return travel.toResponseBodyFormat();
	}
}
