package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.User;

public class RemoveUserTest {

	@Test
	public void testDeleteUser() throws ClassNotFoundException, SQLException {
		User user = new User();
		user.setEmail("mayank@mail.com");
		user.setFollower(0);
		user.setFollowing(0);
		user.setHandle("mayank");
		user.setUserId(User.generateUserID());
		
		AddNewUser.addNewUser(user);
		assertTrue(RemoveUser.deleteUser(User.generateUserID() - 1));
	}

}
