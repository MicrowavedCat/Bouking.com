package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {	
	private String mail;
	private String firstName;
	private String lastName;
	private String password;
	
	public User(ResultSet rs) throws SQLException {
		this.mail = rs.getString("mail");
		this.firstName = rs.getString("first_name");
		this.lastName = rs.getString("last_name");
		this.password = rs.getString("password");
	}
	
	/**
	 * Use if no database available
	 */
	public User(String mail, String firstName, String lastName, String password) throws SQLException {
		this.mail = mail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String toResponseBodyFormat() {
		BodyParser bp = new BodyParser();
		
		bp.newBlock();
		bp.put("mail", this.mail);
		bp.put("firstName", this.firstName);
		bp.put("lastName", this.lastName);
		
		return bp.make();
	}
}
