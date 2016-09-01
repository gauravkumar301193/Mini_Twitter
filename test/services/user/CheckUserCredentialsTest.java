package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import query.database.QueryUser;

public class CheckUserCredentialsTest {

	@Test
	public void testCheckIfUserExists() throws ClassNotFoundException, SQLException {
		assertTrue(QueryUser.checkUserExists(1));
	}
	
	@Test
	public void testCheckIfUserExistsWhenUserNotPresent() throws ClassNotFoundException, SQLException {
		assertFalse(QueryUser.checkUserExists(0));
	}

}
