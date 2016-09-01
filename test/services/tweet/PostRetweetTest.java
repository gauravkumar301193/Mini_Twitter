package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class PostRetweetTest {
	@Test(expected=SQLException.class)
	public void testRetweetPost() throws ClassNotFoundException, SQLException {
			assertTrue(PostRetweet.retweetPost("abc", 2 , 2, 1));
	}
	
	@Test
	public void testRetweetPostWhenInvalidTweetId() throws ClassNotFoundException, SQLException {
			assertFalse(PostRetweet.retweetPost("abc", - 20, 50, 17));
	}
	
	@Test
	public void testRetweetPostWhenInvalidLoggedInUser() throws ClassNotFoundException, SQLException {
			assertFalse(PostRetweet.retweetPost("Chloes", 20, 50, -100));
	}
	

	@Test
	public void testRetweetPostWhenInvalidAuthorId() throws ClassNotFoundException, SQLException {
			assertFalse(PostRetweet.retweetPost("Chloes", 20, -50 , 17));
	}
}
