package model;

import utils.DateConvert;

public class Travel {
	private int trainID;
	private int ticketsFirst;
	private int ticketsBusiness;
	private int ticketsStandard;
	private int flexibleExtraPrice;
	private int firstClassPrice;
	private int businessClassPrice;
	private int standardClassPrice;
	private int flexibleFirstClassPrice;
	private int flexibleBusinessClassPrice;
	private int flexibleStandardClassPrice;
	private String company;
	private String departureStation;
	private String arrivalStation;
	private long departureDate;
	private long arrivalDate;
	
	public Travel(BodyParser bp) {
		this.trainID = Integer.parseInt(bp.get("trainID"));
		this.ticketsFirst = Integer.parseInt(bp.get("numberOfFirstClass"));
		this.ticketsBusiness = Integer.parseInt(bp.get("numberOfBusinessClass"));
		this.ticketsStandard = Integer.parseInt(bp.get("numberOfStandardClass"));
		this.flexibleExtraPrice = Integer.parseInt(bp.get("flexibleExtraPrice"));
		this.firstClassPrice = Integer.parseInt(bp.get("firstClassPrice"));
		this.businessClassPrice = Integer.parseInt(bp.get("businessClassPrice"));
		this.standardClassPrice = Integer.parseInt(bp.get("standardClassPrice"));
		this.flexibleFirstClassPrice = Integer.parseInt(bp.get("flexibleFirstClassPrice"));
		this.flexibleBusinessClassPrice = Integer.parseInt(bp.get("flexibleBusinessClassPrice"));
		this.flexibleStandardClassPrice = Integer.parseInt(bp.get("flexibleStandardClassPrice"));
		this.company = bp.get("company");
		this.departureStation = bp.get("departureStation");
		this.arrivalStation = bp.get("arrivalStation");
		this.departureDate = Long.parseLong(bp.get("departureDate"));
		this.arrivalDate = Long.parseLong(bp.get("arrivalDate"));
	}

	public int getTrainID() {
		return this.trainID;
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

	public int getFlexibleExtraPrice() {
		return this.flexibleExtraPrice;
	}

	public int getFirstClassPrice() {
		return this.firstClassPrice;
	}

	public int getBusinessClassPrice() {
		return this.businessClassPrice;
	}

	public int getStandardClassPrice() {
		return this.standardClassPrice;
	}

	public int getFlexibleFirstClassPrice() {
		return this.flexibleFirstClassPrice;
	}

	public int getFlexibleBusinessClassPrice() {
		return this.flexibleBusinessClassPrice;
	}

	public int getFlexibleStandardClassPrice() {
		return this.flexibleStandardClassPrice;
	}

	public String getCompany() {
		return this.company;
	}

	public String getDepartureStation() {
		return this.departureStation;
	}

	public String getArrivalStation() {
		return this.arrivalStation;
	}

	public Long getDepartureDate() {
		return this.departureDate;
	}

	public Long getArrivalDate() {
		return this.arrivalDate;
	}

	public String getFormattedDepartureDate() {
		return DateConvert.toString(this.departureDate);
	}

	public String getFormattedArrivalDate() {
		return DateConvert.toString(this.arrivalDate);
	}
}
