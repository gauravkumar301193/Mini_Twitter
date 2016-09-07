package database.dummy.dump;

/**
 * @author mayank.ra
 */
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenerateTweetsForUser {
	
	private long getRandomTimeStamp() {
		long current = System.currentTimeMillis();
		return (long) (current * Math.random());
	}
	
	public void insertTweets(Integer user, String prefix, String handle) 
			throws ClassNotFoundException, SQLException {
		for (int i = 0; i < 1000; i++) {
			StringBuilder tweetText = new StringBuilder(prefix + " tweet-" + i);
			String query2 = "select max(tweet_id) as mt from tweets";
			
			StringBuilder query = new StringBuilder();
			ResultSet resultSet = SQLConnection.executeQuery(query2);
			resultSet.next();
			System.out.println("executing " + query2.toString() + "  " + resultSet.getLong("mt"));
			Long tweetId = resultSet.getLong("mt");
			tweetId++;
			StringBuilder queryForText = new StringBuilder("insert into tweets values(")
					.append(tweetId).append(", ")
					.append(user).append(", \"")
					.append(tweetText).append("\", ")
					.append(getRandomTimeStamp()).append(", 0,0,0, \"")
					.append(handle).append("\")");
			System.out.println("executing " + queryForText.toString());
			int res = SQLConnection.executeUpdate(queryForText.toString());
		}
	}
	
	public static void main(String[] args) 
			throws ClassNotFoundException, SQLException {
		GenerateTweetsForUser generateTweetsForUser  = new GenerateTweetsForUser();
		generateTweetsForUser.insertTweets(1958, "This is for CompuFinance ", "CompuFinance");
		generateTweetsForUser.insertTweets(5545, "This is for LaineBergeson ", "LaineBergeson");
	}
}
