package services.tweet;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import models.RetweetModel;
import models.Tweet;
import models.User;
import query.database.QueryTweet;
import query.database.QueryUser;
import response.util.*;

/**
 * @author mayank.ra
 */
public class RetweetUsersAfterLogout {
	
	static Logger logger = Logger.getLogger(RetweetUsersAfterLogout.class);
	public static List<RetweetModel> getRetweetUsersAfterLogout(long userId) 
			throws ClassNotFoundException, SQLException {
		
		if (CheckValidity.isValidUser(userId)) {
			long logoutTime = QueryUser.getLastLogout(userId);
			logger.info("user last logout time: " + logoutTime);
			return QueryUser.getRetweetUsersAfterLogout(userId, logoutTime);
		} else {
			logger.error("invalid user id recived: " + userId);
		}
		return null;
	}
	
}
