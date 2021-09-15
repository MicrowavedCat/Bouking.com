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
		
		try {
			db = new Database();
		} catch (SQLException e) {
			System.err.println("Database connection error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		Travel[] travels = null;
		try {
			travels = db.getTravelsInfo();
		} catch (SQLException e) {
			System.err.println("Database request error :");
			e.printStackTrace();
			return BasicReturn.make(false, "Database error");
		}
		
		db.close();
		
		StringBuilder s = new StringBuilder("");
		
		if(travels != null) {
			for(Travel t : travels)
				s.append(t.toResponseBodyFormat());
		}
		
		return (s.toString().equals("") ? BasicReturn.make(false, "No train found") : s.toString());
	}
}
