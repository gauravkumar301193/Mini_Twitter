package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.Tweet;

public class RemoveTweetTest {


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
	
	@Test
	public void testDeleteTweetWhenWrongTweet() {
		try {
			assertFalse(RemoveTweet.deleteTweet(-1));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
