package resources;

import java.sql.SQLException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import model.Database;
import model.TicketClass;

public class TrainResources extends ServerResource {
	@Get
	public String present(){
		Database db = null;
		int trainID = (int) getRequestAttributes().get("trainID");
		
		try {
			db = new Database();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ResourceException(new Status(Status.SERVER_ERROR_INTERNAL, "Database connection error"));
		}
		
		
		
		db.close();
		return "SNCF\n Paris\n La Creuse :(\n 2021-12-12 23:59:59\n 2021-12-12 23:59:59\n 50\n 1 100\n 2 120\n 3 140\n 15\n\n"
					+ "SNCF\n Paris\n La Creuse :(\n 2021-12-12 23:59:59\n 2021-12-12 23:59:59\n 50\n 1 100\n 2 120\n 3 140\n 15\n\n";
	}
}
