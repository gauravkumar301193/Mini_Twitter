package queryDatabase;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.Tweet;
import query.database.UpdateTweet;

public class UpdateTweetTest {

	@Test(expected=SQLException.class)
	public void testPostTweet() throws ClassNotFoundException, SQLException {
		
		Tweet twt = new Tweet();
		twt.setHandle("abcd");
		twt.setLikeCount(0);
		twt.setMediaId(Long.parseLong("0"));
		twt.setTimestamp(System.currentTimeMillis());
		twt.setTweetId(Tweet.generateTweetID());
		twt.setTweetText("Hey @Mayank how are you #feeling relaxed");
		twt.setUserId(2);
		
		assertTrue(UpdateTweet.postTweet(twt));
		
	}

	@Test
	public void testLikeTweet() throws ClassNotFoundException, SQLException {
		assertTrue(UpdateTweet.likeTweet(1, 2));
		assertTrue(UpdateTweet.unlikeTweet(1, 2));
	}

	@Test(expected=SQLException.class)
	public void testRetweetPost() throws ClassNotFoundException, SQLException {
		assertTrue(UpdateTweet.retweetPost("abcde", 1, 1, 3));
	}

	
	public void testUnlikeTweet() throws ClassNotFoundException, SQLException {
		assertTrue(UpdateTweet.unlikeTweet(1, 2));
		assertTrue(UpdateTweet.likeTweet(1, 2));
		
	}

	@Test
	public void testDeleteTweet() throws ClassNotFoundException, SQLException {
		assertTrue(UpdateTweet.deleteTweet(Tweet.generateTweetID() - 1));
	}


}
