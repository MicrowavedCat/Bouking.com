package model;

public class Booking {
	private int bookingID;
	private int ticketID;
	private String userID;
	private String webService;
	
	public Booking(BodyParser bp) {
		this.bookingID = Integer.parseInt(bp.get("bookingID"));
		this.ticketID = Integer.parseInt(bp.get("ticketID"));
		this.userID = bp.get("userID");
		this.webService = bp.get("webService");
	}

	public int getBookingID() {
		return this.bookingID;
	}

	public int getTicketID() {
		return this.ticketID;
	}

	public String getUserID() {
		return this.userID;
	}

	public String getWebService() {
		return this.webService;
	}
}
