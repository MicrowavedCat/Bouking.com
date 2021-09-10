package resources;

import java.sql.SQLException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.Travel;

public class TrainResources extends ServerResource {
	@Get
	public String present(){
		Database db = null;
		int trainID = Integer.parseInt((String) getRequestAttributes().get("trainID"));
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		Travel travel;
		try {
			travel = db.getTravelsInfo(trainID);
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database error"));
		}
		
		db.close();
		return travel.toResponseBodyFormat();
	}
}
