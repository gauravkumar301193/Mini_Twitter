package services.tweet;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import models.Tweet;
import query.database.QueryTweet;
import response.util.CheckValidity;
/**
 * @author mayank.ra
 */
public class TweetsWithHashtag {
	
	static Logger logger = Logger.getLogger(TweetsWithHashtag.class);
	public static List<Tweet> getTweetsOfHashtags(String hashtag, long startTime, long latestTime) 
			throws ClassNotFoundException, SQLException {
		if (CheckValidity.isValidTime(startTime) && CheckValidity.isValidTime(latestTime)) {
			if (startTime < latestTime) {
				return QueryTweet.getTweetsWithHashtags(hashtag, startTime,  latestTime);				
			} else {
				logger.error("latest time is smaller than the start time: " + latestTime + ", " + startTime);
			}
		} else {
			logger.error("input timestamps are invalid: " + startTime + ", " + latestTime);
		}
		return null;
	}
}
