package database.dummy.dump;

/**
 * @author mayank.ra
 */
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;

public class UserConnections {
	private Integer maxUserID;

	public void setMaxUserID(Integer nrOfUsers) {
		maxUserID = nrOfUsers;
	}
	
	private long getRandomTimeStamp() {
		long current = System.currentTimeMillis();
		return (long) (current * Math.random());
	}
	
	public void insertConnections() throws ClassNotFoundException {
		for (int user = 1; user <= (maxUserID/10); user++) {
			for (int i = 1; i < 7; i++) {
				Integer follower = (int) ((Math.random() * (maxUserID)));
				if (follower == user) {
					i--;
					continue;
				}
				try {
					int result = SQLConnection.executeUpdate(buildString(follower, user));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Couldn't execute " + buildString(follower, user));
					e.printStackTrace();
				}
			}
		}
	}
	
	public String buildString(int follower, int user) {
		StringBuilder query = new StringBuilder("insert into connections(follower, following, start_time) values(");
		query.append(follower);
		query.append(",");
		query.append(user);
		query.append(",");
		query.append(getRandomTimeStamp());
		query.append(")");
		return query.toString();
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		UserConnections userConnections = new UserConnections();
		userConnections.setMaxUserID(2794972);
		userConnections.insertConnections();
		
	}
}
