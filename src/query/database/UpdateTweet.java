package query.database;

import database.dummy.dump.SQLConnection;
import models.Tweet;
import models.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author gaurav.kum
 */

/** add a new tweet in tweets table
* update hashtags with tweet_id
* update likes count in tweet table
* update like table for tweetID
* Update retweet
* update retweetcount in tweet
* delete like for a tweet_id and user_id (unlike)
*/

public class UpdateTweet {
	
	static Logger logger = Logger.getLogger(UpdateTweet.class);
	public static boolean postTweet(Tweet tweetToAdd) throws ClassNotFoundException, SQLException {
		
		int mediaInserted = addMediaToDb(tweetToAdd);
		int tweetInserted = addTweetToDb(tweetToAdd);
		
		ArrayList<String> hashtags = (ArrayList<String>) tweetToAdd.getHashtags();
		ArrayList<Long> mentions = (ArrayList<Long>) tweetToAdd.getMentions();
		long tweet_id = tweetToAdd.getTweetId();
		
		int hashtagsInserted = addHashtagsToDb(tweet_id, hashtags);
		
		int mentionsInserted = addMentionsToDb(tweet_id, mentions);
		
		return (tweetInserted > 0 || hashtagsInserted > 0 || mentionsInserted > 0);
	}
	
	private static int addMediaToDb(Tweet tweetToAdd) {
//		StringBuilder sql =new StringBuilder("insert into media values(")
//				.append(tweetToAdd.getMediaId()).append(SqlQuerySeparators.DOUBLEQUOTE).append().append(",")
//				.append(tweet_id).append(")");
//		
//		logger.info("executing sql query in UpdateTweet addMediaToDb: " + sql.toString());
//		
//		return SQLConnection.executeUpdate(sql.toString());
		return 0;
	}

