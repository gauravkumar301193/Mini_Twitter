package services.tweet;

import java.sql.SQLException;
import java.util.List;

import query.database.QueryTweet;

public class GetHashtags {

	public static List<String> getTrendingHashtags() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<String> hashtags = QueryTweet.getHashtags();
		if(hashtags.size() != 0)
		{
			return hashtags;
		}
		return null;
	}

}
