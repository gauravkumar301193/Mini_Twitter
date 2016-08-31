package query.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import database.dummy.dump.SQLConnection;
import models.User;
import query.database.SqlQuerySeparators;

/**
 * @author gaurav.kum
 */

public class UpdateUser {
	
	static Logger logger = Logger.getLogger(UpdateUser.class);
	
	public static boolean registerUser(User user) throws ClassNotFoundException, SQLException {
		
		int rowsInserted = insertIntoAuthentication(user);
		
		
		int rowsInserted1 = insertIntoUserDetails(user);
		
		return rowsInserted > 0  && rowsInserted1 > 0 ;
		
		
	}
	
	private static int insertIntoAuthentication(User user) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Insert into authentication(email_id, name, handle, user_id, password) values( ")
						.append(SqlQuerySeparators.DOUBLEQUOTE).append(user.getEmail()).append(SqlQuerySeparators.DOUBLEQUOTE).append(SqlQuerySeparators.COMMA)
						.append(SqlQuerySeparators.DOUBLEQUOTE).append(user.getUserName()).append(SqlQuerySeparators.DOUBLEQUOTE).append(SqlQuerySeparators.COMMA)
						.append(SqlQuerySeparators.DOUBLEQUOTE).append(user.getHandle()).append(SqlQuerySeparators.DOUBLEQUOTE).append(SqlQuerySeparators.COMMA)
						.append(user.getUserId()).append(SqlQuerySeparators.COMMA)
						.append(SqlQuerySeparators.DOUBLEQUOTE).append(user.getPassword()).append(SqlQuerySeparators.DOUBLEQUOTE).append(")");
						

		logger.info("executing sql query in UpdateUser insertIntoAuthentication : " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	
	}
	
	private static int insertIntoUserDetails(User user) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Insert into user_details(user_id, following_count , follower_count,logout, tweets_count , handle) values(")
				.append(user.getUserId()).append(SqlQuerySeparators.COMMA)
				.append(user.getFollowing()).append(SqlQuerySeparators.COMMA)
				.append(user.getFollower()).append(",")
				.append(0).append(",")
				.append(user.getTweetCount()).append(",")
				.append(SqlQuerySeparators.DOUBLEQUOTE).append(user.getHandle()).append(SqlQuerySeparators.DOUBLEQUOTE)
				.append(")");
		

		logger.info("executing sql query in UpdateUser insertIntoUserDetails : " + sql.toString());
		
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}
	
	public static boolean followUser(long userId, long userToFollow) throws SQLException, ClassNotFoundException {
		
		int rowsInserted = addConnections(userId, userToFollow);
		
		int rowsUpdated = incrementFollowingCount(userId);
		int rowsUpdated1 = incrementFollowerCount(userToFollow);
		
		return rowsInserted > 0  || rowsUpdated > 0 ;
		
	}
	
	private static int incrementFollowerCount(long userToFollow) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Update user_details set follower_count = follower_count + 1 ") 
				.append(" where user_id = ").append(userToFollow);
	

		logger.info("executing sql query in UpdateUser incrementFollowerCount : " + sql.toString());
	
		return SQLConnection.executeUpdate(sql.toString());
	}

	private static int addConnections(long user, long userToFollow) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("insert into connections(follower, following, start_time, end_time) values (")
				.append(user).append(SqlQuerySeparators.COMMA)
				.append(userToFollow).append(SqlQuerySeparators.COMMA)
				 .append(System.currentTimeMillis()).append(SqlQuerySeparators.COMMA) 
				 .append("null").append(")");
		
		logger.info("executing sql query in UpdateUser addConnections : " + sql.toString());
	
