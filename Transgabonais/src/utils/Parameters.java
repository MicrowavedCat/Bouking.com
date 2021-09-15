package utils;

import org.restlet.resource.ServerResource;

import model.TicketClass;

public class Parameters {
	private ServerResource sr;
	
	public Parameters(ServerResource sr) {
		this.sr = sr;
	}
	
	private String getParameter(String param) {
		String attr = (String) this.sr.getRequestAttributes().get(param);
		
		return (attr == null ? (String) this.sr.getQueryValue(param) : attr);
	}
	
	public boolean isSet(String param) {
		return (this.getParameter(param) != null);
	}
	
	public int getInt(String param) {		
		return (this.isSet(param) ? Integer.parseInt(this.getParameter(param)) : -1);
	}
	
	public long getLong(String param) {		
		return (this.isSet(param) ? Long.parseLong(this.getParameter(param)) : -1);
	}
	
	public String getString(String param) {
		return this.getParameter(param);
	}
	
	public TicketClass getTicketClass(String param) {
		return TicketClass.toEnum(this.getParameter(param));
	}
}
