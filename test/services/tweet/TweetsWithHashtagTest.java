package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;
import query.database.QueryTweet;

public class TweetsWithHashtagTest {

	@Test
	public void testGetTweetsOfHashtags() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getTweetsWithHashtags("feeling" ,0 , Long.parseLong("1472720664186"));
			assertEquals(3, twts.size());
	}
	
	@Test
	public void testGetTweetsOfHashtagsWhenHashtagIsNotPresent() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getTweetsWithHashtags("" ,0 , Long.parseLong("1472020664186"));
			assertEquals(0 , twts.size());
	}
	
	@Test
	public void testGetTweetsOfHashtagsWhenInvalidTime() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = TweetsWithHashtag.getTweetsOfHashtags("jobs" , Long.parseLong("1472020664186") ,0);
			assertEquals(null , twts);
	}
	
	@Test
	public void testGetTweetsOfHashtagsWhenInvalidStartTime() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = TweetsWithHashtag.getTweetsOfHashtags("jobs" , -1  ,Long.parseLong("1472020664186"));
			assertEquals(null , twts);
		
	}

}
