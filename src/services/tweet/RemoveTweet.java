package services.tweet;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import query.database.QueryTweet;
import query.database.QueryUser;
import query.database.UpdateTweet;
import query.database.UpdateUser;

public class RemoveTweet {
	
	static Logger logger = Logger.getLogger(GetMentionsAfterLogout.class);
	public static boolean deleteTweet(long tweetId) throws ClassNotFoundException, SQLException {
		
		if(QueryTweet.checkTweetExists(tweetId)) {
			return UpdateTweet.deleteTweet(tweetId);
		}
		else {
			logger.error("Wrong tweet id received: " + tweetId);
			return false;
		}
		
		
	}
}
