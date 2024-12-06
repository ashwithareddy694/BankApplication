package project_bankApplication;

import java.sql.*;

public class SetConnection {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; 
	public static final String USER = "akash"; 
	public static final String PASSWORD = "reddy"; 

	public static Connection getConnection() throws SQLException {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("DataBase Connection Failed: " + e.getMessage());
			throw e;
		}
	}
}

