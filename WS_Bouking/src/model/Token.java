package model;

public class Token {	
	public static final int TOKEN_SIZE = 20;
	
	private String accessToken;
	private long expirationDate;
	
	/**
	 * Use if no database available
	 */
	public Token(String token, long expirationDate) {
		this.accessToken = token;
		this.expirationDate = expirationDate;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public long getExpirationDate() {
		return this.expirationDate;
	}
	
	public String toResponseBodyFormat() {
		BodyParser bp = new BodyParser();
		
		bp.newBlock();
		bp.put("accessToken", this.accessToken);
		bp.put("expirationDate", Long.toString(this.expirationDate));
		
		return bp.make();
	}
}
