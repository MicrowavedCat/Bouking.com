package model;

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
	
	public Ticket(String webService, BodyParser bp) {
		this.ticketID = Integer.parseInt(bp.get("ticketID"));
		this.trainID = Integer.parseInt(bp.get("trainID"));
		this.price = Integer.parseInt(bp.get("price"));
		this.flexible = Boolean.parseBoolean(bp.get("flexible"));
		this.ticketClass = TicketClass.toEnum(bp.get("ticketClass"));
		
		this.company = bp.get("company");
		this.webService = webService;
		this.departureStation = bp.get("departureStation");
		this.arrivalStation = bp.get("arrivalStation");
		this.departureDate = Long.parseLong(bp.get("departureDate"));
		this.arrivalDate = Long.parseLong(bp.get("arrivalDate"));
	}
	
	public String toResponseBodyFormat() {
		BodyParser bp = new BodyParser();
		
		bp.newBlock();
		bp.put("ticketID", Integer.toString(this.ticketID));
		bp.put("trainID", Integer.toString(this.trainID));
		bp.put("price", Integer.toString(this.price));
		bp.put("flexible", Boolean.toString(this.flexible));
		bp.put("ticketClass", TicketClass.toString(this.ticketClass));
		
		bp.put("company", this.company);
		bp.put("webService", this.webService);
		bp.put("departureStation", this.departureStation);
		bp.put("arrivalStation", this.arrivalStation);
		bp.put("departureDate", Long.toString(this.departureDate));
		bp.put("arrivalDate", Long.toString(this.arrivalDate));
		
		return bp.make();
	}
}
