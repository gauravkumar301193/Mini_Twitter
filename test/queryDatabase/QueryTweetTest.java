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
	public void testGetTweetsWithHashtags() {
		//StringBuilder query = new StringBuilder("select * from hashtags where hash_name = \'WeCantWait\' ");
		try {
			List<Tweet> tweets = QueryTweet.getTweetsWithHashtags("WeCantWait", 0, Long.parseLong("1472020664186"));
			assertEquals(10, tweets.size());
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetTweetsWithNoHashtag() {
		try {
			List<Tweet> tweets = QueryTweet.getTweetsWithHashtags("", 0, 1472020664);
			assertEquals(0, tweets.size());
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetByTweetId() {
		try {
			Tweet twt = QueryTweet.getTweetByTweetId(42);
			assertEquals(34, twt.getUserId());
			assertEquals("OHMichael", twt.getHandle());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetTweetByTweetIdWhichIsNotPresent() {
		try {
			Tweet twt = QueryTweet.getTweetByTweetId(1);
			assertTrue(twt==null);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testCheckRetweetForUser() {
		try {
			 assertTrue(QueryTweet.checkRetweetForUser(Long.parseLong("8499846"), Long.parseLong("2649789")));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckRetweetForUserIfNotPresent() {
		try {
			 assertFalse(QueryTweet.checkRetweetForUser(Long.parseLong("8499845"), Long.parseLong("269789")));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTweetsByUserId() {
		try {
			List<Tweet> twts = QueryTweet.getTweetsByUserId(17);
			
			assertEquals(14 , twts.size());
			assertEquals(8 , (twts.get(0)).getTweetId());
			assertEquals("ChloeS" , (twts.get(0)).getHandle());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsByUserIdWhenUserNotPresent() {
		try {
			List<Tweet> twts = QueryTweet.getTweetsByUserId(1);
			
			assertEquals(0 , twts.size());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void testGetAllRetweetForUser() {
		try {
			List<Tweet> twts = QueryTweet.getAllRetweetForUser(17);
			assertEquals(2 , twts.size());
			assertEquals(7, twts.get(0).getTweetId());
			assertEquals(9, twts.get(1).getTweetId());
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllRetweetForUserWhichIsNotPresent() {
		try {
			List<Tweet> twts = QueryTweet.getAllRetweetForUser(1);
			assertEquals(0 , twts.size());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllHashtagInTweet() {
		try {
			List<String> hashtags = QueryTweet.getAllHashtagInTweet(2377281);
			assertEquals(1 , hashtags.size());
			assertEquals("fanforlife" , hashtags.get(0));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllHashtagInTweetWhenTweetIdNotPresent() {
		try {
			List<String> hashtags = QueryTweet.getAllHashtagInTweet(1);
			assertEquals(0 , hashtags.size());
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Test
	public void testGetAllTweetsWithUserMention() {
		try {
			List<Tweet> mentions = QueryTweet.getAllTweetsWithUserMention(17);
			assertEquals(2 , mentions.size());
			assertEquals(1245254 , mentions.get(0).getTweetId());
			assertEquals(8168105 , mentions.get(1).getTweetId());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void testGetAllTweetsWithUserMentionWhenUserNotPresent() {
		try {
			List<Tweet> mentions = QueryTweet.getAllTweetsWithUserMention(1);
			assertEquals(0 , mentions.size());
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void testGetAllLikesForTweet() {
		try {
			long likes = QueryTweet.getAllLikesForTweet(21);
			assertEquals(2, likes);
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllLikesForTweetWhenTweetNotPresent() {
		try {
			long likes = QueryTweet.getAllLikesForTweet(1);
			assertEquals(0, likes);
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	@Test
	public void testGetMentionsAfterTimestamp() {
		
		try {
			List<Tweet> twts = QueryTweet.getMentionsAfterTimestamp(0, 2);
			assertEquals(61, twts.size());
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetMentionsAfterTimestampWhenUserNotPresent() {
		
		try {
			List<Tweet> twts = QueryTweet.getMentionsAfterTimestamp(0, 0);
			assertEquals(0, twts.size());
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckIfRetweet() {
		try {
			assertTrue(QueryTweet.checkIfRetweet(7, 17));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckIfRetweetWhenTweetNotPresent() {
		try {
			assertFalse(QueryTweet.checkIfRetweet(1, 17));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTweetsForUserProfile() {
		try {
			List<Tweet> twts = QueryTweet.getTweetsForUserProfile(32, 0, Long.parseLong("1472020664186"));
			assertEquals(20 , twts.size());
			assertEquals(860353 , twts.get(0).getTweetId());
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetLikesAfterTimestamp() {
		try {
			int likes = QueryTweet.getLikesAfterTimestamp(0, 17);
			assertTrue(likes>0);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllTweetsForUserHome() {
		try {
			List<Tweet> twts = QueryTweet.getAllTweetsForParticularUser(17, 0, Long.parseLong("1472020664186"));
			assertTrue(twts.size() > 0);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testCheckTweetExists() {
		try {
				assertFalse(QueryTweet.checkTweetExists(2));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllTweetsForParticularUser() {
		try {
			List<Tweet> twts = QueryTweet.getAllTweetsForParticularUser(17, 0, Long.parseLong("1472020664186"));
			assertTrue(twts.size() > 0);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckIsLiked() {
		try {
			assertTrue(QueryTweet.checkIsLiked(2156245, 8839862));
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTweetCount() {
		try {
			long count = QueryTweet.getTweetCount(2156245);
			assertEquals(19, count);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
