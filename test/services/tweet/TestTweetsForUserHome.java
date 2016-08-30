package services.tweet;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;
import services.tweet.TweetsForUserHome;

public class TestTweetsForUserHome {


	@Test
	public void testGetTweetsForUserHome() {
		try {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(10 , 0 , Long.parseLong("1472020664186"));
			assertTrue(twts.size() > 0);
			assertTrue(twts.get(0).getUserId() > 0);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTweetsForUserHomeWhenUserNotPresent() {
		try {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(1 , 0 , Long.parseLong("1472020664186"));
			assertEquals(null, twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsForUserHomeWhenInvalidTimestamp() {
		try {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(10 , Long.parseLong("1472020664186") , 0);
			assertEquals(null, twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsForUserHomeWhenInvalidStartTime() {
		try {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(10 , -1 , 100);
			assertEquals(null, twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsForUserHomeWhenInvalidEndTime() {
		try {
			List<Tweet> twts = TweetsForUserHome.getTweetsForUserHome(10 , 100 , -1);
			assertEquals(null, twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckValidInput() {
		try {
			assertTrue(TweetsForUserHome.checkValidInput(10, 0, Long.parseLong("1472020664186")));
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckValidInputWhenWrongUserId() {
		try {
			assertFalse(TweetsForUserHome.checkValidInput(-1, 0, Long.parseLong("1472020664186")));
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
