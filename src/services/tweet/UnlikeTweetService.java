package services.tweet;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import query.database.UpdateTweet;
import request.controller.tweet.UnlikeATweet;
import response.util.CheckValidity;

public class UnlikeTweetService {
	 private static final Logger logger = Logger.getLogger(UnlikeATweet.class);

	public static boolean unlikeTweet(long userId, long tweetId) throws ClassNotFoundException, SQLException {
		if(CheckValidity.isValidTweet(tweetId)) {
			if(CheckValidity.isValidUser(userId)) {
				return UpdateTweet.unlikeTweet(userId, tweetId);
			}
			else {
				logger.error("Wrong user id received: " + userId);
				return false;
			}
		}
		else {
			logger.error("Wrong tweet id received: " + tweetId);
			return false;
		}
		
	}

}
