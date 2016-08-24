package services.user;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import models.Tweet;
import query.database.QueryTweet;
import query.database.UpdateTweet;
import response.util.CheckValidity;

public class GetAllTweetsForUserIdService {

	static Logger logger = Logger.getLogger(UpdateTweet.class);
	public static List<Tweet> allTweetsForUserId(long userId, long startTime, long latestTime) throws ClassNotFoundException, SQLException {
		if (CheckValidity.isValidUser(userId)) {
			if (CheckValidity.isValidTime(startTime) && CheckValidity.isValidTime(latestTime)) {
				if (startTime <= latestTime) {
					return QueryTweet.getAllTweetsForParticularUser(userId, startTime, latestTime);
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
