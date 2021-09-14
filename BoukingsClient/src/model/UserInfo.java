package model;

public class UserInfo {
	private String mail;
	private String firstName;
	private String lastName;
	
	public UserInfo(BodyParser bp) {
		this.mail = bp.get("mail");
		this.firstName = bp.get("firstName");
		this.lastName = bp.get("lastName");
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
}
