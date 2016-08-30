package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class RemoveTweetTest {

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
