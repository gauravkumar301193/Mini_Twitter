package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class GetLikesAfterTimestampTest {

	@Test
	public void testLikesAfterLogout() throws ClassNotFoundException, SQLException {
			int likeCount = GetLikesAfterTimestamp.likesAfterLogout(1);
			assertEquals(0 , likeCount);
	}
	
	@Test
	public void testLikesAfterLogoutWhenInvalidUser() throws ClassNotFoundException, SQLException {
			int likeCount = GetLikesAfterTimestamp.likesAfterLogout(-50);
			assertEquals(0, likeCount);
	}

}
