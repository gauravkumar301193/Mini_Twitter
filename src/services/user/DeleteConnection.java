package services.user;
/**
 * @author gaurav.kum
 */
import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.User;
import query.database.QueryUser;
import query.database.UpdateUser;

public class DeleteConnection {
	
	static Logger logger = Logger.getLogger(DeleteConnection.class);
	public static boolean unfollowUser(long userId, long userToUnfollowId) throws ClassNotFoundException, SQLException {
		
		
			if(QueryUser.isConnection(userId, userToUnfollowId)) {
				return UpdateUser.unfollowUser(userId, userToUnfollowId);
			}
			else {
				logger.error("Invalid user to unfollow");
				return false;
		}

	}
}
