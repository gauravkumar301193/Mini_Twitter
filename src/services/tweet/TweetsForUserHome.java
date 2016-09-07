package services.tweet;

import query.database.QueryTweet;
import query.database.QueryUser;
import response.util.CheckValidity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import models.Tweet;
/**
 * @author mayank.ra
 */
public class TweetsForUserHome {
	static Logger logger = Logger.getLogger(TweetsForUserHome.class);
	public static List<Tweet> getTweetsForUserHome(long userId, long startTime, long latestTime)
			throws ClassNotFoundException, SQLException {
		
		
		
		List<Tweet> listOfTweetsForHome = new ArrayList<>();
		if (checkValidInput(userId, startTime, latestTime)) {
			List<Tweet> listOfTweetIds = QueryTweet.getAllTweetsForUserHome(userId, startTime, latestTime);
			return listOfTweetIds;
		}
		return null;
	}
//			logger.info("All tweet Ids fetched");
//			for (Tweet tweet : listOfTweetIds) {
//				Tweet currentTweet = QueryTweet.getTweetByTweetId(tweet.getTweetId());
//				
//				logger.info(currentTweet.getTweetId() + " fetched");
//				listOfTweetsForHome.add(currentTweet);
//			}
//			return listOfTweetsForHome;
//		}
//		return null;
//	}
	
	public static boolean checkValidInput(long userId, long startTime, long latestTime) 
			throws ClassNotFoundException, SQLException {
		if (CheckValidity.isValidUser(userId)) {
			if (CheckValidity.isValidTime(startTime) && CheckValidity.isValidTime(latestTime)) {
				if (startTime < latestTime) {
					return true;
				} else {
					logger.error("start time greater than latest time" + startTime + " and " + latestTime);					
				}
			} else {
				logger.error("invalid timestamps received: " + startTime + " and " + latestTime);
			}
		} else {
			logger.error("invalid user id received: " + userId);
		}
		return false;
	}
}
