package model;

import java.util.HashMap;

public class BodyParser {
	private HashMap<String, String> attributes;
	
	public BodyParser() {
		this.attributes = new HashMap<>();
	}
	
	public BodyParser(String body) {
		this.parse(body);
	}
	
	public void parse(String body) {
		
	}
}
