package queryDatabase;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import database.dummy.dump.SQLConnection;
import models.Tweet;
import query.database.QueryTweet;

public class QueryTweetTest {
	
	@Test
	public void testGetTweetsWithHashtags() throws NumberFormatException, ClassNotFoundException, SQLException {
		
			List<Tweet> tweets = QueryTweet.getTweetsWithHashtags("feeling", 0, Long.parseLong("1472720664186"));
		
		
	}

	@Test
	public void testGetTweetsWithNoHashtag() throws ClassNotFoundException, SQLException {
			List<Tweet> tweets = QueryTweet.getTweetsWithHashtags("", 0, 1472020664);
			assertEquals(0, tweets.size());
	}
	
	@Test
	public void testGetTweetByTweetId() throws ClassNotFoundException, SQLException {
			Tweet twt = QueryTweet.getTweetByTweetId(1);
			assertEquals(1, twt.getUserId());
	}
	
	@Test
	public void testGetTweetByTweetIdWhichIsNotPresent() throws ClassNotFoundException, SQLException {
			Tweet twt = QueryTweet.getTweetByTweetId(0);
			assertTrue(twt==null);
	}
	
	@Test
	public void testCheckRetweetForUser() throws NumberFormatException, ClassNotFoundException, SQLException {
			 assertFalse(QueryTweet.checkRetweetForUser(Long.parseLong("1"), Long.parseLong("2649789")));
	}
	
	@Test
	public void testCheckRetweetForUserIfNotPresent() throws ClassNotFoundException, SQLException{
			 assertFalse(QueryTweet.checkRetweetForUser(Long.parseLong("8499845"), Long.parseLong("269789")));
	}

	@Test
	public void testGetTweetsByUserId() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getTweetsByUserId(2);
			assertEquals(3 , twts.size());
		}
	
	@Test
	public void testGetTweetsByUserIdWhenUserNotPresent() throws ClassNotFoundException, SQLException {
		
			List<Tweet> twts = QueryTweet.getTweetsByUserId(0);
			assertEquals(0 , twts.size());
	}
	

	@Test
	public void testGetAllRetweetForUser() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getAllRetweetForUser(1);
			assertEquals(0 , twts.size());
	}
	
	@Test
	public void testGetAllRetweetForUserWhichIsNotPresent() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getAllRetweetForUser(17);
			assertEquals(0 , twts.size());
	}

	@Test
	public void testGetAllHashtagInTweet() throws ClassNotFoundException, SQLException {
			List<String> hashtags = QueryTweet.getAllHashtagInTweet(2);
			assertEquals(1 , hashtags.size());
	}
	
	@Test
	public void testGetAllHashtagInTweetWhenTweetIdNotPresent() throws ClassNotFoundException, SQLException {
			List<String> hashtags = QueryTweet.getAllHashtagInTweet(17);
			assertEquals(0 , hashtags.size());
	}

	
	@Test
	public void testGetAllTweetsWithUserMention() throws ClassNotFoundException, SQLException {
			List<Tweet> mentions = QueryTweet.getAllTweetsWithUserMention(1);
			assertEquals(0 , mentions.size());
	}
	

	@Test
	public void testGetAllTweetsWithUserMentionWhenUserNotPresent() throws ClassNotFoundException, SQLException {
			List<Tweet> mentions = QueryTweet.getAllTweetsWithUserMention(17);
			assertEquals(0 , mentions.size());
		
	}
	

	@Test
	public void testGetAllLikesForTweet() throws ClassNotFoundException, SQLException {
			long likes = QueryTweet.getAllLikesForTweet(21);
			assertEquals(0, likes);
	}
	
	@Test
	public void testGetAllLikesForTweetWhenTweetNotPresent() throws ClassNotFoundException, SQLException {
			long likes = QueryTweet.getAllLikesForTweet(10);
			assertEquals(0, likes);
	}
	

	
	@Test
	public void testGetMentionsAfterTimestamp() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getMentionsAfterTimestamp(0, 2);
			assertEquals(0, twts.size());
	}

	@Test
	public void testGetMentionsAfterTimestampWhenUserNotPresent() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getMentionsAfterTimestamp(0, 0);
			assertEquals(0, twts.size());
	}

	@Test
	public void testCheckIfRetweet() throws ClassNotFoundException, SQLException {
			assertFalse(QueryTweet.checkIfRetweet(1, 17));
	}
	
	@Test
	public void testCheckIfRetweetWhenTweetNotPresent() throws ClassNotFoundException, SQLException{
		
			assertFalse(QueryTweet.checkIfRetweet(17, 17));
	}
	
	@Test
	public void testGetTweetsForUserProfile() throws NumberFormatException, ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getTweetsForUserProfile(1, 0, Long.parseLong("1472720664186"));
			assertEquals(1 , twts.size());
	}

	@Test
	public void testGetLikesAfterTimestamp() throws NumberFormatException, ClassNotFoundException, SQLException {
			int likes = QueryTweet.getLikesAfterTimestamp(0, 1);
			assertTrue(likes>=0);
	}

	public void testGetAllTweetsForUserHome() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getAllTweetsForParticularUser(17, 0, Long.parseLong("1472720664186"));
			assertTrue(twts.size() >= 0);
		
	}

	@Test
	public void testCheckTweetExists() throws ClassNotFoundException, SQLException {
				assertTrue(QueryTweet.checkTweetExists(2));
	}

	@Test
	public void testGetAllTweetsForParticularUser() throws ClassNotFoundException, SQLException {
			List<Tweet> twts = QueryTweet.getAllTweetsForParticularUser(1, 0, Long.parseLong("1472720664186"));
			assertTrue(twts.size() > 0);
	}

	@Test
	public void testCheckIsLiked() throws ClassNotFoundException, SQLException {
			assertFalse(QueryTweet.checkIsLiked(2156245, 8839862));
	}

	@Test
	public void testGetTweetCount() throws ClassNotFoundException, SQLException {
			long count = QueryTweet.getTweetCount(2);
			assertEquals(3, count);
	}

}
