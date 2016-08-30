package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.User;

public class FetchUserDetailsTest {

	@Test
	public void testGetUserDetails() {
		try {
			User user = FetchUserDetails.getUserDetails(17);
			assertEquals("ChloeS", user.getHandle());
			assertEquals("ChloeS@mail.com", user.getEmail());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetUserDetailsWhenInvalidUser() {
		try {
			User user = FetchUserDetails.getUserDetails(1);
			assertEquals(null, user);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
