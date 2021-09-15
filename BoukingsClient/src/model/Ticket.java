package model;

import utils.DateConvert;

public class Ticket {	
	private int ticketID;
	private int trainID;
	private int price;
	private boolean flexible;
	private TicketClass ticketClass;

	private String company;
	private String webService;
	private String departureStation;
	private String arrivalStation;
	private long departureDate;
	private long arrivalDate;
	
	public Ticket(BodyParser bp) {
		this.ticketID = Integer.parseInt(bp.get("ticketID"));
		this.trainID = Integer.parseInt(bp.get("trainID"));
		this.price = Integer.parseInt(bp.get("price"));
		this.flexible = Boolean.parseBoolean(bp.get("flexible"));
		this.ticketClass = TicketClass.toEnum(bp.get("ticketClass"));
		
		this.company = bp.get("company");
		this.webService = bp.get("webService");
		this.departureStation = bp.get("departureStation");
		this.arrivalStation = bp.get("arrivalStation");
		this.departureDate = Long.parseLong(bp.get("departureDate"));
		this.arrivalDate = Long.parseLong(bp.get("arrivalDate"));
	}

	public int getTicketID() {
		return this.ticketID;
	}

	public int getTrainID() {
		return this.trainID;
	}

	public int getPrice() {
		return this.price;
	}

	public TicketClass getTicketClass() {
		return this.ticketClass;
	}

	public String getCompany() {
		return this.company;
	}

	public String getWebService() {
		return this.webService;
	}

	public String getDepartureStation() {
		return this.departureStation;
	}

	public String getArrivalStation() {
		return this.arrivalStation;
	}

	public long getDepartureDate() {
		return this.departureDate;
	}

	public long getArrivalDate() {
		return this.arrivalDate;
	}

	public String getFormattedDepartureDate() {
		return DateConvert.toString(this.departureDate);
	}

	public String getFormattedArrivalDate() {
		return DateConvert.toString(this.arrivalDate);
	}

	public boolean isFlexible() {
		return this.flexible;
	}
}
