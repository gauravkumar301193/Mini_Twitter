package query.database;

import database.dummy.dump.SQLConnection;
import database.dummy.dump.TweetParser;
import models.Tweet;
import models.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		
		int tweetInserted = addTweetToDb(tweetToAdd);
		
		TweetParser tp = new TweetParser(tweetToAdd.getTweetText());
		tp.parseTweet();
		
		String[] listOfWords = tp.listToArrayOfWords(); 
		
		List<Integer> hashtags = tp.getHashtags();
		List<Integer> mentions = tp.getMentions();
		
		long tweet_id = tweetToAdd.getTweetId();
		
		int hashtagsInserted = addHashtagsToDb(tweet_id, hashtags, listOfWords);
		
		int mentionsInserted = addMentionsToDb(tweet_id, mentions, listOfWords, tweetToAdd.getTimestamp());
		
		return (tweetInserted > 0 || hashtagsInserted > 0 || mentionsInserted > 0);
	}
	
//	private static int addMediaToDb(Tweet tweetToAdd) {
//		StringBuilder sql =new StringBuilder("insert into media values(")
//				.append(tweetToAdd.getMediaId()).append(SqlQuerySeparators.DOUBLEQUOTE).append().append(",")
//				.append(tweet_id).append(")");
//		
//		logger.info("executing sql query in UpdateTweet addMediaToDb: " + sql.toString());
//		
//		return SQLConnection.executeUpdate(sql.toString());
//		return 0;
//	}

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
//		return SQLConnection.db.updateDb(sql.toString());
	}
	
	public static int addHashtagsToDb(long tweet_id, List<Integer> hashtags, String[] listOfWords) throws ClassNotFoundException, SQLException {
		
		int hashtagsInserted = 0;
		Iterator<Integer> hashtagIterator = hashtags.iterator();
		while (hashtagIterator.hasNext()) {
			String hashtag = listOfWords[hashtagIterator.next()];
			hashtag = hashtag.substring(1, hashtag.length());
			StringBuilder sql =new StringBuilder("insert into hashtags values(")
					.append(SqlQuerySeparators.DOUBLEQUOTE).append(hashtag).append(SqlQuerySeparators.DOUBLEQUOTE).append(",")
					.append(tweet_id).append(")");
			
			logger.info("executing sql query in UpdateTweet addHashtagsToDb: " + sql.toString());
			
			hashtagsInserted += SQLConnection.executeUpdate(sql.toString());
//			hashtagsInserted += SQLConnection.db.updateDb(sql.toString());
			
		}
		
		return hashtagsInserted;
 
	}
	
	public static int addMentionsToDb(long tweet_id, List<Integer> mentions , String[] allWordsInTweet, long timestamp) throws ClassNotFoundException, SQLException {
		int mentionsInserted = 0;
		
		Iterator<Integer> mentionIterator = mentions.iterator();
		long userId = 0;
		String mention = "";
		while (mentionIterator.hasNext()) {
			mention = allWordsInTweet[mentionIterator.next()];
			mention = mention.substring(1, mention.length());
			userId = QueryUser.getUserID(mention);
			
			StringBuilder sql = new StringBuilder("insert into tweet_mentions values(")
						.append(tweet_id).append(",") 
						.append(userId).append(",") 
						.append(timestamp).append(")");
			
			logger.info("executing sql query in UpdateTweet addMentionsToDb: " + sql.toString());
			mentionsInserted += SQLConnection.executeUpdate(sql.toString());
//			mentionsInserted += SQLConnection.db.updateDb(sql.toString());
			
		}
		
		return mentionsInserted;
	}

	public static boolean likeTweet(long user, long tweetId ) throws ClassNotFoundException, SQLException {
		long likedBy = user;
		int likeInserted = insertInLikes(likedBy, tweetId);
		
		
		int likesupdateInTweet = incrementTweetLikes(tweetId );
		
		return likeInserted > 0 && likesupdateInTweet > 0;
		
	}
	
	private static int incrementTweetLikes(long tweet_id) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Update tweets set likes = likes + 1 where tweet_id = ").append(tweet_id);
	
		logger.info("executing sql query in UpdateTweet incrementTweetLikes: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
		
	}

	private static int insertInLikes(long likedBy, long tweetId) throws ClassNotFoundException, SQLException {
		
		Tweet tweet = QueryTweet.getTweetByTweetId(tweetId);
		
		StringBuilder sql = new StringBuilder("insert into likes values (")
					.append(tweetId).append(",")
					.append(likedBy).append(",")
					.append(tweet.getUserId()).append(",")
					.append(System.currentTimeMillis()).append(")");
		
		logger.info("executing sql query in UpdateTweet insertInLikes: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
		
	}

	public static boolean retweetPost(String loggedInUserHandle, long tweetId, long authorId, long loggedInUser) throws ClassNotFoundException, SQLException {
		
		int insertedInRetweet = insertInRetweet(loggedInUserHandle, tweetId, authorId, loggedInUser);
		int retweetCountupdated = updateRetweetCount(tweetId);
		
		return retweetCountupdated > 0 && insertedInRetweet > 0;
	}
	
	private static int updateRetweetCount(long tweet_id) throws ClassNotFoundException, SQLException {
		String sql = "Update tweets set retweet_count = retweet_count + 1 where tweet_id = " + tweet_id;
		
		logger.info("executing sql query in UpdateTweet updateRetweetCount: " + sql);

		
		return SQLConnection.executeUpdate(sql);
//		return SQLConnection.db.updateDb(sql);
	}

	private static int insertInRetweet(String loggedInUserHandle, long tweetId ,long authorId, long loggedInUser) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("insert into retweets values (") 
				.append(tweetId).append(",")
				.append(loggedInUser).append(",")
				.append(System.currentTimeMillis()).append(",")
				.append(authorId).append(",")
				.append("\"").append(loggedInUserHandle).append("\"").append(" )");
		
		logger.info("executing sql query in UpdateTweet insertInRetweet: " + sql.toString());
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}

	public static boolean unlikeTweet(long userId, long tweetId) throws ClassNotFoundException, SQLException {
		
		int likeDeleted = deleteFromLikes(userId, tweetId);
		
		int likesupdateInTweet = decrementTweetLikes(tweetId);
		
		return likeDeleted > 0 || likesupdateInTweet > 0;
	}
	
	private static int decrementTweetLikes(long tweetId) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("Update tweets set likes = likes - 1 where tweet_id = ") 
				.append(tweetId);
	
		logger.info("executing sql query in UpdateTweet decrementTweetLikes: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
		
	}

	private static int deleteFromLikes(long unlikedBy, long tweet_id) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("delete from likes where tweet_id = ").append(tweet_id).append(" and user_id = ")
				.append(unlikedBy);

		logger.info("executing sql query in UpdateTweet deleteFromLikes: " + sql.toString());

		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
		
	}

	public static boolean deleteTweet(long tweetId) throws ClassNotFoundException, SQLException {
		
		Tweet tweet = QueryTweet.getTweetByTweetId(tweetId);
		
		int mentionsDeleted = deleteMentionsFromDb(tweetId);
		
		int retweetsDeleted = deleteRetweets(tweetId);
		
		int hashtagsDeleted = deleteHashtagsFromDb(tweetId);
		
		int likesDelted = deleteLikeForTweet(tweetId);
		
		int tweetDeleted = deleteTweetFromDb(tweetId);
		
		return (tweetDeleted > 0 || hashtagsDeleted > 0 || mentionsDeleted > 0);

	}
	
	private static int deleteLikeForTweet(long tweetId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from likes where tweet_id = ")
				.append(tweetId);
		
		logger.info("executing sql query in UpdateTweet deleteRetweets: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
	}

	private static int deleteRetweets(long tweetId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from retweets where tweet_id = ")
				.append(tweetId);
		
		logger.info("executing sql query in UpdateTweet deleteRetweets: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	
	}

	private static int deleteTweetFromDb(long tweetId) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("delete from tweets where tweet_id = ")
				.append(tweetId);
		
		logger.info("executing sql query in UpdateTweet deleteTweetFromDb: " + sql.toString());

		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}

	private static int deleteMentionsFromDb(long tweetId) throws ClassNotFoundException, SQLException {
		int mentionsDeleted = 0;
		StringBuilder sql = new StringBuilder("delete from tweet_mentions where tweet_id = ")
					.append(tweetId);
			logger.info("executing sql query in UpdateTweet deleteMentionsFromDb: " + sql.toString());

			return SQLConnection.executeUpdate(sql.toString());
//			return SQLConnection.db.updateDb(sql.toString());
			
	}

	private static int deleteHashtagsFromDb(long tweetId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from hashtags where tweet_id = ")
						.append(tweetId);
			logger.info("executing sql query in UpdateTweet deleteHashtagsFromDb: " + sql.toString());

			return SQLConnection.executeUpdate(sql.toString());
//			return SQLConnection.db.updateDb(sql.toString());
	}

	public static int insertIntoTweetDb(Long tweetId, Long mediaId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("update tweets set media_id = ").append(mediaId).append(" where tweet_id = ").append(tweetId) ;
		
		return SQLConnection.executeUpdate(sql.toString());
		
	}

}
