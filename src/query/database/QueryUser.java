package query.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import models.User;
import database.dummy.dump.SQLConnection;
import models.RetweetModel;
/**
 * @author mayank.ra
 */

public class QueryUser {
	static Logger logger = Logger.getLogger(QueryUser.class);
	
	public static boolean checkUserExists(long userId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select * from authentication where user_id=");
		query.append(userId);
		
		logger.info("executing sql query: " + query.toString());
		if (SQLConnection.executeQuery(query.toString()).next()) {
			return true;
		}
		return false;
	}
	
	public static Long checkUserCredentials(String emailId, String password)
			throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select * from authentication where email_id =")
				.append(SqlQuerySeparators.DOUBLEQUOTE)
				.append(emailId)
				.append(SqlQuerySeparators.DOUBLEQUOTE)
				.append(" and password = ")
				.append(SqlQuerySeparators.DOUBLEQUOTE)
				.append(password)
				.append(SqlQuerySeparators.DOUBLEQUOTE);
		
		logger.info("Executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			return rs.getLong("user_id");
		} 
		return null;
	}
	
	public static long getLastLogout(long userId) 
			throws SQLException, ClassNotFoundException {
		String query = "select logout from user_details where user_id=" + userId;

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);
		if (rs.next()) {
			return rs.getLong("logout");
		}
		return 0;
	}
	
	public static String getUserEmail(long userId)
			throws SQLException, ClassNotFoundException {
		String query = "select email_id from Authentication where user_id=" + userId;
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);
		if (rs.next()) {
			// TODO log statement to be added
			return rs.getString(1);
		}
		return null;
	}

	public static long getProfilePhotoID(long userId)
			throws SQLException, ClassNotFoundException {
		String query = "select media_id from user_details where user_id=" + userId;
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);
		if (rs.next()) {
			// TODO log the result
			return rs.getLong("media_id");
		}
		return 0;
	}

	public static int getFollowersCount(long userId) 
			throws SQLException, ClassNotFoundException {
		String query = "select follower_count from user_details where user_id=" + userId;

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);
		if (rs.next()) {
			// TODO log the result
			return rs.getInt("follower_count");
		}
		return 0;
	}

	public static long getFollowingCount(long userId) 
			throws SQLException, ClassNotFoundException {
		String query = "select following_count from user_details where user_id=" + userId;

		logger.info("executing sql query: " + query.toString());
		
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);
		if (rs.next()) {
			// TODO log the result
			return rs.getInt("following_count");
		}
		return 0;
	}

	public static long getUserID(String handle)
			throws SQLException, ClassNotFoundException {
		String query = "select user_id from authentication where handle=\"" + handle + "\"";

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);
		if (rs.next()) {
			// TODO log the result
			return rs.getLong("user_id");
		}
		return 0;
	}

	public static String getUserPassword(long userId)
			throws SQLException, ClassNotFoundException {
		String query = "select password from Authentication where user_id=" + userId;

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);
		if (rs.next()) {
			// TODO log the result
			return rs.getString("password");
		}
		return null;	
	}

	public static String getUserPassword(String email)
			throws SQLException, ClassNotFoundException {
		String query = "select password from Authentication where email_id=\"" + email + "\"";

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);
		if (rs.next()) {
			// TODO log the result
			return rs.getString("password");
		}
		return null;
	}
	
	public static User getUserDetailsFromDb(long userId)
			throws SQLException, ClassNotFoundException {
		User user = new User();
		StringBuilder query = new StringBuilder("select ud.user_id, a.email_id, a.name, a.handle, ud.follower_count,");
		query.append("ud.following_count");
		query.append(" from user_details as ud inner join authentication a ");
		query.append("on ud.user_id = a.user_id and a.user_id = ");
		query.append(userId);

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			// TODO log the result
			//user = getUserCredentials(userId);
			user.setUserId(rs.getLong("user_id"));
			user.setEmail(rs.getString("email_id"));
			user.setUserName(rs.getString("name"));
			user.setHandle(rs.getString("handle"));
			user.setFollower(rs.getInt("follower_count"));
			user.setFollowing(rs.getInt("following_count"));
			
			return user;
		} 
		return null;
	}

	private static User getUserCredentials(long userId)
			throws ClassNotFoundException, SQLException {
		User user = new User();

		StringBuilder query = new StringBuilder("select * from Authentication where user_id=");
		query.append(userId);

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			// TODO log the result
			user = prepareUserObject(rs);
			user.setPassword(rs.getString("password"));
			return user;
		}
		return null;
	}
	
	public static List<User> getAllFollowers(long userId) 
			throws ClassNotFoundException, SQLException {
		List<User> allFollowerIDs = new ArrayList<>();
		StringBuilder query = new StringBuilder("select * from authentication where user_id in (");
		query.append("select follower from connections where following= " + userId + " and end_time is null) order by handle");

		logger.info("executing sql query: " + query.toString());		
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		while (rs.next()) {
			// TODO log all the followers
			allFollowerIDs.add(prepareUserObject(rs));
		}
		return allFollowerIDs;
	}

	public static List<User> getAllFollowing(long userId) 
			throws ClassNotFoundException, SQLException {
		List<User> allFollowingIDs = new ArrayList<>();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from Authentication where user_id in (");
		stringBuilder.append("select following from connections where follower = ");
		stringBuilder.append(userId);
		stringBuilder.append(" and end_time is null ) order by handle");
		
		logger.info("executing sql query: " + stringBuilder.toString());
		ResultSet rs = SQLConnection.executeQuery(stringBuilder.toString());
//		ResultSet rs = SQLConnection.db.queryDb(stringBuilder.toString());
		while (rs.next()) {
			// TODO log all the followers
			allFollowingIDs.add(prepareUserObject(rs));
		}
		return allFollowingIDs;		
	}
	
	public static List<Long> getAllMentionsForTweet(long tweet_id)
			throws ClassNotFoundException, SQLException {
		List<Long> allMentions = new ArrayList<>();
		String query = "select user_id from tweet_mentions where tweet_id=" + tweet_id;

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		while (rs.next()) {
			allMentions.add(rs.getLong("user_id"));
		}
		return allMentions;
	}
	
	public static List<RetweetModel> getRetweetUsersAfterLogout(long userId, long logoutTime) 
			throws ClassNotFoundException, SQLException {
		List<RetweetModel> allRetweetsAfterLogout = new ArrayList<>();
		
		StringBuilder query = new StringBuilder("select a.user_id, a.handle, r.tweet_id from authentication as a ");
		query.append("inner join retweets r on a.user_id = r.user_id and r.author_id = ");
		query.append(userId);
		query.append(" and created_at > ");
		query.append(logoutTime);
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		RetweetModel retweet = new RetweetModel();
		while(rs.next()) {
			retweet.setTweetId(rs.getLong("tweet_id"));
			retweet.setUserId(rs.getLong("user_id"));
			retweet.setUserHandle(rs.getString("handle"));
			allRetweetsAfterLogout.add(retweet);
		}
		return allRetweetsAfterLogout;
	}
	
	public static boolean checkEmailExists(String email) 
			throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder();
		
		query.append("select * from Authentication where email_id= ")
		.append(SqlQuerySeparators.DOUBLEQUOTE)
		.append(email)
		.append(SqlQuerySeparators.DOUBLEQUOTE);
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			logger.info("email doesn't exists");
			
			return true;
		}
		return false;
	}

	public static boolean checkHandleExists(String handle) 
			throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder();
		
		query.append("select * from Authentication where handle=")
		.append(SqlQuerySeparators.DOUBLEQUOTE)
		.append(handle)
		.append(SqlQuerySeparators.DOUBLEQUOTE);
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			return true;
		}
		return false;
	}

	public static User prepareUserObject(ResultSet rs)
			throws SQLException {
		User user = new User();
		user.setEmail(rs.getString("email_id"));
		user.setHandle(rs.getString("handle"));
		user.setUserName(rs.getString("name"));
		user.setUserId(rs.getLong("user_id"));
		return user;
	}

	public static List<User> getFollowersAfterTimestamp(long userId, long timestamp) 
			throws ClassNotFoundException, SQLException {
		List<User> followersAfterTimestamp = new ArrayList<>();
		
		StringBuilder query = new StringBuilder("select a.user_id, a.handle from authentication as a where ");
		query.append("a.user_id in (");
		query.append("select follower from connections where following = ");
		query.append(userId);
		query.append(" and start_time > ");
		query.append(timestamp);
		query.append(" and end_time is null)");
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		while(rs.next()) {
			User user = new User();
			user.setHandle(rs.getString("handle"));
			user.setUserId(rs.getLong("user_id"));
			followersAfterTimestamp.add(user);
		}
		return followersAfterTimestamp;
	}
	
	public static long getLogoutTime(long userId) 
			throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select logout_time from user_details where user_id = ");
		query.append(userId);
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			return rs.getLong("logout_time");
		}
		return 0;
	}
	
	public static boolean isConnection(long userId1, long userId2) throws ClassNotFoundException, SQLException {
		
		StringBuilder query = new StringBuilder("select end_time from connections where follower = ");
		query.append(userId1).append(" and").append(" following = ").append(userId2);
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		if (rs.next()) {
			return (rs.getLong(1) == 0);
		}
		return false;
	}

	public static List<User> getAllUsersWithNameStartingWith(String usernameLike) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		StringBuilder query = new StringBuilder("select * from Authentication where handle like ")
							.append("\'").append(usernameLike).append("%\'").append(" limit 5");
		
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());
		logger.info("executing sql query : "+ query.toString());
		
		List<User> allUsers = new ArrayList<>();
		while(rs.next()) {
			User user = new User();
			user.setUserName(rs.getString("name"));
			user.setHandle(rs.getString("handle"));
			user.setEmail(rs.getString("email_id"));
			user.setUserId(rs.getLong("user_id"));
			allUsers.add(user);
			
		}
		
		
		return allUsers;
	}


	public static long generateUserId() throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select max(user_id) from authentication");
		ResultSet rs = SQLConnection.executeQuery(query.toString());
