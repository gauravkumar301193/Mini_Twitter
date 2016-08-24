package services.user;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import models.User;
import query.database.QueryUser;
import response.util.CheckValidity;

public class GetFollowersAfterLogout {
	
	static Logger logger = Logger.getLogger(GetAllFollowers.class);
	public static List<User> getFollowersAfterLogout(long userId) 
			throws ClassNotFoundException, SQLException {
		if (CheckValidity.isValidUser(userId)) {
			long logoutTime = QueryUser.getLastLogout(userId);
			return QueryUser.getFollowersAfterTimestamp(userId,logoutTime);
		} else {
			logger.error("Invalid user id received: " + userId);
		}
		return null;
	}
}
