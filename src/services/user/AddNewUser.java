package services.user;
/**
 * @author gaurav.kum
 */
import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.User;
import query.database.QueryUser;
import query.database.UpdateUser;

public class AddNewUser {
	
	static Logger logger = Logger.getLogger(AddNewUser.class);
	public static boolean addNewUser(User user) throws ClassNotFoundException, SQLException {
		String email_id = user.getEmail();
		logger.info("email Id of user is: " + email_id);
		
		String handle = user.getHandle();
		logger.info("handle of user is: " + handle);
		
		if(!(QueryUser.checkEmailExists(email_id)) && !(QueryUser.checkHandleExists(handle))){
			
			if(UpdateUser.registerUser(user)) {
				return true;
			}
			else {
				logger.error("Invaid user received: " + user);
				return false;
			}
		}
		else {
			return false;
		}
	}
}