		return SQLConnection.executeUpdate(sql.toString());
	}
	
	private static int incrementFollowingCount(long user) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Update user_details set following_count = following_count + 1 ") 
				.append(" where user_id = ").append(user);
	

		logger.info("executing sql query in UpdateUser incrementFollowingCount : " + sql.toString());
	
		return SQLConnection.executeUpdate(sql.toString());
	}
	
	public static boolean unfollowUser(long user, long userToUnfollow) throws SQLException, ClassNotFoundException {
		
		int rowsUpdated = updateConnectionEndTime(user, userToUnfollow);
		
		int rowsUpdated1 = decrementFollowingCount(user);
		
		int rowsUpdated2 = decrementFollowerCount(userToUnfollow);
		
		return rowsUpdated > 0  && rowsUpdated1 > 0 ;
	}
	
	private static int decrementFollowerCount(long userToUnfollow) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Update user_details set follower_count = follower_count - 1") 
				.append(" where user_id = ") 
				.append(userToUnfollow);

		logger.info("executing sql query in UpdateUser decrementFollowerCount : " + sql.toString());
	
		return SQLConnection.executeUpdate(sql.toString());
	}

	private static int updateConnectionEndTime(long user, long userToUnfollow) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Update connections set end_time = ") 
				.append(System.currentTimeMillis()).append(" where following = ") 
				.append(userToUnfollow)
				.append(" and follower = ")
				.append(user);
	

		logger.info("executing sql query in UpdateUser updateConnectionEndTime : " + sql.toString());
	
		return SQLConnection.executeUpdate(sql.toString());
	}
	
	private static int decrementFollowingCount(long user) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("Update user_details set following_count = following_count - 1") 
				.append(" where user_id = ") 
				.append(user);
	
		return SQLConnection.executeUpdate(sql.toString());
	}
	
	public static boolean deleteUser(long userId) throws ClassNotFoundException, SQLException {
		
		int decrementLikes = decrementLikes(userId);
		
		int likesDeleted = deleteLikes(userId);

		int connectionsDeleted = deleteConnections(userId);
		
		int retweetsDeleted = deleteRetweets(userId);
		
		
		int mentionsDeleted = deleteTweetMentions(userId);

		
		
		int hashtagsDeleted = deleteHashtags(userId);
		
		int tweetMediaDeleted = deleteTweetMedia(userId);
		
		int tweetsDeleted = deleteTweets(userId);
		
		int profileMediaDeleted = deleteUserMedia(userId);
		
		int userDeleted = deleteUserDetails(userId);
		
		int authenticationDeleted = deleteFromAuthentication(userId);
		
		return userDeleted > 0 && authenticationDeleted > 0;
		
		
	}
	
	private static int decrementLikes(long userId) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("Update tweets set likes = likes - 1 where tweet_id in (")
					.append("select tweet_id from likes where user_id = ")
					.append(userId).append(")");

		logger.info("executing sql query in UpdateUser decrementLikes : " + sql.toString());
	
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());

	}

	private static int deleteLikes(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from likes where user_id = ").append(userId) ;
		logger.info("executing sql query in UpdateUser deleteLikes : " + sql.toString());
		
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}
	
	private static int deleteConnections(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from connections where follower = ") 
				.append(userId).append(" or following = ").append(userId);
		logger.info("executing sql query in UpdateUser deleteConnections : " + sql.toString());
		
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());

	}

	private static int deleteTweetMentions(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from tweet_mentions where user_id = ").append(userId);
		logger.info("executing sql query in UpdateUser deleteTweetMentions : " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());

	}
	
	private static int deleteRetweets(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from retweets where author_id = ").append(userId).append(" or user_id =").append(userId);
		logger.info("executing sql query in UpdateUser deleteRetweets : " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}
	
	private static int deleteHashtags(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from hashtags where tweet_id in ( select tweet_id from tweets where user_id = ")
				.append(userId).append(")");
		logger.info("executing sql query in UpdateUser deleteHashtags : " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}
	
	private static int deleteTweetMedia(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from media where media_id in ( select media_id from tweets where user_id = ").append(userId).append(")");
		
		logger.info("executing sql query in UpdateUser deleteTweetMedia : " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}
	
	private static int deleteTweets(long userId) throws ClassNotFoundException, SQLException {
		
		int mentionsDeleted = deleteFromMentions(userId); 
		
		int hashtagsDeleted = deleteFromHashtags(userId); 
		
		int retweetsDeleted = deleteRetweets(userId); 
		
		StringBuilder sql = new StringBuilder("delete from tweets where user_id = ").append(userId);
		
		logger.info("executing sql query in UpdateUser deleteTweets : " + sql.toString());
		
		return  SQLConnection.executeUpdate(sql.toString()) ;
//		return SQLConnection.db.updateDb(sql.toString());
		
	}
	
	private static int deleteFromHashtags(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from hashtags where tweet_id in ( select tweet_id from tweets where user_id = ")
				.append(userId).append(")");
		
		logger.info("executing sql query in UpdateUser deleteTweetshashtags : " + sql.toString());
		
		return  SQLConnection.executeUpdate(sql.toString()) ;
//		return SQLConnection.db.updateDb(sql.toString());
		
	}

	private static int deleteFromMentions(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from tweet_mentions where tweet_id in ( select tweet_id from tweets where user_id = ")
				.append(userId).append(")");
		
		logger.info("executing sql query in UpdateUser deleteTweetsMentions : " + sql.toString());
		
		return  SQLConnection.executeUpdate(sql.toString()) ;
//		return SQLConnection.db.updateDb(sql.toString());
		}

	private static int deleteUserMedia(long userId) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("delete from media where media_id in ( select media_id from user_details where user_id = ").append(userId).append(")");
		
		logger.info("executing sql query in UpdateUser deleteUserMedia : " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}
	
	private static int deleteUserDetails(long userId) throws ClassNotFoundException, SQLException {
		
		StringBuilder sql = new StringBuilder("delete from user_details where user_id = ").append(userId) ;
		
		logger.info("executing sql query in UpdateUser deleteUserDetails : " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}
	
	private static int deleteFromAuthentication(long userId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("delete from Authentication where user_id = ").append(userId) ;
		
		logger.info("executing sql query in UpdateUser deleteFromAuthentication : " + sql.toString());
		
		return SQLConnection.executeUpdate(sql.toString());
//		return SQLConnection.db.updateDb(sql.toString());
	}

	public static int insertIntoUserDb(Long userId, Long mediaId) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder("update user_details set media_id = ").append(mediaId).append(" where user_id = ").append(userId) ;
		
		return SQLConnection.executeUpdate(sql.toString());
		
	}
	
	
}
