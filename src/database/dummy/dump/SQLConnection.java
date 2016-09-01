package database.dummy.dump;

/**
 * @author gaurav.kum
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
	
	public Connection conn;
    private static Statement statement;
    public static SQLConnection db;
    private SQLConnection() {
        
        try {
        	Class.forName("com.mysql.jdbc.Driver");
    		
//    		conn = (Connection) DriverManager.getConnection(
//    				"jdbc:mysql://localhost:3306/test", "root", "");
    		conn = (Connection) DriverManager.getConnection(
    				"jdbc:mysql://localhost:3306/TwitterDatabase", "root", "");
    		
          }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
   
    public static synchronized SQLConnection getDbCon() {
        if ( db == null ) {
            db = new SQLConnection();
        }
        return db;
    }
 
	public static ResultSet executeQuery(String sqlQuery) throws SQLException, ClassNotFoundException {
		if (db == null) {
			db = getDbCon();
		}
		statement = db.conn.createStatement();
		
		return statement.executeQuery(sqlQuery);
	}
	
	
	public static int executeUpdate(String sqlQuery) throws SQLException, ClassNotFoundException {
		if (db == null) {
			db = getDbCon();
		}
		// TODO log: executing query: sqlQuery
		statement = db.conn.createStatement();
		return statement.executeUpdate(sqlQuery);
	}
}
	
