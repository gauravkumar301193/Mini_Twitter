package services.tweet;

import models.Tweet;
import org.apache.log4j.Logger;
import query.database.QueryTweet;
import response.util.CheckValidity;

import java.sql.SQLException;
import java.util.List;
/**
 * @author gaurav.kum
 *
 */
public class GetTweetsForUserProfile {
	
	static Logger logger = Logger.getLogger(GetTweetsForUserProfileTest.class);
	public static List<Tweet> tweetsForUserProfile(long userId, long startTime, long latestTime)
			throws ClassNotFoundException, SQLException {
		
		if (CheckValidity.isValidUser(userId)) {
			if (CheckValidity.isValidTime(startTime) && CheckValidity.isValidTime(latestTime)) {
				if (startTime <= latestTime) {
					return QueryTweet.getTweetsForUserProfile(userId, startTime, latestTime);
				} else {
					logger.error("start time is greater than the latest time");
				}
			} else {
				logger.error("invalid timestamps provided: " + startTime + ", " + latestTime);
			}
		} else {
			logger.error("invalid user id provided: " + userId);
		}
		return null;
	}
}
