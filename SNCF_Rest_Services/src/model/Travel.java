package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Travel {
	private final static float FIRST_CLASS_EXTRA = 0.40f;
	private final static float BUSINESS_CLASS_EXTRA = 0.20f;
	private final static float STANDARD_CLASS_EXTRA = 0f;
	private final static int FLEXIBLE_EXTRA = 35;
	
	private int trainID;
	private int ticketsFirst;
	private int ticketsBusiness;
	private int ticketsStandard;
	private int basePrice;
	private String departureStation;
	private String arrivalStation;
	private long departureDate;
	private long arrivalDate;
	
	public Travel(ResultSet rs) throws SQLException {
		this.trainID = rs.getInt("train_id");
		this.ticketsFirst = rs.getInt("nb_first_class");
		this.ticketsBusiness = rs.getInt("nb_business_class");
		this.ticketsStandard = rs.getInt("nb_standard_class");
		this.basePrice = rs.getInt("base_price");
		this.departureStation = rs.getString("departure_station");
		this.arrivalStation = rs.getString("arrival_station");
		this.departureDate = rs.getLong("departure_date");
		this.arrivalDate = rs.getLong("arrival_date");
	}
	
	public int getFirstClassPrice(boolean flexible) {
		int price = this.basePrice + Math.round(this.basePrice * FIRST_CLASS_EXTRA);
		
		if(flexible)
			price += FLEXIBLE_EXTRA;
		
		return price;
	}
	
	public int getBusinessClassPrice(boolean flexible) {
		int price = this.basePrice + Math.round(this.basePrice * BUSINESS_CLASS_EXTRA);
		
		if(flexible)
			price += FLEXIBLE_EXTRA;
		
		return price;
	}
	
	public int getStandardClassPrice(boolean flexible) {
		int price = this.basePrice + Math.round(this.basePrice * STANDARD_CLASS_EXTRA);
		
		if(flexible)
			price += FLEXIBLE_EXTRA;
		
		return price;
	}

	public int getNumberOfTicketsFirst() {
		return this.ticketsFirst;
	}

	public int getNumberOfTicketsBusiness() {
		return this.ticketsBusiness;
	}

	public int getNumberOfTicketsStandard() {
		return this.ticketsStandard;
	}
	
	public String toResponseBodyFormat() {
		BodyParser bp = new BodyParser();
		
		bp.newBlock();
		bp.put("trainID", Integer.toString(this.trainID));
		bp.put("company", Database.COMPANY);
		bp.put("numberOfFirstClass", Integer.toString(this.ticketsFirst));
		bp.put("numberOfBusinessClass", Integer.toString(this.ticketsBusiness));
		bp.put("numberOfStandardClass", Integer.toString(this.ticketsStandard));
		bp.put("flexibleExtraPrice", Integer.toString(FLEXIBLE_EXTRA));
		bp.put("firstClassPrice", Integer.toString(this.getFirstClassPrice(false)));
		bp.put("businessClassPrice", Integer.toString(this.getBusinessClassPrice(false)));
		bp.put("standardClassPrice", Integer.toString(this.getStandardClassPrice(false)));
		bp.put("flexibleFirstClassPrice", Integer.toString(this.getFirstClassPrice(true)));
		bp.put("flexibleBusinessClassPrice", Integer.toString(this.getBusinessClassPrice(true)));
		bp.put("flexibleStandardClassPrice", Integer.toString(this.getStandardClassPrice(true)));
		bp.put("departureStation", this.departureStation);
		bp.put("arrivalStation", this.arrivalStation);
		bp.put("departureDate", Long.toString(this.departureDate));
		bp.put("arrivalDate", Long.toString(this.arrivalDate));
		
		return bp.make();
	}

	public boolean hasEnoughPlace(int nbPlaces, TicketClass ticketClass) {
		switch(ticketClass) {
			case FIRST:
				return (this.ticketsFirst >= nbPlaces);
			case BUSINESS:
				return (this.ticketsBusiness >= nbPlaces);
			case STANDARD:
				return (this.ticketsStandard >= nbPlaces);
		}
		
		return false;
	}
}
