package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class Database {
	private final static String URL = "jdbc:mariadb://192.168.192.5:3306/";
	private final static String USER = "wsclient";
	private final static String PASSWORD = "wsclient";
	public final static String COMPANY = "Transgabonais";
	
	private Connection connection;
	
	public Database() throws SQLException {
		this.connection = DriverManager.getConnection(URL + COMPANY + "?user=" + USER + "&password=" + PASSWORD);
	}
	
	public Travel getTravelInfo(int trainID) throws SQLException {
		Statement stmt = this.connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Travels WHERE train_id = " + trainID);
		Travel travel = null;
		
		while(rs.next()) {
			travel = new Travel(rs);
		}
		
		rs.close();
		stmt.close();
		
		return travel;
	}
	
	public Travel[] getTravelsInfo() throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT * FROM Travels");
		
		Statement stmt = this.connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql.toString());
		ArrayList<Travel> travels = new ArrayList<>();
		
		while(rs.next()) {
			travels.add(new Travel(rs));
		}
		
		rs.close();
		stmt.close();
		
		return (Travel[]) travels.toArray(new Travel[travels.size()]);
	}
	
	public Ticket getTicketInfo(int ticketID) throws SQLException {
		Statement stmt = this.connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Tickets NATURAL JOIN Travels WHERE ticket_id = " + ticketID);
		Ticket ticket = null;
		
		while(rs.next()) {
			ticket = new Ticket(rs);
		}
		
		rs.close();
		stmt.close();
		
		return ticket;
	}
	
	public String buy(int trainID, TicketClass ticketClass, boolean flexible, int price) throws SQLException {
		StringBuilder updateSql = new StringBuilder("UPDATE Travels SET ");
		StringBuilder createSql = new StringBuilder("INSERT INTO Tickets (train_id, flexible, price, class) VALUES (" + trainID + ", " + flexible + ", " + price + ", '" + ticketClass.toString() + "')");
		
		switch(ticketClass) {
			case FIRST:
				updateSql.append("nb_first_class = nb_first_class - 1");
				break;
			case BUSINESS:
				updateSql.append("nb_business_class = nb_business_class - 1");
				break;
			case STANDARD:
				updateSql.append("nb_standard_class = nb_standard_class -1");
				break;
		}
		
		updateSql.append(" WHERE train_id = " + trainID);
		
		Statement stmt = this.connection.createStatement();
		String ticketID = null;
		
		if(stmt.executeUpdate(createSql.toString(), Statement.RETURN_GENERATED_KEYS) == 0)
			throw new SQLException("Nothing inserted : inserted rows = 0");
		
		ResultSet rs = stmt.getGeneratedKeys();
		
		while(rs.next())
			ticketID = Integer.toString(rs.getInt("ticketID"));
		
		if(stmt.executeUpdate(updateSql.toString()) == 0)
			throw new SQLException("Nothing updated : updated rows = 0");
		
		stmt.close();
		
		return ticketID;
	}
	
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			System.err.println("Database close error");
			e.printStackTrace();
		}
	}
}
