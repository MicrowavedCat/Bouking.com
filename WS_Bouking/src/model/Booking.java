package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Booking {	
	private int bookingID;
	private int ticketID;
	private String userID;
	private String webService;
	
	public Booking(ResultSet rs) throws SQLException {
		this.bookingID = rs.getInt("booking_id");
		this.ticketID = rs.getInt("ticket_id");
		this.userID = rs.getString("user_id");
		this.webService = rs.getString("web_service");
	}
	
	/**
	 * Use if no database available
	 */
	public Booking(int bookingID, int ticketID, String userID, String webService) {
		this.bookingID = bookingID;
		this.ticketID = ticketID;
		this.userID = userID;
		this.webService =webService;
	}

	public int getTicketID() {
		return this.ticketID;
	}

	public String getWebService() {
		return this.webService;
	}

	public Object getUserID() {
		return this.userID;
	}
	
	public String toResponseBodyFormat() {
		BodyParser bp = new BodyParser();
		
		bp.newBlock();
		bp.put("bookingID", Integer.toString(this.bookingID));
		bp.put("ticketID", Integer.toString(this.ticketID));
		bp.put("userID", this.userID);
		bp.put("webService", this.webService);
		
		return bp.make();
	}
}
