package services.tweet;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;

public class GetTweetsForUserProfileTest {

	@Test
	public void testGetTweetsForUserProfile() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(1 , 0 , Long.parseLong("1472020664186"));
			assertEquals(0, twts.size());	
	}

	@Test
	public void testGetTweetsForUserProfileWhenInvalidUserId() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(-1 , 0 , Long.parseLong("1472020664186"));
			assertEquals(null, twts);
	}
	
	@Test
	public void testGetTweetsForUserProfileWhenInvalidTimestamp() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(10 , Long.parseLong("1472020664186") , 0);
			assertEquals(null, twts);
	}
	
	@Test
	public void testGetTweetsForUserProfileWhenInvalidStartTime() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(10 , -1 , 100);
			assertEquals(null, twts);
	}
	
	@Test
	public void testGetTweetsForUserProfileWhenInvalidEndTime() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = GetTweetsForUserProfile.tweetsForUserProfile(10 , 100 , -1);
			assertEquals(null, twts);
	}
	

}
