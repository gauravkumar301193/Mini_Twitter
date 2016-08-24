package response.util;

import models.Tweet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.List;

public class CreateJSONResponseTweets {

	public static JSONObject jsonResponseTweet(List<Tweet> tweets)
			throws ClassNotFoundException, SQLException {
		
		JSONObject obj;
		JSONArray allTweets = new JSONArray();
		for (Tweet twt : tweets) {
			obj = new JSONObject();
			obj.put("tweetId", twt.getTweetId());
			obj.put("content", twt.getTweetText());
			obj.put("likes", twt.getLikeCount());
			obj.put("retweetCount", twt.getRetweetCount());
			obj.put("likes", twt.getLikeCount());
			if (twt.isRetweet()) {
				obj.put("retweet", true);
				obj.put("retweetUserId", twt.getRetweetUserId());
			} 
			obj.put("timestamp", twt.getTimestamp());
			
			allTweets.add(obj);
		}
		
		JSONObject mainObj = new JSONObject();
		mainObj.put("Tweets", allTweets);
		
		return mainObj;
		
	}
	
	public static JSONObject jsonResponseOfSingleTweet(Tweet twt) {
		JSONObject obj = new JSONObject();
		obj.put("tweetId", twt.getTweetId());
		obj.put("content", twt.getTweetText());
		obj.put("likes", twt.getLikeCount());
		obj.put("retweetCount", twt.getRetweetCount());
		obj.put("likes", twt.getLikeCount());
		if (twt.isRetweet()) {
			obj.put("retweet", true);
			obj.put("retweetUserId", twt.getRetweetUserId());
		} 
		obj.put("timestamp", twt.getTimestamp());
		
		return obj;
	}
}
