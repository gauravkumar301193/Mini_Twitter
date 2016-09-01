package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.User;

public class GetAllFollowingTest {

	@Test
	public void testGetAllFollowing() throws ClassNotFoundException, SQLException {
		 List<User> users;
		
			users = GetAllFollowingUsers.getAllFollowingUsers(2);
			assertTrue(users.size() > 0);
		
	}
	
	@Test
	public void testGetAllFollowingWhenInvalidUser() throws ClassNotFoundException, SQLException {
		 List<User> users;
		
			users = GetAllFollowingUsers.getAllFollowingUsers(-20);
			assertTrue(users == null);
		
	}
}
