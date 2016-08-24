package services.user;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import models.User;

public class UserTestDb {
	static Logger logger = Logger.getLogger(UserTestDb.class);
	public static void main(String[] ar) throws ClassNotFoundException, SQLException {
		//AddNewUser addNewUser = new AddNewUser();
		
//		User user = new User();
//		
//		user.setEmail("abc6@gmail.com");
//		user.setHandle("abc23456");
//		user.setPassword("abc23456");
//		user.setUserName("abc23456");
//		user.setUserId(User.generateUserID());
//		user.setLogout(0);
//
//		addNewUser.addNewUser(user);
//		logger.info("user added successfully");
////		
//		CheckUserCredentials chkUserCredentials = new CheckUserCredentials();
//		
//		if(chkUserCredentials.checkIfUserExists("abc6@gmail.com", "abc3456")) {
//			logger.info("valid user");
//		}
//		else {
//			logger.info("user not present");
//		}
		
		
//		DeleteConnection delteConnection = new DeleteConnection();
//		
//		if(DeleteConnection.unfollowUser(12346, 12345)) {
//			logger.info("unfollowed successfully");
//		}
//		else {
//			logger.info("user not present");
//		}
		
//		GetAllFollowers getAllFollowerUsers = new GetAllFollowers();
//		
//		 List<User> users = getAllFollowerUsers.getAllFollowers(1);
//		 
//		 for(User user : users) {
//			 logger.info(user.getHandle());
//		 }
		
//		

		RemoveUser removeUser = new RemoveUser();
		if(removeUser.deleteUser(1)) {
			logger.info("removed successfully");
		}
		else {
			logger.info("can't be removed");
		}
	}
}
