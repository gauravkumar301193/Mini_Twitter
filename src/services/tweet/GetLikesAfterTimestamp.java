package services.tweet;
/*
 * @author gaurav.kum
 */
import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.User;
import query.database.QueryTweet;
import query.database.QueryUser;

public class GetLikesAfterTimestamp {
	
	static Logger logger = Logger.getLogger(GetLikesAfterTimestamp.class);
	public static int likesAfterLogout(long userId) throws ClassNotFoundException, SQLException {
		String handle = QueryUser.getUserHandle(userId);
		System.out.println(handle);
		if(QueryUser.checkHandleExists(handle)) {
			long timestamp = QueryUser.getLastLogout(userId);
			
			return QueryTweet.getLikesAfterTimestamp(timestamp, userId);
		}
		else {
			logger.error("Wrong user id received: " + userId);
			return 0;
		}
	}
}
