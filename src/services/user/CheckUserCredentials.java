package services.user;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import query.database.QueryTweet;
import query.database.QueryUser;

public class CheckUserCredentials {

	static Logger logger = Logger.getLogger(CheckUserCredentials.class);
	public static Long checkIfUserExists(String emailId, String password) 
			throws ClassNotFoundException, SQLException {
		Long userId = QueryUser.checkUserCredentials(emailId, password);
		if (userId != null) {
			return userId;
		} else {
			logger.error("user does not exists: " + emailId);
		}
		return null;
	}
}
