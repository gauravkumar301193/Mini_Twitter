package services.tweet;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.Tweet;
import query.database.UpdateTweet;
import response.util.CheckValidity;

public class TweetLike {

	static Logger logger = Logger.getLogger(TweetLike.class);
	public static boolean likeTweet(long userId, long tweetId) throws ClassNotFoundException, SQLException {
		
		if(CheckValidity.isValidTweet(tweetId)) {
			if(CheckValidity.isValidUser(userId)) {
				return UpdateTweet.likeTweet(userId, tweetId);
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
		
		// TODO Auto-generated method stub
		
	}

}
