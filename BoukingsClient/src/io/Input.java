package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import utils.DateConvert;

public class Input {
	private final static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
	
	public static String readString() {
		try {
			return READER.readLine();
		} catch (IOException e) {
			System.err.println("Reade line error");
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static long askDate() {
		String d = "";
		String m = "";
		String y = "";
		String h = "";
		String min = "";
		
		Output.ask("Jour");
		d = Input.readString();
		if(d.equals("")) return -1;
		Output.ask("Mois");
		m = Input.readString();
		if(m.equals("")) return -1;
		Output.ask("Année");
		y = Input.readString();
		if(y.equals("")) return -1;
		Output.ask("Heure");
		h = Input.readString();
		if(h.equals("")) return -1;
		Output.ask("Minute");
		min = Input.readString();
		if(min.equals("")) return -1;
		
		return DateConvert.toLong(Integer.parseInt(y), Integer.parseInt(m), Integer.parseInt(d), Integer.parseInt(h), Integer.parseInt(min), 0);
	}
}
