package database.dummy.dump;

/**
 * @author mayank.ra
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
	public static Statement statement = null;

	public static Statement getExecutableStatement() throws SQLException, ClassNotFoundException {
		if (statement != null) 
			return statement;
		Class.forName("com.mysql.jdbc.Driver");
		// TODO log: trying to establish connection on port 3306.
		
		Connection con = (Connection) DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/TwitterDatabase", "root", "");
		// TODO log: the connection has been established
		statement = con.createStatement();
		return statement;
	}
	
	public static Statement getExecutableStatement1() throws ClassNotFoundException, SQLException {
		Statement st;
		
		Class.forName("com.mysql.jdbc.Driver");
		// TODO log: trying to establish connection on port 3306.
		Connection con = (Connection) DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/TwitterDatabase", "root", "");
		// TODO log: the connection has been established
		st = con.createStatement();
		return st;
		
	}
	
	public static ResultSet executeQuery1(String sqlQuery) throws SQLException, ClassNotFoundException {
	
			Statement st = getExecutableStatement1();
		
		// TODO log: executing query: sqlQuery
		return st.executeQuery(sqlQuery);
	}
	
	public static ResultSet executeQuery(String sqlQuery) throws SQLException, ClassNotFoundException {
		if (statement == null) {
			statement = getExecutableStatement();
		}
		// TODO log: executing query: sqlQuery
		return statement.executeQuery(sqlQuery);
	}
	
	
	public static int executeUpdate(String sqlQuery) throws SQLException, ClassNotFoundException {
		if (statement == null) {
			statement = getExecutableStatement();
		}
		// TODO log: executing query: sqlQuery
		return statement.executeUpdate(sqlQuery);
	}
	
}
