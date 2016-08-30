package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.User;

public class GetAllFollowingTest {

	@Test
	public void testGetAllFollowing() {
		 List<User> users;
		try {
			users = GetAllFollowingUsers.getAllFollowingUsers(10);
			assertTrue(users.size() > 0);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllFollowingWhenInvalidUser() {
		 List<User> users;
		try {
			users = GetAllFollowingUsers.getAllFollowingUsers(-20);
			assertTrue(users == null);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
