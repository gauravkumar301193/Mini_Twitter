package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class LikeTweetServiceTest {

	@Test
	public void testLikeTweet() throws ClassNotFoundException, SQLException {
			assertTrue(LikeTweetService.likeTweet(1, 2));
			assertTrue(UnlikeTweetService.unlikeTweet(1, 2));
	}
	
	@Test
	public void testLikeTweetWhenInvalidUser() throws ClassNotFoundException, SQLException {
			assertFalse(LikeTweetService.likeTweet(-1, 15));
	}
	
	@Test
	public void testLikeTweetWhenInvalidTweet() throws ClassNotFoundException, SQLException {
			assertFalse(LikeTweetService.likeTweet(16, 0));
	}

}
