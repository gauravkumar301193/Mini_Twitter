package services.tweet;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.Tweet;
import query.database.UpdateTweet;

public class NewTweet {
	
	static Logger logger = Logger.getLogger(NewTweet.class);
	public static boolean postTweet(Tweet tweet) throws ClassNotFoundException, SQLException {
		if(isValidTweet(tweet)) {
			return UpdateTweet.postTweet(tweet);
		}
		else {
			logger.error("Tweet Not valid ");
			return false;
		}
	}
	
	private static boolean isValidTweet(Tweet tweet) {
		return !(tweet.getTweetText() == "");
	}
}
