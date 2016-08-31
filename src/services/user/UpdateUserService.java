package services.user;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import database.dummy.dump.SQLConnection;
import models.User;
import response.util.CheckValidity;

public class UpdateUserService {

	private static final Logger logger = Logger.getLogger(UpdateUserService.class);

	public static boolean updateEmail(String email, Long userId) throws ClassNotFoundException, SQLException {
		if(CheckValidity.isValidUser(userId)) {
		StringBuilder query = new StringBuilder("update authentication set email_id = \"")
				.append(email).append("\" where user_id = ").append(userId);
		logger.info("executing sql query : " + query.toString());
		int rs = SQLConnection.executeUpdate(query.toString());
		return rs > 0;
		}
		return false;
		
		
	}

	public static boolean updatePassword(String password, Long userId) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		if(CheckValidity.isValidUser(userId)) {
		password = User.encryptPassword(password);
		
		StringBuilder query = new StringBuilder("update authentication set password = \"")
				.append(password).append("\" where user_id = ").append(userId);
		logger.info("executing sql query : " + query.toString());
		int rs = SQLConnection.executeUpdate(query.toString());
		
			return rs > 0;
		}
		return false;
	}

	public static boolean updateUserName(String username, Long userId) throws ClassNotFoundException, SQLException {
		if(CheckValidity.isValidUser(userId)) {
		StringBuilder query = new StringBuilder("update authentication set name = \"")
				.append(username).append("\" where user_id = ").append(userId);
		logger.info("executing sql query : " + query.toString());
		int rs = SQLConnection.executeUpdate(query.toString());
		return rs > 0;
		}
		return false;
	}

}
