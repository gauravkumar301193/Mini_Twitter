package services.user;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.User;
import query.database.QueryUser;
import query.database.UpdateUser;

/**
 * @author gaurav.kum
 */
public class RemoveUser {
	
	static Logger logger = Logger.getLogger(RemoveUser.class);
	public static boolean deleteUser(long userId) throws ClassNotFoundException, SQLException {
		
		if(QueryUser.checkUserExists(userId)) {
			return UpdateUser.deleteUser(userId);
		}
		else {
			logger.error("Inalid user to delete: " + userId);
		return false;
		}
	}
}
