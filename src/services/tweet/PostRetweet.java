package services.tweet;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.Tweet;
import models.User;
import query.database.UpdateTweet;
import response.util.CheckValidity;

public class PostRetweet {
	
	static Logger logger = Logger.getLogger(PostRetweet.class);
	public static boolean retweetPost( String loggedInUserHandle, long tweetId, long authorId, long loggedInUser) throws ClassNotFoundException, SQLException {
		if(CheckValidity.isValidUser(loggedInUser) && CheckValidity.isValidUser(authorId)) {
			if(CheckValidity.isValidTweet(tweetId)) {
				return UpdateTweet.retweetPost(loggedInUserHandle, tweetId ,authorId, loggedInUser);
			}
			else {
				logger.error("Wrong tweet id received: " + tweetId);
				return false;
			}
			
		}
		else {
			logger.error("Wrong user id received: " + loggedInUser);
			return false;
		}
		
	}
}
