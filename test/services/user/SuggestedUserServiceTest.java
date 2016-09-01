package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.User;

public class SuggestedUserServiceTest {

	@Test
	public void testGetAllUsersForUsername() throws ClassNotFoundException, SQLException {
		List<User> users = SuggestedUsersService.getAllUsersForUsername("ab");
		
		assertEquals(2 , users.size());
	}
	
}