//		ResultSet rs = SQLConnection.db.queryDb(query.toString());

		if(rs.next()) {
			return rs.getLong(1);
		}
		
		// TODO Auto-generated method stub
		return 0;
	}

	public static String getUserHandle(long userId) throws ClassNotFoundException, SQLException {
		
		String query = "select handle from Authentication where user_id=" + userId;

		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query);
//		ResultSet rs = SQLConnection.db.queryDb(query);

		if (rs.next()) {
			// TODO log the result
			return rs.getString("handle");
		}
		return null;
	}

	public static void setLogoutAfterLogin(Long userId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("update user_details set logout = null where user_id = ").append(userId);
		int rs = SQLConnection.executeUpdate(query.toString());
		return;
		
	}

	public static void setLogoutAfterSignout(Long userId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("update user_details set logout = ").append(System.currentTimeMillis()).append(" where user_id = ").append(userId);
		logger.info("log out time set");
		SQLConnection.executeUpdate(query.toString());
		return;
	}

	public static boolean checkValidRetweetUser(long long1) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select * from retweets where user_id=");
		query.append(long1);
		
		logger.info("executing sql query: " + query.toString());
		ResultSet rs = SQLConnection.executeQuery(query.toString());
		if (rs.next()) {
			return true;
		}
		return false;
	}
}
