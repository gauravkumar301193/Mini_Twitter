package database.dummy.dump;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateConnectionsCount {
/*	public void updateFollowerCount() throws ClassNotFoundException, SQLException {
		String query = "select max(user_id) as muid from authentication";
		ResultSet rs = SQLConnection.executeQuery(query);
		rs.next();
		long total = 2795038;
		StringBuilder queryInner = new StringBuilder();
		for (long i = 1; i <= total; i++) {
			queryInner.delete(0, queryInner.length());
			try {
				queryInner.append("select count(*) as tot from connections where following = " + i);
				ResultSet rs2 = SQLConnection.executeQuery(queryInner.toString());
				rs2.next();
				queryInner.delete(0, queryInner.length());
				queryInner.append("update user_details set follower_count = ")
						.append(rs2.getInt("tot")).append(" where user_id = ")
						.append(i);
				int temp = SQLConnection.executeUpdate(queryInner.toString());
			} catch (Exception e) {	
				System.out.println("Error occurred follower at id = " + i);
			}			
		}
	}
	*/
	public void updatteFollowerCount() throws ClassNotFoundException, SQLException {
		 String query2 = "update user_details set following_count = ";
		 String query3 = " where user_id = ";
		 String query4 = "select count(*) as tot from connections where follower = ";
		ResultSet rs2;
		int temp;
		int total = 2795038;
		
		for (int i = 1; i <= total; i++) {
			try {
				rs2 = SQLConnection.executeQuery(query4 + i);
				rs2.next();
				temp = SQLConnection.executeUpdate(query2 + rs2.getInt("tot") + query3 + i);
			} catch (Exception e) {	
				System.out.println("Error occurred follower at id = " + i);
			}			
		}
	}
	
	/*public void updateFollowingCount() throws ClassNotFoundException, SQLException {
		String query = "select max(user_id) as muid from authentication";
		ResultSet rs = SQLConnection.executeQuery(query);
		rs.next();
		long total = rs.getLong("muid");
		StringBuilder queryInner = new StringBuilder();
		for (int i = 1; i <= total; i++) {
			queryInner.delete(0, queryInner.length());
			try {
				queryInner.append("select count(*) as tot from connections where follower = " + i);
				ResultSet rs2 = SQLConnection.executeQuery(queryInner.toString());
				rs2.next();
				queryInner.delete(0, queryInner.length());
				queryInner.append("update user_details set following_count = ")
						.append(rs2.getInt("tot")).append(" where user_id = ")
						.append(i);
				int temp = SQLConnection.executeUpdate(queryInner.toString());
			} catch (Exception e) {	
				System.out.println("Error occurred following at id = " + i);
			}			
		}
	} */
	
	public static void main(String[] args) 
			throws ClassNotFoundException, SQLException {
		UpdateConnectionsCount updateConnectionsCount = new UpdateConnectionsCount();
		updateConnectionsCount.updatteFollowerCount();
		//updateConnectionsCount.updateFollowingCount();
	}
}
