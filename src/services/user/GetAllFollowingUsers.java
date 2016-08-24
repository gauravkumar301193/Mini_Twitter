package services.user;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import query.database.*;
import response.util.CheckValidity;
import models.*;

/**
 * @author mayank.ra
 */

public class GetAllFollowingUsers {
	
	static Logger logger = Logger.getLogger(GetAllFollowers.class);
	public static List<User> getAllFollowingUsers(long userId)
			throws ClassNotFoundException, SQLException {
		if (CheckValidity.isValidUser(userId)) {
			return QueryUser.getAllFollowing(userId);
		} else {
			logger.error("Invaid user id received: " + userId);
		}
		return null;
	}
}
