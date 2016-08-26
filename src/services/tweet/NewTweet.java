package services.tweet;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import database.dummy.dump.TweetParser;
import models.Tweet;
import query.database.UpdateTweet;

public class NewTweet {
	
	static Logger logger = Logger.getLogger(NewTweet.class);
	public static boolean postTweet(Tweet tweet) throws ClassNotFoundException, SQLException {
		if(isValidTweet(tweet)) {
			TweetParser tp = new TweetParser(tweet.getTweetText());
			tp.parseTweet();
			List<Integer> mentionsIndex = tp.getMentions();
			List<Integer> hashtagsIndex = tp.getHashtags();
			
			String[] listOfWords = tp.listToArrayOfWords();
			
			Iterator<Integer> mentionsIter = mentionsIndex.iterator();

			Iterator<Integer> hashtagsIter = hashtagsIndex.iterator();
			String hashtag = "";
			String mention = "";
			List<String> hashtags = new ArrayList<>();
			List<Integer> mentions = new ArrayList<>();
			
			if (mentionsIter.hasNext())
				mentionsIter.next();
			while(mentionsIter.hasNext()) {
				tweet.addMentions(mentionsIter.next());
			}
			
			while(hashtagsIter.hasNext()) {
				hashtag = listOfWords[hashtagsIter.next()];
				tweet.addHashtags(hashtag.substring(1, hashtag.length()-1));
			}
			
			return UpdateTweet.postTweet(tweet);
		}
		else {
			logger.error("Tweet Not valid ");
			return false;
		}
	}
	
	private static boolean isValidTweet(Tweet tweet) {
		return !(tweet.getTweetText() == "");
	}
}
