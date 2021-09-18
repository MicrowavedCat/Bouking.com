package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Database {
	private final static String FOLDER = "/home/doz/Bouking.com/databaseTables/";
	
	private BufferedReader usersRead; 
	private BufferedReader bookingsRead;
	private BufferedReader tokensRead;
	private BufferedWriter bookingsWrite;
	private BufferedWriter tokensWrite;

	private void readUsers() throws FileNotFoundException {
		this.usersRead = new BufferedReader(new InputStreamReader(new FileInputStream(FOLDER + "Users")));
	}
	
	private void readBookings() throws FileNotFoundException {
		this.bookingsRead = new BufferedReader(new InputStreamReader(new FileInputStream(FOLDER + "Bookings")));
	}
	
	private void readTokens() throws FileNotFoundException {
		this.tokensRead = new BufferedReader(new InputStreamReader(new FileInputStream(FOLDER + "Tokens")));
	}
	
	private void writeBookings() throws FileNotFoundException {
		this.bookingsWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FOLDER + "Bookings", true)));
	}
	
	private void writeTokens() throws FileNotFoundException {
		this.tokensWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FOLDER + "Tokens", true)));
	}
	
	public User getUserInfo(String mail) throws IOException {
		String line;
		BodyParser info = new BodyParser();
		StringBuilder block = new StringBuilder();
		
		this.readUsers();
		while ((line = this.usersRead.readLine()) != null){            
            if(line.equals("")) {
            	block.append('\n');
            	info.parse(block.toString());
            	info.next();
            	
            	if(info.get("mail") != null && info.get("mail").equals(mail))
            		break;
            	
            	info = new BodyParser();
            	block = new StringBuilder();
            } else
            	block.append(line + '\n');
        }
		
		if(line == null)
			return null;

		this.close();
		return new User(info.get("mail"), info.get("firstName"), info.get("lastName"), info.get("password"));
	}
	
	public Booking getBookingInfo(int bookingID) throws IOException {
		String line;
		BodyParser info = new BodyParser();
		StringBuilder block = new StringBuilder();
		
		this.readBookings();
		while ((line = this.bookingsRead.readLine()) != null){            
            if(line.equals("")) {
            	block.append('\n');
            	info.parse(block.toString());
            	info.next();
            	
            	if(info.get("bookingID") != null && info.get("bookingID").equals(Integer.toString(bookingID)))
            		break;
            	
            	info = new BodyParser();
            	block = new StringBuilder();
            } else
            	block.append(line + '\n');
        }
		
		if(line == null)
			return null;

		this.close();
		return new Booking(Integer.parseInt(info.get("bookingID")), Integer.parseInt(info.get("ticketID")), info.get("userID"), info.get("webService"));
	}
	
	public Token getTokenInfo(String accessToken) throws IOException {
		String line;
		BodyParser info = new BodyParser();
		StringBuilder block = new StringBuilder();
		
		this.readTokens();
		while ((line = this.tokensRead.readLine()) != null){            
            if(line.equals("")) {
            	block.append('\n');
            	info.parse(block.toString());
            	info.next();
            	
            	if(info.get("accessToken") != null && info.get("accessToken").equals(accessToken))
            		break;
            	
            	info = new BodyParser();
            	block = new StringBuilder();
            } else
            	block.append(line + '\n');
        }
		
		if(line == null)
			return null;

		this.close();
		return new Token(info.get("accessToken"), Long.parseLong(info.get("expirationDate")));
	}
	
	public Booking[] getUserBookingsInfo(String mail) throws IOException {
		String line;
		BodyParser info = new BodyParser();
		StringBuilder block = new StringBuilder();
		ArrayList<Booking> bookings = new ArrayList<>();
		
		this.readBookings();
		while ((line = this.bookingsRead.readLine()) != null){            
            if(line.equals("")) {
            	block.append('\n');
            	info.parse(block.toString());
            	info.next();
            	
            	if(info.get("userID") != null && info.get("userID").equals(mail))
            		bookings.add(new Booking(Integer.parseInt(info.get("bookingID")), Integer.parseInt(info.get("ticketID")), info.get("userID"), info.get("webService")));
            	
            	info = new BodyParser();
            	block = new StringBuilder();
            } else
            	block.append(line + '\n');
        }

		this.close();
		return (Booking[]) bookings.toArray(new Booking[bookings.size()]);
	}
	
	public String addBooking(int ticketID, String userID, String webService) throws IOException {
		String line;
		int autoIncrement = 0;
		
		this.readBookings();
		this.writeBookings();
		while ((line = this.bookingsRead.readLine()) != null){            
            if(line.equals(""))
            	autoIncrement++;
        }
		
		autoIncrement++;
		
		BodyParser bp = new BodyParser();
		bp.newBlock();
		bp.put("bookingID", Integer.toString(autoIncrement));
		bp.put("ticketID", Integer.toString(ticketID));
		bp.put("userID", userID);
		bp.put("webService", webService);
		
		this.bookingsWrite.write(bp.make());
		
		this.close();
		return Integer.toString(autoIncrement);
	}
	
	public Token createToken() throws IOException {
		Random rand = new Random();
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder token;
		Token accessToken;

		while(true) {
			token = new StringBuilder();
			
			for(int i = 0; i < Token.TOKEN_SIZE; i++)
				token.append( chars.charAt( rand.nextInt(chars.length()) ) );
			
			accessToken = this.getTokenInfo(token.toString());
			if(accessToken == null)
				break;
		}
		
		this.writeTokens();
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 3);

		long today = c.getTimeInMillis();
		BodyParser bp = new BodyParser();
		bp.newBlock();
		bp.put("accessToken", token.toString());
		bp.put("expirationDate", Long.toString(today));
		
		this.tokensWrite.write(bp.make());
		
		this.close();
		return new Token(token.toString(), today);
	}
	
	public void close() {
		try {
			if(this.usersRead != null) {
				this.usersRead.close();
				this.usersRead = null;
			}
	
			if(this.bookingsRead != null) {
				this.bookingsRead.close();
				this.bookingsRead = null;
			}
	
			if(this.tokensRead != null) {
				this.tokensRead.close();
				this.tokensRead = null;
			}
	
			if(this.bookingsWrite != null) {
				this.bookingsWrite.close();
				this.bookingsWrite = null;
			}
	
			if(this.tokensWrite != null) {
				this.tokensWrite.close();
				this.tokensWrite = null;
			}
		} catch (IOException e) {
			System.err.println("Database close error");
			e.printStackTrace();
		}
	}
}
