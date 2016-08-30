package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class DeleteConeectionTest {


	@Test
	public void testFollowUserWhenInvalidUserToUnfollowId() {
		try {
			assertFalse(DeleteConnection.unfollowUser(17, -40));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void testFollowUserWhenInvalidUserWhoWantToUnfollowId() {
		try {
			assertFalse(DeleteConnection.unfollowUser(-50, 17));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
