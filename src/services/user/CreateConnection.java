package services.user;
/**
 * @author gaurav.kum
 */
import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.User;
import query.database.QueryUser;
import query.database.UpdateUser;
import response.util.CheckValidity;

public class CreateConnection {


	static Logger logger = Logger.getLogger(CreateConnection.class);
	public static boolean followUser(long userId, long userToFollowId) throws ClassNotFoundException, SQLException {
		
		if(CheckValidity.isValidUser(userId) && CheckValidity.isValidUser(userToFollowId) && !QueryUser.isConnection(userId, userToFollowId)) {
			return UpdateUser.followUser(userId, userToFollowId);
		}
		else {
			return false;
		}
		
	}
}
