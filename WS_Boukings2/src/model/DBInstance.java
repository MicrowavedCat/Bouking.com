package model;

import java.sql.SQLException;

public class DBInstance {
	private static Database DB = null;
	
	public static Database getInstance() throws SQLException {
		if(DB == null)
			DB = new Database();
		
		return DB;
	}
}
