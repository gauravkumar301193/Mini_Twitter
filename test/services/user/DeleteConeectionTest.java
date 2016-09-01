package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class DeleteConeectionTest {


	@Test
	public void testFollowUserWhenInvalidUserToUnfollowId() throws ClassNotFoundException, SQLException {
			assertFalse(DeleteConnection.unfollowUser(17, -40));
	}
	

	@Test
	public void testFollowUserWhenInvalidUserWhoWantToUnfollowId() throws ClassNotFoundException, SQLException {
			assertFalse(DeleteConnection.unfollowUser(-50, 17));
	}
	

	@Test
	public void testUnFollowUser() throws ClassNotFoundException, SQLException {
			CreateConnection.followUser(2, 3);
			assertTrue(DeleteConnection.unfollowUser(2, 3));
			
	}
}
