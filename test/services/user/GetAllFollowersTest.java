package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.User;

public class GetAllFollowersTest {

	@Test
	public void testGetAllFollowers() {
		 List<User> users;
		try {
			users = GetAllFollowers.getAllFollowers(17);
			assertTrue(users.size() > 0);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllFollowersWhenInvalidUser() {
		 List<User> users;
		try {
			users = GetAllFollowers.getAllFollowers(-20);
			assertTrue(users == null);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
