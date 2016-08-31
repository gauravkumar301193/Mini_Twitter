package services.tweet;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;

public class GetTweetsForUserProfileTest {

	@Test
	public void testGetTweetsForUserProfile() {
		try {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(50 , 0 , Long.parseLong("1472020664186"));
			assertTrue(twts.size() > 0);
			assertTrue(twts.get(0).getUserId() > 0);		
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTweetsForUserProfileWhenInvalidUserId() {
		try {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(-1 , 0 , Long.parseLong("1472020664186"));
			assertEquals(null, twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsForUserProfileWhenInvalidTimestamp() {
		try {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(10 , Long.parseLong("1472020664186") , 0);
			assertEquals(null, twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsForUserProfileWhenInvalidStartTime() {
		try {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(10 , -1 , 100);
			assertEquals(null, twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsForUserProfileWhenInvalidEndTime() {
		try {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(10 , 100 , -1);
			assertEquals(null, twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
