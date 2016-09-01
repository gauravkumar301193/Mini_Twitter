package queryDatabase;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.User;
import query.database.UpdateUser;

public class UpdateUserTest {

	@Test
	public void testRegisterUser() {
		
		try {
				User usr = new User();
				usr.setEmail("abc@mail.com");
				usr.setUserId(User.generateUserID());
				usr.setFollower(0);
				usr.setFollowing(0);
				usr.setLogout(null);
				usr.setPassword("abc");
				usr.setTweetCount(0);
				usr.setUserName("abc");
				usr.setHandle("abc");
				
				User usr1 = new User();
				usr1.setEmail("abcd@mail.com");
				usr1.setUserId(User.generateUserID());
				usr1.setFollower(0);
				usr1.setFollowing(0);
				usr1.setLogout(null);
				usr1.setPassword("abcd");
				usr1.setTweetCount(0);
				usr1.setUserName("abcd");
				usr1.setHandle("abcd");
				
				User usr2 = new User();
				usr2.setEmail("abcde@mail.com");
				usr2.setUserId(User.generateUserID());
				usr2.setFollower(0);
				usr2.setFollowing(0);
				usr2.setLogout(null);
				usr2.setPassword("abcde");
				usr2.setTweetCount(0);
				usr2.setUserName("abcde");
				usr2.setHandle("abcde");
				
				
				assertTrue(UpdateUser.registerUser(usr2));
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFollowUser() {
		try {
				assertTrue(UpdateUser.followUser(1, 2));
				assertTrue(UpdateUser.followUser(2, 1));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		

	@Test
	public void testUnfollowUser() {
		try {
			assertTrue(UpdateUser.unfollowUser(1, 2));
	} catch(Exception e) {
		e.printStackTrace();
	}
	}


	@Test
	public void testDeleteUser() {
		try {
			assertTrue(UpdateUser.deleteUser(3));
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	
}
