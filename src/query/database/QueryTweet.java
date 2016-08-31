package query.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import database.dummy.dump.SQLConnection;

import models.*;
import response.util.CheckValidity;

/**
 * @author mayank.ra
 */
public class QueryTweet {
	
	static Logger logger = Logger.getLogger(QueryTweet.class);
	public static List<Tweet> getTweetsWithHashtags(String hashtag, long startTime, long latestTime)
			throws ClassNotFoundException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from tweets where tweet_id in ");
		stringBuilder.append("(select tweet_id from hashtags where hash_name=");
		stringBuilder.append(SqlQuerySeparators.DOUBLEQUOTE + hashtag + SqlQuerySeparators.DOUBLEQUOTE);
		stringBuilder.append(")");
		stringBuilder.append("and created_at > ");
		stringBuilder.append(startTime);
		stringBuilder.append(" and created_at < ");
		stringBuilder.append(latestTime);
		stringBuilder.append(" order by created_at desc limit 10");
		
		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
		List<Tweet> tweets = new ArrayList<Tweet>();
		while (rs.next()) {
			tweets.add(prepareTweetObject(rs));
		}
		return tweets;
	}

	public static Tweet getTweetByTweetId(long tweetId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from tweets where tweet_id=");
		stringBuilder.append(tweetId);

		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
//		ResultSet rs = SQLConnection.db.queryDb(stringBuilder.toString());

		if (rs.next()) {
			return prepareTweetObject(rs);
		}
		return null;
	}
	
	public static boolean checkRetweetForUser(Long tweetId, Long userId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select * from retweets where user_id =")
				.append(userId)
				.append(" and tweet_id = ")
				.append(tweetId);
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public static List<Tweet> getTweetsByUserId(long user_id) 
			throws ClassNotFoundException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		List<Tweet> allTweetsForUser = new ArrayList<Tweet>();
		
		stringBuilder.append("select * from tweets where user_id=");
		stringBuilder.append(user_id);

		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
		
//		ResultSet rs = SQLConnection.db.queryDb(stringBuilder.toString());
		
		while (rs.next()) {
			allTweetsForUser.add(prepareTweetObject(rs));
		}
		return allTweetsForUser;
	}
	
	public static List<Tweet> getAllRetweetForUser(long userId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		List<Tweet> allReTweets = new ArrayList<>();
		
		stringBuilder.append("select * from tweets where tweet_id in ");
		stringBuilder.append("(select tweet_id from retweets where user_id=");
		stringBuilder.append(userId);
		stringBuilder.append(")");
		
		logger.info("executing sql query: " + stringBuilder.toString());
//		ResultSet rs = SQLConnection.db.queryDb(stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
		while (rs.next()) {
			allReTweets.add(prepareTweetObject(rs));
		}
		return allReTweets;
	}

	public static List<String> getAllHashtagInTweet(long tweetId) 
			throws SQLException, ClassNotFoundException {
		List<String> allhashtags = new ArrayList<>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select hash_name from hashtags where tweet_id=");
		stringBuilder.append(tweetId);
		
		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
//		ResultSet rs = SQLConnection.db.queryDb(stringBuilder.toString());
		
		while(rs.next()) {
			allhashtags.add(rs.getString("hash_name"));
		}
		return allhashtags;
	}
	
	public static List<Tweet> getAllTweetsWithUserMention(long userId) 
			throws ClassNotFoundException, SQLException {
		List<Tweet> allTweetsWithMention = new ArrayList<>();		
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("select * from tweets where tweet_id in (");
		stringBuilder.append("select tweet_id from tweet_mentions where user_id=");
		stringBuilder.append(userId);
		stringBuilder.append(")");
		
		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
//		ResultSet rs = SQLConnection.db.queryDb(stringBuilder.toString());
		
		while(rs.next()) {
			allTweetsWithMention.add(prepareTweetObject(rs));
		}
		return allTweetsWithMention;
	}

	public static int getAllLikesForTweet(long tweetId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("select count(*) from likes where tweet_id=");
		stringBuilder.append(tweetId);
		
		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
//		ResultSet rs = SQLConnection.db.queryDb(stringBuilder.toString());
		
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public static boolean checkUserLikeOnTweet(long userId, long tweetId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("select * from likes where tweet_id = ");
		stringBuilder.append(tweetId);
		stringBuilder.append(" and ");
		stringBuilder.append("user_id = ");
		stringBuilder.append(userId);
		
		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
//		ResultSet rs = SQLConnection.db.queryDb(stringBuilder.toString());
		
		if (rs.next()) {
			return true;
		}
		return false;
	}

	public static List<Tweet> getMentionsAfterTimestamp(long timestamp, long userId)
			throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder();
		List<Tweet> allTweetsWithMention = new ArrayList<>();
		
		query.append("select * from tweets where tweet_id in (");
		query.append("select tweet_id from tweet_mentions where user_id = ");
		query.append(userId);
		query.append(" and timestamp > ");
		query.append(timestamp);
		query.append(")");
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		
		while(rs.next()) {
			allTweetsWithMention.add(prepareTweetObject(rs));
		}
		return allTweetsWithMention;
	}
	
	public static boolean checkIfRetweet(long tweetId, long userId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from retweets where tweet_id=");
		stringBuilder.append(tweetId);
		stringBuilder.append(" and user_id=");
		stringBuilder.append(userId);
		
		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
		if (rs.next()) {
			return true;
		}
		return false;
	}

	public static List<Tweet> getTweetsForUserProfile(long userId, long startTime, long latestTime) 
			throws ClassNotFoundException, SQLException {
		List<Tweet> tweetsForUserProfile = new ArrayList<>();
		
		String query = generateQueryForUserProfile(userId, startTime, latestTime);
		logger.info("executing sql query: " + query);

		ResultSet rs = SQLConnection.executeQuery(query);
		while (rs.next()) {
			Tweet tweet = prepareTweetObject(rs);
			if (rs.getLong("user_id") != rs.getLong("retweetUser") && QueryTweet.checkIfRetweet(tweet.getTweetId(), rs.getLong("retweetUser"))) {
				tweet.markRetweet();
				tweet.setRetweetUserId(userId);
				tweet.setRetweetHandle(QueryUser.getUserHandle(rs.getLong("retweetUser")));
			}
			tweetsForUserProfile.add(tweet);
		}
		return tweetsForUserProfile;
		
	}
	
	private static String generateQueryForUserProfile(long userId, long startTime, long latestTime) {
		StringBuilder query = new StringBuilder("select t.likes,t.media_id, t.retweet_count, e.timestamp as created_at, t.user_id, t.tweet_text, t.tweet_id, t.handle, e.retweetUser")
				.append(" from tweets as t inner join  (select * from (select tweet_id, timestamp, timestamp as retweetUser from tweet_mentions where user_id =") 
				.append(userId)
				.append(" and timestamp >") 
				.append(startTime)
				.append(" and timestamp <") 
				.append(latestTime)
				.append(" order by timestamp desc limit 20) as a union select * from (select tweet_id, created_at as timestamp, user_id as retweetUser from retweets where user_id =") 
				.append(userId)
				.append(" and created_at >") 
				.append(startTime)
				.append(" and created_at <") 
				.append(latestTime)
				.append(" order by created_at desc limit 20) as b union select * from (select tweet_id, created_at as timestamp, created_at as retweetUser from tweets where user_id =") 
				.append(userId)
				.append(" and created_at >") 
				.append(startTime)
				.append(" and created_at <") 
				.append(latestTime)
				.append(" order by created_at limit 20) as c) e on e.tweet_id=t.tweet_id order by e.timestamp desc");
		return query.toString();
	}
	
	public static int getLikesAfterTimestamp(long timestamp, long userId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(*) from likes where author_id = ");
		stringBuilder.append(userId);
		stringBuilder.append(" and timestamp >");
		stringBuilder.append(timestamp);
		
		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
		
		if(rs.next()) {
			return (int) rs.getLong(1);
		}
		return 0;
	}
	
	public static List<Tweet> getAllTweetsForUserHome(long userId, long startTime, long latestTime)
			throws ClassNotFoundException, SQLException {		
		List<Tweet> tweetsForHomepage = new ArrayList<>();
		String query = generateQueryForUserHome(userId, startTime, latestTime);

		logger.info("executing sql query: " + query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query);
		ResultSet rs = SQLConnection.executeQuery(query.toString());
		logger.info("executing sql query2: " + query.toString());
		while (rs.next()){
			Tweet currentTweet = prepareTweetObject(rs);
			if(rs.getLong("user_id") != rs.getLong("retweetUser") && QueryTweet.checkIfRetweet(currentTweet.getTweetId(), rs.getLong("retweetUser"))) {
				currentTweet.markRetweet();
				currentTweet.setRetweetUserId(rs.getLong("retweetUser"));
				currentTweet.setRetweetHandle(QueryUser.getUserHandle(rs.getLong("retweetUser")));
			}
			tweetsForHomepage.add(currentTweet);
		}
		return tweetsForHomepage;
	}

	private static Tweet prepareTweetObject(ResultSet rs) throws SQLException, ClassNotFoundException {
		Tweet tweet = new Tweet();
		tweet.setLikeCount(rs.getInt("likes"));
		
		tweet.setMediaId(rs.getLong("media_id"));
		tweet.setRetweetCount(rs.getInt("retweet_count"));
		tweet.setTimestamp(rs.getLong("created_at"));
		tweet.setTweetText(rs.getString("tweet_text"));
		tweet.setUserId(rs.getLong("user_id"));
		tweet.setTweetId(rs.getLong("tweet_id"));
		tweet.setHandle(rs.getString("handle"));
		return tweet;
		
	}
	
	private static String generateQueryForUserHome(long userId, long startTime, long latestTime) {
		StringBuilder query = new StringBuilder("select t.tweet_id, t.user_id, t.tweet_text, final.created_at, t.media_id, t.likes, t.retweet_count, final.retweetUser, t.handle, final.user_id ")
				.append("as post_user from tweets t inner join ( select * from ( select tweet_id, user_id, created_at, user_id as retweetUser from retweets as r inner join connections c on r.user_id = c.following and c.follower = ")
				.append(userId)
				.append(" and  ( ( (c.end_time is null or c.end_time > ")
				.append(latestTime)
				.append(" and r.created_at >= ")
				.append(startTime)
				.append(" and r.created_at < ")
				.append(latestTime)
				.append(" ) or ( c.end_time >= ")
				.append(startTime)
				.append(" and c.end_time < ")
				.append(latestTime)
				.append(") and r.created_at >= c.end_time and r.created_at < ")
				.append(startTime)
				.append(" ) )  order by created_at desc limit 40) as a union all ")
				.append("select * from ( select tweet_id, user_id, created_at, created_at as retweetUser  from tweets as t inner join connections c on t.user_id = c.following and ( ( c.follower = ")
				.append(userId)
				.append(" and ( ( (c.end_time is null or c.end_time > ")
				.append(latestTime)
				.append(") and t.created_at >= ")
				.append(startTime)
				.append(" and t.created_at < ")
				.append(latestTime)
				.append(") or ( c.end_time >= ")
				.append(startTime)
				.append(" and c.end_time < ")
				.append(latestTime)
				.append(" and t.created_at >= c.end_time and t.created_at < ")
				.append(startTime)
				.append(") ) ) )  order by created_at desc limit 40) as b union all ")
				.append(" select * from ( select tweet_id, user_id, created_at , created_at as retweetUser from tweets as t where ( t.tweet_id in ( select tweet_id from tweet_mentions where user_id = ")
				.append(userId)
				.append(" and timestamp >= ")
				.append(startTime)
				.append(" and timestamp < ")
				.append(latestTime)
				.append(" ) ) or ( user_id = ")
				.append(userId)
				.append(" and created_at >= ")
				.append(startTime)
				.append(" and created_at < ")
				.append(latestTime)
				.append(" ) order by created_at desc limit 40) as c ) as final on t.tweet_id = final.tweet_id order by final.created_at desc limit 40");
	
			return query.toString();
	}

	public static boolean checkTweetExists(long tweetId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select * from tweets where tweet_id=");
		query.append(tweetId);
		
		logger.info("executing sql query: " + query.toString());
		if (SQLConnection.executeQuery(query.toString()).next()) {
			return true;
		}
		return false;
	}

	public static List<Tweet> getAllTweetsForParticularUser(long userId, long startTime, long latestTime) throws ClassNotFoundException, SQLException {
		List<Tweet> tweetsForParticularUser = new ArrayList<>();
	
		StringBuilder query = new StringBuilder("select A.tweet_id, A.user_id, A.tweet_text, B.created_at, A.media_id, A.likes, A.retweet_count, A.handle, B.retweetUser from tweets as A inner join ")  
					.append("(select * from (select tweet_id , created_at , user_id as retweetUser from retweets where user_id = ").append(userId).append(" ) as C union all  select * from ")
					.append("(select tweet_id, created_at , created_at as retweetUser from tweets where user_id = ").append(userId).append(") as D) as B on A.tweet_id = B.tweet_id order by B.created_at desc");
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
		
		while (rs.next()) {
			Tweet tweet = prepareTweetObject(rs);
			if(QueryTweet.checkIfRetweet(tweet.getTweetId(), rs.getLong("retweetUser"))) {
				tweet.setRetweetUserId(rs.getLong("retweetUser"));
				tweet.markRetweet();
				tweet.setRetweetHandle(QueryUser.getUserHandle(rs.getLong("retweetUser")));
			}
			tweetsForParticularUser.add(tweet);
		}
		//System.out.println(tweetsForParticularUser.size());
		logger.info("returning " + tweetsForParticularUser.size() + " objects");
		return tweetsForParticularUser;
	}
	
	public static boolean checkIsLiked(long userId, long tweetId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select * from likes where user_id = ").append(userId)
				.append(" and tweet_id = ").append(tweetId);
		
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if(rs.next()) {
			return true;
		}
		return false;
	}

	public static long generateNewTweetId() throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select max(tweet_id) from tweets");
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if(rs.next()) {
			return rs.getLong(1);
		}
		return 0;
	}

	public static long getTweetCount(long userId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select count(*) as cnt from tweets where user_id = ")
				.append(userId);
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			return rs.getLong("cnt");
		}
		return 0;
	}

	public static List<String> getHashtags() throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select hash_name from hashtags group by hash_name order by count(hash_name) desc limit 5");
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if(!rs.next()){
			return null;
		}
		List<String> hashtags = new ArrayList<>();
		while(rs.next()) {
			hashtags.add(rs.getString("hash_name"));
		}
		return hashtags;
	}

	
	public static boolean checkIsRetweetedByUser(Long userId, long tweetId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select * from retweets where user_id = ").append(userId)
				.append(" and tweet_id = ").append(tweetId);
		
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if(rs.next()) {
			return true;
		}
		return false;
	}

	public static long getRetweetCount(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select count(tweet_id) as countRetweets from retweets where user_id = ").append(userId).append(" group by user_id");
		ResultSet rs = SQLConnection.executeQuery(query.toString());
		if(rs.next()) {
			return rs.getLong("countRetweets");
		}
		return 0;
	}
	
}
