package services.user;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import models.User;
import query.database.*;
import response.util.CheckValidity;
/**
 * @author mayank.ra
 */
public class GetAllFollowers {

	static Logger logger = Logger.getLogger(GetAllFollowers.class);
	public static List<User> getAllFollowers(long userId) 
			throws ClassNotFoundException, SQLException {
		if (CheckValidity.isValidUser(userId)) {
			return QueryUser.getAllFollowers(userId);
		} else {
			logger.error("invalid user id received: " + userId);
		}
		return null;
	}
}
