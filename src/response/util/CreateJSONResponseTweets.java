package response.util;

import models.Tweet;
import query.database.QueryTweet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import database.dummy.dump.TweetParser;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class CreateJSONResponseTweets {

	public static JSONObject jsonResponseTweet(List<Tweet> tweets , Long userId)
			throws ClassNotFoundException, SQLException {
		
		JSONObject obj;
		JSONArray allTweets = new JSONArray();
		for (Tweet twt : tweets) {
			//System.out.println(twt.getTweetText());
			TweetParser tp = new TweetParser(twt.getTweetText());
			tp.parseTweet();
			
			obj = new JSONObject();
			obj.put("tweetId", twt.getTweetId());
			obj.put("userHandle", twt.getHandle());
			obj.put("userId", twt.getUserId());
			obj.put("timestamp", twt.getTimestamp());
			obj.put("content", twt.getTweetText());
			obj.put("likes", twt.getLikeCount());
			obj.put("retweetCount", twt.getRetweetCount());
			
			boolean isLiked = false;
			if(userId != null && CheckValidity.isValidUser(userId)){
				isLiked = QueryTweet.checkIsLiked(userId, twt.getTweetId());
			}
			
			JSONArray tweetText = new JSONArray();
			List<String> wordsInTweet = tp.getAllWordsInTweet();
			
			//System.out.println("no of words in tweet :" + wordsInTweet.size() );
			Iterator<String> wordsInTweetIter = wordsInTweet.iterator();
	        while(wordsInTweetIter.hasNext()) {
	        	JSONObject tweetWords = new JSONObject();
	        	tweetWords.put("word", wordsInTweetIter.next());
	            tweetText.add(tweetWords);      
	        }
	        
	        obj.put("tweetText",tweetText);
	        
	        JSONArray noSpaceSeparatedWords = new JSONArray();
			List<Integer> noSpaceWordsList = tp.getWithoutSpaceWords();
			
			//System.out.println("no of words in tweet :" + wordsInTweet.size() );
			Iterator<Integer> noSpaceWordsListIter = noSpaceWordsList.iterator();
	        while(noSpaceWordsListIter.hasNext()) {
	        	JSONObject withoutSpaceWords = new JSONObject();
	        	withoutSpaceWords.put("withoutSpaceWordsIndex", noSpaceWordsListIter.next());
	        	noSpaceSeparatedWords.add(withoutSpaceWords);      
	        }
	        
	        obj.put("withoutSpaceWords", noSpaceSeparatedWords);
	        
		if (twt.isRetweet()) {
				obj.put("retweet", true);
				obj.put("retweetUserId", twt.getRetweetUserId());
				obj.put("retweetHandle", twt.getRetweetHandle());
			} 

			JSONArray mentions = new JSONArray();
			JSONArray hashtags = new JSONArray();
			
			List<Integer> mentionsList = tp.getMentions();
			List<Integer> hashtagsList = tp.getHashtags();
			
			Iterator<Integer> mentionsIter = mentionsList.iterator();

			Iterator<Integer> hashtagsIter = hashtagsList.iterator();
	        
	        while(mentionsIter.hasNext()) {
	        	JSONObject mentionIndex = new JSONObject();
	        	mentionIndex.put("index", mentionsIter.next());
	            mentions.add(mentionIndex);
	            
	        }
	        
	        obj.put("mentions",mentions);
			
	        while(hashtagsIter.hasNext()) {
	        	JSONObject hashtagsIndex = new JSONObject();
	        	hashtagsIndex.put("index", hashtagsIter.next());
	            hashtags.add(hashtagsIndex);
	        }
	        
	        obj.put("hashtags", hashtags);
	        
			allTweets.add(obj);
		}
		
		JSONObject mainObj = new JSONObject();
		mainObj.put("Tweets", allTweets);
		
		return mainObj;
		
	}
	
	public static JSONObject jsonResponseOfSingleTweet(Tweet twt , Long userId) throws ClassNotFoundException, SQLException {
		
		TweetParser tp = new TweetParser(twt.getTweetText());
		tp.parseTweet();
		
		JSONObject obj = new JSONObject();
		obj.put("tweetId", twt.getTweetId());
		obj.put("userHandle", twt.getHandle());
		obj.put("userId", twt.getUserId());
		obj.put("timestamp", twt.getTimestamp());
		obj.put("content", twt.getTweetText());
		obj.put("likes", twt.getLikeCount());
		obj.put("retweetCount", twt.getRetweetCount());
	
		
		JSONArray tweetText = new JSONArray();
		List<String> wordsInTweet = tp.getAllWordsInTweet();
		
		//System.out.println("no of words in tweet :" + wordsInTweet.size() );
		Iterator<String> wordsInTweetIter = wordsInTweet.iterator();
        while(wordsInTweetIter.hasNext()) {
        	JSONObject tweetWords = new JSONObject();
        	tweetWords.put("word", wordsInTweetIter.next());
            tweetText.add(tweetWords);      
        }
        
        obj.put("tweetText",tweetText);
        
        JSONArray noSpaceSeparatedWords = new JSONArray();
		List<Integer> noSpaceWordsList = tp.getWithoutSpaceWords();
		
		//System.out.println("no of words in tweet :" + wordsInTweet.size() );
		Iterator<Integer> noSpaceWordsListIter = noSpaceWordsList.iterator();
        while(noSpaceWordsListIter.hasNext()) {
        	JSONObject withoutSpaceWords = new JSONObject();
        	withoutSpaceWords.put("withoutSpaceWordsIndex", noSpaceWordsListIter.next());
        	noSpaceSeparatedWords.add(withoutSpaceWords);      
        }
        
        obj.put("withoutSpaceWords", noSpaceSeparatedWords);
        
	if (twt.isRetweet()) {
			obj.put("retweet", true);
			obj.put("retweetUserId", twt.getRetweetUserId());
			obj.put("retweetHandle", twt.getRetweetHandle());
		} 
	
		boolean isLiked = false;
		if(userId != null && CheckValidity.isValidUser(userId)){
			isLiked = QueryTweet.checkIsLiked(userId, twt.getTweetId());
		}
		obj.put("isLikedByUser", isLiked);
		JSONArray mentions = new JSONArray();
		JSONArray hashtags = new JSONArray();
		
		List<Integer> mentionsList = tp.getMentions();
		List<Integer> hashtagsList = tp.getHashtags();
		
		Iterator<Integer> mentionsIter = mentionsList.iterator();

		Iterator<Integer> hashtagsIter = hashtagsList.iterator();
        
        while(mentionsIter.hasNext()) {
        	JSONObject mentionIndex = new JSONObject();
        	mentionIndex.put("index", mentionsIter.next());
            mentions.add(mentionIndex);
            
        }
        
        obj.put("mentions",mentions);
		
        while(hashtagsIter.hasNext()) {
        	JSONObject hashtagsIndex = new JSONObject();
        	hashtagsIndex.put("index", hashtagsIter.next());
            hashtags.add(hashtagsIndex);
        }
        
        obj.put("hashtags", hashtags);
        
		return obj;
	}
}
