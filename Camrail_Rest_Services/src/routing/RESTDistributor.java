package routing;
 
import org.restlet.Component;
import org.restlet.data.Protocol;
 
public class RESTDistributor {
 
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Create a new Restlet component and add a HTTP server connector to it  
	     Component component = new Component();

	     //Prepare server on port 8080
	     component.getServers().add(Protocol.HTTP, 8083);
	     // Then attach it to the local host  
	     component.getDefaultHost().attach(new RouterApplication());  

	     //Start server
	     component.start();  
	}	 
 
}