package model;

import java.sql.*;
import java.util.ArrayList;

public class Database {
	private final static String URL = "jdbc:mariadb://192.168.192.5:3306/";
	private final static String USER = "wsclient";
	private final static String PASSWORD = "wsclient";
	private final static String COMPANY = "SNCF";
	
	private static Connection CONNECTION;
	
	public Database() throws SQLException {
		CONNECTION = DriverManager.getConnection(URL + "Boukings?user=" + USER + "&password=" + PASSWORD);
	}
	
	public Travel getTravelInfo(int trainID) throws SQLException {
		Statement stmt = CONNECTION.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Travels WHERE company = '" + COMPANY + "' AND train_id = " + trainID);
		Travel travel = null;
		
		while(rs.next()) {
			travel = new Travel(rs);
		}
		
		rs.close();
		stmt.close();
		
		return travel;
	}
	
	public Travel[] getTravelsInfo(int numberOfTickets, TicketClass ticketClass, String departureStation, String arrivalStation, String departureDate, String arrivalDate) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT * FROM Travels WHERE company = '" + COMPANY + "' AND ");
		
		if(ticketClass != null) {			
			switch(ticketClass) {
				case FIRST:
					sql.append("nb_first_class >= ");
					break;
				case BUSINESS:
					sql.append("nb_business_class >= ");
					break;
				default:
					sql.append("nb_standard_class >= ");
					break;
			}
			
			sql.append(numberOfTickets);
		} else
			sql.append("nb_first_class >= " + numberOfTickets + " AND nb_business_class >= " + numberOfTickets + " AND nb_standard_class >= " + numberOfTickets);
		
		if(departureStation != null)
			sql.append(" AND departure_station = '" + departureStation + "'");
		
		if(arrivalStation != null)
			sql.append(" AND arrival_station = '" + arrivalStation + "'");
		
		if(departureDate != null)
			sql.append(" AND departure_date = '" + departureDate + "'");
		
		if(arrivalDate != null)
			sql.append(" AND arrival_date = '" + arrivalDate + "'");
		
		Statement stmt = CONNECTION.createStatement();
		ResultSet rs = stmt.executeQuery(sql.toString());
		ArrayList<Travel> travels = new ArrayList<>();
		
		while(rs.next()) {
			travels.add(new Travel(rs));
		}
		
		rs.close();
		stmt.close();
		
		return (Travel[]) travels.toArray(new Travel[travels.size()]);
	}
	
	public void close() {
		try {
			CONNECTION.close();
		} catch (SQLException e) {
			System.err.println("Database close error");
			e.printStackTrace();
		}
	}
}
