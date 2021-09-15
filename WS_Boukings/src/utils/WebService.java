package utils;

import java.util.HashMap;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;

public class WebService {
	public final static String SNCF = "localhost:8081";
	public final static String TRANSGABONAIS = "localhost:8082";
	public final static String CAMRAIL = "localhost:8083";
	
	public static String[] getWebServices() {
		String[] s = {SNCF, TRANSGABONAIS, CAMRAIL};
		
		return s;
	}
	
	public static String GET(String webService, String URI, String URIParam) throws Exception {
		StringBuilder s = new StringBuilder("http://" + webService + URI);
		
		if(URIParam != null)
			s.append("/" + URIParam);
				
		return new ClientResource(s.toString()).get().getText();
	}
	
	public static String POST(String webService, String URI, String URIParam, HashMap<String, String> params) throws Exception {
		StringBuilder s = new StringBuilder("http://" + webService + URI);
		Form form = new Form();
		
		if(URIParam != null)
			s.append("/" + URIParam);
		
		if(params != null && params.size() > 0) {
			params.forEach((k, v) -> {
				form.add(k, v);
			});
		}
		
		System.out.println(s.toString());
				
		return new ClientResource(s.toString()).post(form).getText();
	}
}
