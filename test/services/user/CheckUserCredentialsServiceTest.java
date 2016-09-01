package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class CheckUserCredentialsServiceTest {

	@Test
	public void testCheckIfUserExists() throws ClassNotFoundException, SQLException {
		Long userId = CheckUserCredentials.checkIfUserExists("abc@mail.com", "abc");
		assertTrue(userId == 1);
	}
	
	@Test(expected=SQLException.class)
	public void testCheckIfUserExistsWhenInvalidPassword() throws ClassNotFoundException, SQLException {
		Long userId = CheckUserCredentials.checkIfUserExists("abc@mail.com", "abcd");
		assertFalse(userId == 1);
	}
}
