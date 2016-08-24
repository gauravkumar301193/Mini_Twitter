package services.user;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import query.database.QueryTweet;
import query.database.QueryUser;

public class CheckUserCredentials {

	static Logger logger = Logger.getLogger(CheckUserCredentials.class);
	public static boolean checkIfUserExists(String emailId, String password) 
			throws ClassNotFoundException, SQLException {
		if (QueryUser.checkUserCredentials(emailId, password)) {
			return true;
		} else {
			logger.error("user does not exists: " + emailId);
		}
		return false;
	}
}
