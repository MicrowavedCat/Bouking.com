package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private String company;
	private String departureStation;
	private String arrivalStation;
	private Date departureDate;
	private Date arrivalDate;
	
	public Travel(ResultSet rs) throws SQLException {
		this.trainID = rs.getInt("train_id");
		this.ticketsFirst = rs.getInt("nb_first_class");
		this.ticketsBusiness = rs.getInt("nb_business_class");
		this.ticketsStandard = rs.getInt("nb_standard_class");
		this.basePrice = rs.getInt("base_price");
		this.company = rs.getString("company");
		this.departureStation = rs.getString("departure_station");
		this.arrivalStation = rs.getString("arrival_station");
		this.departureDate = rs.getDate("departure_date");
		this.arrivalDate = rs.getDate("arrival_date");
	}
	
	public String toResponseBodyFormat() {
		BodyParser bp = new BodyParser();
		int firstPrice = this.basePrice + Math.round(this.basePrice * FIRST_CLASS_EXTRA);
		int businessPrice = this.basePrice + Math.round(this.basePrice * BUSINESS_CLASS_EXTRA);
		int standardPrice = this.basePrice + Math.round(this.basePrice * STANDARD_CLASS_EXTRA);
		
		bp.newBlock();
		bp.put("trainID", Integer.toString(this.trainID));
		bp.put("numberOfFirstClass", Integer.toString(this.ticketsFirst));
		bp.put("numberOfBusinessClass", Integer.toString(this.ticketsBusiness));
		bp.put("numberOfStandardClass", Integer.toString(this.ticketsStandard));
		bp.put("flexibleExtraPrice", Integer.toString(FLEXIBLE_EXTRA));
		bp.put("firstClassPrice", Integer.toString(firstPrice));
		bp.put("businessClassPrice", Integer.toString(businessPrice));
		bp.put("standardClassPrice", Integer.toString(standardPrice));
		bp.put("flexibleFirstClassPrice", Integer.toString(firstPrice + FLEXIBLE_EXTRA));
		bp.put("flexibleBusinessClassPrice", Integer.toString(businessPrice + FLEXIBLE_EXTRA));
		bp.put("flexibleStandardClassPrice", Integer.toString(standardPrice + FLEXIBLE_EXTRA));
		bp.put("company", this.company);
		bp.put("departureStation", this.departureStation);
		bp.put("arrivalStation", this.arrivalStation);
		bp.put("departureDate", Long.toString(this.departureDate.getTime()));
		bp.put("arrivalDate", Long.toString(this.arrivalDate.getTime()));
		
		return bp.make();
	}
}
