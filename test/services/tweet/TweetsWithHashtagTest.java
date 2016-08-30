package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;
import query.database.QueryTweet;

public class TweetsWithHashtagTest {

	@Test
	public void testGetTweetsOfHashtags() {
		try {
			List<Tweet> twts = QueryTweet.getTweetsWithHashtags("WeCantWait" ,0 , Long.parseLong("1472020664186"));
			assertTrue(twts.size() > 0);
			assertTrue(twts.get(0).getUserId() > 0);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsOfHashtagsWhenHashtagIsNotPresent() {
		try {
			List<Tweet> twts = QueryTweet.getTweetsWithHashtags("" ,0 , Long.parseLong("1472020664186"));
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsOfHashtagsWhenInvalidTime() {
		try {
			List<Tweet> twts = TweetsWithHashtag.getTweetsOfHashtags("jobs" , Long.parseLong("1472020664186") ,0);
			assertEquals(null , twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsOfHashtagsWhenInvalidStartTime() {
		try {
			List<Tweet> twts = TweetsWithHashtag.getTweetsOfHashtags("jobs" , -1  ,Long.parseLong("1472020664186"));
			assertEquals(null , twts);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
