package services.user;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.junit.Test;

public class UpdateUserServiceTest {

	@Test
	public void testUpdateEmail() throws ClassNotFoundException, SQLException {
		assertTrue(	UpdateUserService.updateEmail("mayank@mail.com", (long) 1));
		UpdateUserService.updateEmail("abc@mail.com", (long) 1);
	}


	@Test
	public void testUpdateUserName() throws ClassNotFoundException, SQLException {
		assertTrue(	UpdateUserService.updateUserName("abcd", (long) 1));
		UpdateUserService.updateUserName("abc", (long) 1);

	}
}
