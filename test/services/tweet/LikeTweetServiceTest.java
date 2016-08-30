package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class LikeTweetServiceTest {

	@Test
	public void testLikeTweet() {
		try {
			assertTrue(LikeTweetService.likeTweet(17, 15));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLikeTweetWhenInvalidUser() {
		try {
			assertFalse(LikeTweetService.likeTweet(-1, 15));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLikeTweetWhenInvalidTweet() {
		try {
			assertFalse(LikeTweetService.likeTweet(16, 1));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
