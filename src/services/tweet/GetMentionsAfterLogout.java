package services.tweet;

import query.database.QueryTweet;
import query.database.QueryUser;
import response.util.CheckValidity;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import models.Tweet;

/**
 * @author mayank.ra
 */
public class GetMentionsAfterLogout {

	static Logger logger = Logger.getLogger(GetMentionsAfterLogout.class);
	public static List<Tweet> getMentionsAfterLogout(long userId) 
			throws ClassNotFoundException, SQLException {

		if (CheckValidity.isValidUser(userId)) {
			long logoutTime = QueryUser.getLastLogout(userId);
			logger.info("user logout time: " + logoutTime);
			return QueryTweet.getMentionsAfterTimestamp(logoutTime, userId);
		} else {
			logger.error("Wrong user id received: " + userId);
		}
		return null;
	}
}
