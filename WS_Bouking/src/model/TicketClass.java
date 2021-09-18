package model;

public enum TicketClass {
	FIRST,
	BUSINESS,
	STANDARD;
	
	public static TicketClass toEnum(String s) {
		switch(s) {
			case "FIRST":
				return FIRST;
			case "BUSINESS":
				return BUSINESS;
			case "STANDARD":
				return STANDARD;
			default:
				return null;
		}
	}
	
	public static String toString(TicketClass tc) {
		return tc + "";
	}
}
