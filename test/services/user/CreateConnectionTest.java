package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class CreateConnectionTest {

	@Test
	public void testFollowUserWhenInvalidUserToFollowId() throws ClassNotFoundException, SQLException {
			assertFalse(CreateConnection.followUser(19, -1));
	}
	

	@Test
	public void testFollowUserWhenInvalidFollowerId() throws ClassNotFoundException, SQLException {
			assertFalse(CreateConnection.followUser(-50, 19));
	}
	

}
