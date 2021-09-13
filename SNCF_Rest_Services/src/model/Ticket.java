package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Ticket {	
	private int ticketID;
	private int trainID;
	private int price;
	private boolean flexible;
	private TicketClass ticketClass;
	
	public Ticket(ResultSet rs) throws SQLException {
		this.ticketID = rs.getInt("ticket_id");
		this.trainID = rs.getInt("train_id");
		this.price = rs.getInt("price");
		this.flexible = rs.getBoolean("flexible");
		this.ticketClass = TicketClass.toEnum(rs.getString("class"));
	}
	
	public String toResponseBodyFormat() {
		BodyParser bp = new BodyParser();
		
		bp.newBlock();
		bp.put("ticketID", Integer.toString(this.ticketID));
		bp.put("trainID", Integer.toString(this.trainID));
		bp.put("price", Integer.toString(this.price));
		bp.put("flexible", Boolean.toString(this.flexible));
		bp.put("ticketClass", TicketClass.toString(this.ticketClass));
		
		return bp.make();
	}
}
