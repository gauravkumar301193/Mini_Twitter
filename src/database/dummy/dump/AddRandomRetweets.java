package database.dummy.dump;

/**
 * @author gaurav.kum
 */
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddRandomRetweets {
	private Integer maxUserID;
	
	private Integer maxTweetID;

	public void setMaxID(Integer nrOfUsers, Integer nrOfTweets) {
		maxUserID = nrOfUsers;
		maxTweetID = nrOfTweets;
	}
	
	private long getRandomTimeStamp(long timeOriginalTweet) {
		long current = System.currentTimeMillis();
		return (long) timeOriginalTweet + (long)((current-timeOriginalTweet) * Math.random());
	}
	
	public void insertRetweets() throws ClassNotFoundException, SQLException {
		for (int tweet = 1; tweet <= (maxTweetID/20); tweet++) {
				long tweetId = (int) ((Math.random() * (maxTweetID)));
				long userId = (int) ((Math.random() * (maxUserID)));
				long authorId = 0 ; 
				long timeOriginalTweet = 0;
				
				StringBuilder query = new StringBuilder("select user_id, created_at from tweets where tweet_id = ").append(tweetId);
				//ResultSet rs =  SQLConnection.executeQuery(query.toString());
				ResultSet rs =  SQLConnection.executeQuery(query.toString());
				if(rs.next())
				{
					authorId = rs.getLong(1);
					timeOriginalTweet = rs.getLong(2);
				}
				
				long retweetTime = getRandomTimeStamp(timeOriginalTweet);
				if (userId == authorId) {
					continue;
				}
				try {

					int result = SQLConnection.executeUpdate(buildString(tweetId, userId, retweetTime, authorId));
				
//					int result = SQLConnection.executeUpdate(buildString(tweetId, userId, retweetTime, authorId));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Couldn't execute " + buildString(tweetId, userId, retweetTime, authorId));
					e.printStackTrace();
				}
			
		}
	}
	
	public String buildString(long tweetId, long userId , long retweetTime, long authorId) {
		StringBuilder query = new StringBuilder("insert into retweets(tweet_id, user_id, created_at , author_id) values(");
		query.append(tweetId);
		query.append(",");
		query.append(userId);
		query.append(",");
		query.append(retweetTime);
		query.append(",");
		query.append(authorId);
		query.append(")");
		return query.toString();
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		AddRandomRetweets addRandomRetweets = new AddRandomRetweets();
		addRandomRetweets.setMaxID(2794972, 9821978);
		try {
			addRandomRetweets.insertRetweets();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
