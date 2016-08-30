package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class CreateConnectionTest {

	@Test
	public void testFollowUserWhenInvalidUserToFollowId() {
		try {
			assertFalse(CreateConnection.followUser(19, -1));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void testFollowUserWhenInvalidFollowerId() {
		try {
			assertFalse(CreateConnection.followUser(-50, 19));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
