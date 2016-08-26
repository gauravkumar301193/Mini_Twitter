package services.user;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.User;
import query.database.QueryUser;
import response.util.CheckValidity;

public class FetchUserDetails {

	private static final Logger logger = Logger.getLogger(GetAllFollowers.class);
	public static User getUserDetails(long userId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		if(CheckValidity.isValidUser(userId)){
			return QueryUser.getUserDetailsFromDb(userId);
		}
		else {
			logger.error("invalid user id received: " + userId);
		}
		
		return null;
	}

}
