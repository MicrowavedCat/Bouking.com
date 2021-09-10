package routing;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import resources.TrainResources;
import resources.TravelsResources;
 
public class RouterApplication extends Application{
	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a new respective instance of resource.
		Router router = new Router(getContext());
		
		//Routes
		router.attach("/travels", TravelsResources.class);
		router.attach("/travels/{trainID}", TrainResources.class);
		
		return router;
	}
}
