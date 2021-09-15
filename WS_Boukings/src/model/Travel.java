package model;

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
	private String webService;
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
		this.webService = webService;
		this.departureStation = bp.get("departureStation");
		this.arrivalStation = bp.get("arrivalStation");
		this.departureDate = Long.parseLong(bp.get("departureDate"));
		this.arrivalDate = Long.parseLong(bp.get("arrivalDate"));
	}
	
	public String toResponseBodyFormat() {
		BodyParser bp = new BodyParser();
		
		bp.newBlock();
		bp.put("trainID", Integer.toString(this.trainID));
		bp.put("company", this.company);
		bp.put("webService", this.webService);
		bp.put("numberOfFirstClass", Integer.toString(this.ticketsFirst));
		bp.put("numberOfBusinessClass", Integer.toString(this.ticketsBusiness));
		bp.put("numberOfStandardClass", Integer.toString(this.ticketsStandard));
		bp.put("flexibleExtraPrice", Integer.toString(this.flexibleExtraPrice));
		bp.put("firstClassPrice", Integer.toString(this.firstClassPrice));
		bp.put("businessClassPrice", Integer.toString(this.businessClassPrice));
		bp.put("standardClassPrice", Integer.toString(this.standardClassPrice));
		bp.put("flexibleFirstClassPrice", Integer.toString(this.flexibleFirstClassPrice));
		bp.put("flexibleBusinessClassPrice", Integer.toString(this.flexibleBusinessClassPrice));
		bp.put("flexibleStandardClassPrice", Integer.toString(this.flexibleStandardClassPrice));
		bp.put("departureStation", this.departureStation);
		bp.put("arrivalStation", this.arrivalStation);
		bp.put("departureDate", Long.toString(this.departureDate));
		bp.put("arrivalDate", Long.toString(this.arrivalDate));
		
		return bp.make();
	}
}
