package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.User;

public class AddNewUserTest {
	
	@Test
	public void testAddNewUser() throws ClassNotFoundException, SQLException {
		User user = new User();
		user.setEmail("mayank@mail.com");
		user.setFollower(0);
		user.setFollowing(0);
		user.setHandle("mayank");
		user.setUserId(User.generateUserID());
		
		assertTrue(AddNewUser.addNewUser(user));
		RemoveUser.deleteUser(User.generateUserID() - 1);
	}

}
