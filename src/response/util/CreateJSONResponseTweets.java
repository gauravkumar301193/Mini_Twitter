package response.util;

import models.Tweet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import database.dummy.dump.TweetParser;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class CreateJSONResponseTweets {

	public static JSONObject jsonResponseTweet(List<Tweet> tweets)
			throws ClassNotFoundException, SQLException {
		
		JSONObject obj;
		JSONArray allTweets = new JSONArray();
		for (Tweet twt : tweets) {
			TweetParser tp = new TweetParser(twt.getTweetText());
			
			obj = new JSONObject();
			obj.put("tweetId", twt.getTweetId());
			obj.put("userHandle", twt.getHandle());
			obj.put("userId", twt.getUserId());
			obj.put("timestamp", twt.getTimestamp());
			obj.put("content", twt.getTweetText());
			obj.put("likes", twt.getLikeCount());
			obj.put("retweetCount", twt.getRetweetCount());
			obj.put("likes", twt.getLikeCount());
			
			JSONArray tweetText = new JSONArray();
			List<String> wordsInTweet = tp.getAllWordsInTweet();
			
			Iterator<String> wordsInTweetIter = wordsInTweet.iterator();
	        while(wordsInTweetIter.hasNext()) {
	        	tweetText.add(wordsInTweetIter.next());
	            
	        }
	        obj.put("tweetText", tweetText);
			if (twt.isRetweet()) {
				obj.put("retweet", true);
				obj.put("retweetUserId", twt.getRetweetUserId());
				obj.put("retweetHandle", twt.getRetweetHandle());
			} 
			obj.put("timestamp", twt.getTimestamp());
			JSONArray mentions = new JSONArray();
			JSONArray hashtags = new JSONArray();
			
			List<Integer> mentionsList = tp.getMentions();
			List<Integer> hashtagsList = tp.getHashtags();
			
			Iterator<Integer> mentionsIter = mentionsList.iterator();

			Iterator<Integer> hashtagsIter = hashtagsList.iterator();
	        
	        while(mentionsIter.hasNext()) {
	            mentionsList.add(mentionsIter.next());
	            
	        }
	        
	        obj.put("mentions", mentionsList);
			
	        while(hashtagsIter.hasNext()) {
	            mentionsList.add(hashtagsIter.next());
	            
	        }
	        
	        obj.put("hashtags", hashtagsIter);
			
			
			allTweets.add(obj);
		}
		
		JSONObject mainObj = new JSONObject();
		mainObj.put("Tweets", allTweets);
		
		return mainObj;
		
	}
	
	public static JSONObject jsonResponseOfSingleTweet(Tweet twt) {
		
		TweetParser tp = new TweetParser(twt.getTweetText());
		JSONObject obj = new JSONObject();
		obj.put("tweetId", twt.getTweetId());
		obj.put("userHandle", twt.getHandle());
		obj.put("userId", twt.getUserId());
		obj.put("timestamp", twt.getTimestamp());
		obj.put("content", twt.getTweetText());
		obj.put("likes", twt.getLikeCount());
		obj.put("retweetCount", twt.getRetweetCount());
		obj.put("likes", twt.getLikeCount());
		
		JSONArray tweetText = new JSONArray();
		List<String> wordsInTweet = tp.getAllWordsInTweet();
		
		Iterator<String> wordsInTweetIter = wordsInTweet.iterator();
        while(wordsInTweetIter.hasNext()) {
        	tweetText.add(wordsInTweetIter.next());
            
        }
        
        obj.put("tweetText", tweetText);
		if (twt.isRetweet()) {
			obj.put("retweet", true);
			obj.put("retweetUserId", twt.getRetweetUserId());
			obj.put("retweetHandle", twt.getRetweetHandle());
		} 
		
		obj.put("timestamp", twt.getTimestamp());
		JSONArray mentions = new JSONArray();
		JSONArray hashtags = new JSONArray();
		
		List<Integer> mentionsList = tp.getMentions();
		List<Integer> hashtagsList = tp.getHashtags();
		
		Iterator<Integer> mentionsIter = mentionsList.iterator();

		Iterator<Integer> hashtagsIter = hashtagsList.iterator();
        
        while(mentionsIter.hasNext()) {
            mentionsList.add(mentionsIter.next());
            
        }
        
        obj.put("mentions", mentionsList);
		
        while(hashtagsIter.hasNext()) {
            mentionsList.add(hashtagsIter.next());
            
        }
        
        obj.put("hashtags", hashtagsIter);
		

		return obj;
	}
}
