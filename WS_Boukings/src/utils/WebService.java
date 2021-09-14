package utils;

import java.util.HashMap;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;

public class WebService {
	public final static String SNCF = "localhost:8081";
	
	
	public static String GET(String webService, String URI, String URIParam, HashMap<String, String> params) throws Exception {
		StringBuilder s = new StringBuilder("http://" + webService + URI);
		
		if(URIParam != null)
			s.append("/" + URIParam);
		
		if(params != null && params.size() > 0) {
			s.append("?");
			
			int i = 0;
			params.forEach((k, v) -> {
				s.append(k + "=" + v);
				
				if(i < params.size() - 1)
					s.append("&");
			});
		}
				
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
				
		return new ClientResource(s.toString()).post(form).getText();
	}
}