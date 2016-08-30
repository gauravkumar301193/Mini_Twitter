package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class PostRetweetTest {

	@Test
	public void testRetweetPostWhenInvalidTweetId() {
		try {
			assertFalse(PostRetweet.retweetPost("Chloes", - 20, 50, 17));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRetweetPostWhenInvalidLoggedInUser() {
		try {
			assertFalse(PostRetweet.retweetPost("Chloes", 20, 50, -100));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void testRetweetPostWhenInvalidAuthorId() {
		try {
			assertFalse(PostRetweet.retweetPost("Chloes", 20, -50 , 17));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
