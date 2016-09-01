package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.User;

public class FetchUserDetailsTest {

	@Test
	public void testGetUserDetails() throws ClassNotFoundException, SQLException {
			User user = FetchUserDetails.getUserDetails(1);
			assertEquals("abc", user.getUserName());
	}
	
	@Test
	public void testGetUserDetailsWhenInvalidUser() throws ClassNotFoundException, SQLException {
			User user = FetchUserDetails.getUserDetails(0);
			assertEquals(null, user);
	}

}
