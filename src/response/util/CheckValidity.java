package response.util;

import java.sql.SQLException;

import query.database.QueryTweet;
import query.database.QueryUser;

/**
 * @author mayank.ra
 */
public class CheckValidity {
	
	public static boolean isValidTime(long timestamp) {
		if (timestamp > System.currentTimeMillis() || timestamp < 0)
			return false;
		return true;
	}
	
	public static boolean isValidUser(long userId) 
			throws SQLException, ClassNotFoundException {
		if (userId < 0)
			return false;
		if (QueryUser.checkUserExists(userId)) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidTweet(long tweetId) 
			throws SQLException, ClassNotFoundException {
		if (tweetId < 0)
			return false;
		if (QueryTweet.checkTweetExists(tweetId))
			return true;
		return false;
	}
}
