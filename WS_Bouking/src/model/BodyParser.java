package model;

import java.util.ArrayList;
import java.util.HashMap;

public class BodyParser {
	private ArrayList<BodyBlock> blocks;
	private int current;
	
	public BodyParser() {
		this.blocks = new ArrayList<>();
		this.current = -1;
	}
	
	public BodyParser(String body) {
		this();
		this.parse(body);
	}
	
	public void newBlock() {
		this.blocks.add(new BodyBlock());
	}
	
	public int count() {
		return this.blocks.size();
	}

	public void put(String key, String value) {
		this.blocks.get(this.blocks.size() - 1).put(key, value);
	}
	
	public boolean next() {
		if(this.current == this.blocks.size() - 1)
			return false;
		
		this.current++;
		return true;
	}
	
	public String get(String key) {
		if(this.current < 0 && !this.next())
			return null;
		
		return this.blocks.get(this.current).get(key);
	}
	
	public void parse(String body) {
		String line;
		int index;
		BodyBlock bb = new BodyBlock();
		
		while(body.length() > 0) {
			index = body.indexOf('\n');
			
			if(index < 0)
				break;
				
			line = body.substring(0, index);
			body = index + 1 >= body.length() ? "" : body.substring(index + 1);
			
			if(line.equals("")) {
				this.blocks.add(bb);
				bb = new BodyBlock();
			} else {
				int index2 = line.indexOf(':');
				bb.put(line.substring(0, index2), line.substring(index2 + 1));
			}
		}
	}
	
	public String make() {
		StringBuilder s = new StringBuilder();
		
		for(BodyBlock b : this.blocks) {
			s.append(b.make()).append("\n");
		}
		
		return s.toString();
	}

	private class BodyBlock {
		private HashMap<String, String> attributes;
		
		public BodyBlock() {
			this.attributes = new HashMap<>();
		}
		
		public void put(String key, String value) {
			this.attributes.put(key, value);
		}
		
		public String get(String key) {
			return this.attributes.get(key);
		}
		
		public String make() {
			StringBuilder s = new StringBuilder();
			
			this.attributes.forEach((k, v) -> {
				s.append(k).append(":").append(v).append("\n");
			});
			
			return s.toString();
		}
	}
}
