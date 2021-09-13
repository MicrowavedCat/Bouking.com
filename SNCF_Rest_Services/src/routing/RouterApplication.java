package routing;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import resources.TravelResources;
import resources.AllTravelsResources;
import resources.BuyResources;
import resources.TicketResources;
 
public class RouterApplication extends Application{
	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a new respective instance of resource.
		Router router = new Router(getContext());
		
		//Routes
		router.attach("/travels", AllTravelsResources.class);
		router.attach("/travels/{trainID}", TravelResources.class);
		router.attach("/buy/{trainID}", BuyResources.class);
		router.attach("/tickets/{ticketID}", TicketResources.class);
		
		return router;
	}
}
