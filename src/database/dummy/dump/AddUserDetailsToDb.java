package database.dummy.dump;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddUserDetailsToDb {
	private Integer maxUserID;

	private long getRandomTimeStamp() {
		long current = System.currentTimeMillis();
		return (long) (current * Math.random());
	}
	
	public void insertUserDetails() 
			throws ClassNotFoundException, SQLException {
	
		StringBuilder sql = new StringBuilder("select user_id from Authentication");
		
		ResultSet rs = SQLConnection.executeQuery1(sql.toString());
	
		ResultSet rs1 ;
		ResultSet rs2 ;
		Statement st1 = SQLConnection.getExecutableStatement();
		Statement st2 = SQLConnection.getExecutableStatement1();
		Statement st3 = SQLConnection.getExecutableStatement1();
		while(rs.next()) {
			long follower = 0;
			long following = 0;
			long userId = rs.getLong("user_id");
			
			StringBuilder followers = new StringBuilder("select count(*) from connections where following = ").append(userId);
			
			rs1 = st1.executeQuery(followers.toString());
			if(rs1.next()) {
				 follower = rs1.getLong(1);
			}

			StringBuilder followingUsers = new StringBuilder("select count(*) from connections where follower = ").append(userId);
			
			rs2 = st2.executeQuery(followingUsers.toString());
			if(rs2.next()) {
				 following = rs2.getLong(1);			
			}

			StringBuilder query = new StringBuilder("insert into user_details(user_id, following_count, follower_count, logout) values(")
				 .append(userId).append(",")
				 .append(following).append(",").append(follower)
				 .append(",").append(getRandomTimeStamp()).append(")");
			 
			 
			int result = st3.executeUpdate(query.toString());
			rs1.close();
			rs2.close();
		}
	}
	
	public static void main(String[] args) 
			throws ClassNotFoundException, SQLException {
		AddUserDetailsToDb userDetails = new AddUserDetailsToDb();
		userDetails.insertUserDetails();
	}
}
