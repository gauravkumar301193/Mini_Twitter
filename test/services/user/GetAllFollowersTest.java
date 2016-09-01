package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.User;

public class GetAllFollowersTest {

	@Test
	public void testGetAllFollowers() throws ClassNotFoundException, SQLException {
		 List<User> users;
			users = GetAllFollowers.getAllFollowers(1);
			assertTrue(users.size() > 0);
		
	}
	
	@Test
	public void testGetAllFollowersWhenInvalidUser() throws ClassNotFoundException, SQLException {
		 List<User> users = GetAllFollowers.getAllFollowers(-20);
			assertTrue(users == null);
	}
}