	public static int addTweetToDb(Tweet tweetToAdd) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Insert into tweets values( ")
				.append(tweetToAdd.getTweetId()).append(",")
				.append(tweetToAdd.getUserId()).append(",")
				.append(SqlQuerySeparators.DOUBLEQUOTE).append(tweetToAdd.getTweetText()).append(SqlQuerySeparators.DOUBLEQUOTE).append(",")
				.append(tweetToAdd.getTimestamp()).append(",")
				.append(tweetToAdd.getMediaId()).append(",")
				.append(tweetToAdd.getLikeCount()).append(",")
				.append(tweetToAdd.getRetweetCount()).append(",")
				.append("\"").append(tweetToAdd.getHandle()).append("\"").append( ")");
		
		logger.info("executing sql query in UpdateTweet addTweetToDb: " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
	}
	
	public static int addHashtagsToDb(long tweet_id, ArrayList<String> hashtags) throws ClassNotFoundException, SQLException {
		
		int hashtagsInserted = 0;
		Iterator<String> hashtagIterator = hashtags.iterator();
		while (hashtagIterator.hasNext()) {
			StringBuilder sql =new StringBuilder("insert into hashtags values(")
					.append(SqlQuerySeparators.DOUBLEQUOTE).append(hashtagIterator.next()).append(SqlQuerySeparators.DOUBLEQUOTE).append(",")
					.append(tweet_id).append(")");
			
			logger.info("executing sql query in UpdateTweet addHashtagsToDb: " + sql.toString());
			
			hashtagsInserted += SQLConnection.executeUpdate(sql.toString());
			
		}
		
		return hashtagsInserted;
 
	}
	
	public static int addMentionsToDb(long tweet_id, ArrayList<Long> mentions) throws ClassNotFoundException, SQLException {
		int mentionsInserted = 0;
		Iterator<Long> mentionIterator = mentions.iterator();
		while (mentionIterator.hasNext()) {
			StringBuilder sql = new StringBuilder("insert into tweet_mentions values(")
						.append(tweet_id).append(",") 
						.append(mentionIterator.next()).append(",") 
						.append(System.currentTimeMillis()).append(")");
			
			logger.info("executing sql query in UpdateTweet addMentionsToDb: " + sql.toString());
			mentionsInserted += SQLConnection.executeUpdate(sql.toString());
			
		}
		
		return mentionsInserted;
	}

	public static boolean likeTweet(long user, long tweetId) throws ClassNotFoundException, SQLException {
		long likedBy = user;
		int likeInserted = insertInLikes(likedBy, tweetId);
		
		
		int likesupdateInTweet = incrementTweetLikes(tweetId);
		
		return likeInserted > 0 && likesupdateInTweet > 0;
		
	}
	
	private static int incrementTweetLikes(long tweet_id) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Update tweets set likes = likes + 1 where tweet_id = ").append(tweet_id);
	
		logger.info("executing sql query in UpdateTweet incrementTweetLikes: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
		
	}

	private static int insertInLikes(long likedBy, long tweetId) throws ClassNotFoundException, SQLException {
		
		Tweet tweet = QueryTweet.getTweetByTweetId(tweetId);
		
		StringBuilder sql = new StringBuilder("insert into likes values (")
					.append(tweet.getTweetId()).append(",")
					.append(likedBy).append(",")
					.append(tweet.getUserId()).append(",")
					.append(System.currentTimeMillis()).append(")");
		
		logger.info("executing sql query in UpdateTweet insertInLikes: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
		
	}

	public static boolean retweetPost(long userId, long tweetId) throws ClassNotFoundException, SQLException {
		
		long retweetedBy = userId;
		int insertedInRetweet = insertInRetweet(retweetedBy, tweetId);
		
		//long tweet_id = tweet.getTweetId();
		int retweetCountupdated = updateRetweetCount(tweetId);
		
		return retweetCountupdated > 0 && insertedInRetweet > 0;
	}
	
	private static int updateRetweetCount(long tweet_id) throws ClassNotFoundException, SQLException {
		String sql = "Update tweets set retweet_count = retweet_count + 1 where tweet_id = " + tweet_id;
		
		logger.info("executing sql query in UpdateTweet updateRetweetCount: " + sql);

		
		return SQLConnection.executeUpdate(sql);
	}

	private static int insertInRetweet(long retweetedBy, long tweetId) throws ClassNotFoundException, SQLException {
		
		Tweet tweet = QueryTweet.getTweetByTweetId(tweetId);
		StringBuilder sql = new StringBuilder("insert into retweets values (") 
				.append(tweet.getTweetId()).append(",")
				.append(retweetedBy).append(",")
				.append(System.currentTimeMillis()).append(",")
				.append(tweet.getUserId()).append(")");
		
		logger.info("executing sql query in UpdateTweet insertInRetweet: " + sql.toString());

		
		return SQLConnection.executeUpdate(sql.toString());
	}

	public static boolean unlikeTweet(User user, Tweet tweet) throws ClassNotFoundException, SQLException {
		long unlikedBy = user.getUserId();
		long tweet_id = tweet.getTweetId();
		int likeDeleted = deleteFromLikes(unlikedBy, tweet_id);
		
		int likesupdateInTweet = decrementTweetLikes(tweet_id);
		
		return likeDeleted > 0 || likesupdateInTweet > 0;
	}
	
	private static int decrementTweetLikes(long tweet_id) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("Update tweets set likes = likes - 1 where tweet_id = ") 
				.append(tweet_id);
	
		logger.info("executing sql query in UpdateTweet decrementTweetLikes: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
		
	}

	private static int deleteFromLikes(long unlikedBy, long tweet_id) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("delete from likes where tweet_id = ").append(tweet_id).append(" and user_id = ")
				.append(unlikedBy);

		logger.info("executing sql query in UpdateTweet deleteFromLikes: " + sql.toString());

		
		return SQLConnection.executeUpdate(sql.toString());
		
	}

	public static boolean deleteTweet(long tweetId) throws ClassNotFoundException, SQLException {
		
		Tweet tweet = QueryTweet.getTweetByTweetId(tweetId);
		
		ArrayList<String> hashtags = (ArrayList<String>) tweet.getHashtags();
		ArrayList<Long> mentions = (ArrayList<Long>) tweet.getMentions();
		
		int retweetsDeleted = deleteRetweets(tweetId);
		
		int mentionsDeleted = deleteMentionsFromDb(tweetId, mentions);
		
		int hashtagsDeleted = deleteHashtagsFromDb(tweetId, hashtags);
		
		int tweetDeleted = deleteTweetFromDb(tweetId);
		
		return (tweetDeleted > 0 || hashtagsDeleted > 0 || mentionsDeleted > 0);

	}

	private static int deleteRetweets(long tweetId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from retweets where tweet_id = ")
				.append(tweetId);
		
		logger.info("executing sql query in UpdateTweet deleteRetweets: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
	
	}

	private static int deleteTweetFromDb(long tweetId) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("delete from tweets where tweet_id = ")
				.append(tweetId);
		
		logger.info("executing sql query in UpdateTweet deleteTweetFromDb: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
	}

	private static int deleteMentionsFromDb(long tweetId, ArrayList<Long> mentions) throws ClassNotFoundException, SQLException {
		int mentionsDeleted = 0;
		Iterator<Long> mentionIterator = mentions.iterator();
		while (mentionIterator.hasNext()) {
			StringBuilder sql = new StringBuilder("delete from mentions where tweet_id = ")
					.append(tweetId);
			

			logger.info("executing sql query in UpdateTweet deleteMentionsFromDb: " + sql.toString());

			mentionsDeleted += SQLConnection.executeUpdate(sql.toString());
			
		}
		
		return mentionsDeleted;
		
	}

	private static int deleteHashtagsFromDb(long tweetId, ArrayList<String> hashtags) throws ClassNotFoundException, SQLException {

		int hashtagsDeleted = 0;
		Iterator<String> hashtagIterator = hashtags.iterator();
		while (hashtagIterator.hasNext()) {
			StringBuilder sql = new StringBuilder("delete from hashtags where tweet_id = ")
						.append(tweetId);
			

			logger.info("executing sql query in UpdateTweet deleteMentionsFromDb: " + sql.toString());

			hashtagsDeleted += SQLConnection.executeUpdate(sql.toString());
			
		}
		return hashtagsDeleted;
	}
}
