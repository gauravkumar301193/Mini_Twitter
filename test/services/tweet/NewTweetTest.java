package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.Tweet;
import request.controller.tweet.DeleteTweetGivenId;

public class NewTweetTest {

	@Test
	public void testPostTweet() throws ClassNotFoundException, SQLException {
		Tweet twt = new Tweet();
		twt.setTweetId(Tweet.generateTweetID());
		twt.setTweetText("Hello Mayank");
		twt.setUserId(1);
		twt.setMediaId(Long.parseLong("0"));
		assertTrue(NewTweet.postTweet(twt));
		assertTrue(RemoveTweet.deleteTweet(Tweet.generateTweetID() - 1));	
	}
	
	@Test(expected=SQLException.class)
	public void testPostTweetWhenInvalidUser() throws ClassNotFoundException, SQLException {
		Tweet twt = new Tweet();
		twt.setTweetId(Tweet.generateTweetID());
		twt.setTweetText("Hello Mayank");
		twt.setUserId(-1);
		twt.setMediaId(Long.parseLong("0"));
		assertFalse(NewTweet.postTweet(twt));
	}
}
