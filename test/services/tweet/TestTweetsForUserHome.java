package services.tweet;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;
import services.tweet.TweetsForUserHome;

public class TestTweetsForUserHome {

	@Test
	public void testGetTweetsForUserHomeWhenUserNotPresent() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(10 , 0 , Long.parseLong("1472020664186"));
			assertEquals(null, twts);
		
	}
	
	@Test
	public void testGetTweetsForUserHomeWhenInvalidTimestamp() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(10 , Long.parseLong("1472020664186") , 0);
			assertEquals(null, twts);
	}
	
	@Test
	public void testGetTweetsForUserHomeWhenInvalidStartTime() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(10 , -1 , 100);
			assertEquals(null, twts);
	}
	
	@Test
	public void testGetTweetsForUserHomeWhenInvalidEndTime() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(10 , 100 , -1);
			assertEquals(null, twts);
	}
	
	@Test
	public void testCheckValidInput() throws NumberFormatException, ClassNotFoundException, SQLException {
			assertTrue(TweetsForUserHome.checkValidInput(1, 0, Long.parseLong("1472020664186")));
	}
	
	@Test
	public void testCheckValidInputWhenWrongUserId() throws NumberFormatException, ClassNotFoundException, SQLException {
			assertFalse(TweetsForUserHome.checkValidInput(-1, 0, Long.parseLong("1472020664186")));
	}
	

	@Test
	public void testGetTweetsForUserHome() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(1 , 0 , Long.parseLong("1472720664186"));
			assertEquals(null, twts);
	}

}
