package services.tweet;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.Tweet;
import models.User;
import query.database.UpdateTweet;
import response.util.CheckValidity;

public class PostRetweet {
	
	static Logger logger = Logger.getLogger(PostRetweet.class);
	public static boolean retweetPost( long userId, long tweetId) throws ClassNotFoundException, SQLException {
		
		if(CheckValidity.isValidUser(userId)) {
			if(CheckValidity.isValidTweet(tweetId)) {
				return UpdateTweet.retweetPost(userId, tweetId);
			}
			else {
				logger.error("Wrong tweet id received: " + tweetId);
				return false;
			}
			
		}
		else {
			logger.error("Wrong user id received: " + userId);
			return false;
		}
		
	}
}
