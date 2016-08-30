package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class GetLikesAfterTimestampTest {

	@Test
	public void testLikesAfterLogout() {
		try {
			int likeCount = GetLikesAfterTimestamp.likesAfterLogout(2622766);
			assertTrue(likeCount >=0 );
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLikesAfterLogoutWhenInvalidUser() {
		try {
			int likeCount = GetLikesAfterTimestamp.likesAfterLogout(-50);
			assertEquals(0, likeCount);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
