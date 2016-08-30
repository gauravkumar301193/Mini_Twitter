package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import models.Tweet;

public class NewTweetTest {

	@Test
	public void testPostTweet() {
		Tweet twt = new Tweet();
		try {
			twt.setTweetId(Tweet.generateTweetID());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		twt.setTweetText("Hello Mayank");
		twt.setUserId(17);
		try {
			boolean status = NewTweet.postTweet(twt);
			assertTrue(status);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPostTweetWhenInvalidUser() {
		Tweet twt = new Tweet();
		try {
			twt.setTweetId(Tweet.generateTweetID());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		twt.setTweetText("Hello Mayank");
		twt.setUserId(-1);
		try {
			boolean status = NewTweet.postTweet(twt);
			assertFalse(status);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
