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
		
		JSONObject obj = new JSONObject();
		JSONArray allTweets = new JSONArray();
		if (tweets == null) {
			obj.put("Tweets", allTweets);
			return obj;
		}
		for (Tweet twt : tweets) {
			obj = jsonResponseOfSingleTweet(twt, userId);
			allTweets.add(obj);			
		}
		
		JSONObject mainObj = new JSONObject();
		mainObj.put("Tweets", allTweets);
		
		return mainObj;
		
	}
	
	public static JSONObject jsonResponseOfSingleTweet(Tweet twt , Long userId) throws ClassNotFoundException, SQLException {
		
		TweetParser tp = new TweetParser(twt.getTweetText());
		if(tp != null) {
		tp.parseTweet();
		}
		JSONObject obj = new JSONObject();
		obj.put("tweetId", twt.getTweetId());
		obj.put("authorHandle", twt.getHandle());
		obj.put("authorId", twt.getUserId());
		obj.put("timestamp", twt.getTimestamp());
		//obj.put("content", twt.getTweetText());
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
        
        obj.put("allWords",tweetText);
        
        JSONArray noSpaceSeparatedWords = new JSONArray();
		List<Integer> noSpaceWordsList = tp.getWithoutSpaceWords();
		//System.out.println("no of words in tweet :" + wordsInTweet.size() );
		Iterator<Integer> noSpaceWordsListIter = noSpaceWordsList.iterator();
        while(noSpaceWordsListIter.hasNext()) {
        	JSONObject withoutSpaceWords = new JSONObject();
        	withoutSpaceWords.put("withoutSpaceWordsIndex", noSpaceWordsListIter.next());
        	noSpaceSeparatedWords.add(withoutSpaceWords);      
        }
        
        obj.put("noSpaceWords", noSpaceSeparatedWords);
        
	
        if (twt.isRetweet()) {
			obj.put("isARetweet", true);
			obj.put("retweetUserId", twt.getRetweetUserId());
			obj.put("retweetUserHandle", twt.getRetweetHandle());
		} 
	
		boolean isLiked = false;
		if(userId != null && CheckValidity.isValidUser(userId)){
			isLiked = QueryTweet.checkIsLiked(userId, twt.getTweetId());
		}
		obj.put("isLikedByLoggedInUser", isLiked);

		boolean isRetweetedByLoggedInUser = false;
		if(userId != null && CheckValidity.isValidUser(userId)){
			isLiked = QueryTweet.checkIsRetweetedByUser(userId, twt.getTweetId());
		}
		
		//obj.put("isRetweetedByLoggedInUser", isRetweetedByLoggedInUser);

		JSONArray mentions = new JSONArray();
		JSONArray hashtags = new JSONArray();
		JSONArray urls = new JSONArray();
		
		List<Integer> mentionsList = tp.getMentions();
		List<Integer> hashtagsList = tp.getHashtags();
		List<Integer> urlsList = tp.getUrls();
		
		Iterator<Integer> mentionsIter = mentionsList.iterator();
		Iterator<Integer> hashtagsIter = hashtagsList.iterator();
		Iterator<Integer> urlsIter = urlsList.iterator();
        
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
         obj.put("mentions",mentions);

         while(urlsIter.hasNext()) {
         	JSONObject urlIndex = new JSONObject();
         	urlIndex.put("index", urlsIter.next());
            urls.add(urlIndex);
  
         }
         
         obj.put("urls", urls);
 		
        
		return obj;
	}
}
