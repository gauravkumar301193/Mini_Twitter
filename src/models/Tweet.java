package models;

import java.sql.SQLException;

/**
 * @author mayank.ra
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import query.database.QueryTweet;

public class Tweet {
	private static final Logger logger = Logger.getLogger(Tweet.class);
	
	private String tweetText;
	private long tweetId;
	private long userId;
	private long timestamp;
	private Long mediaId;
	private int likeCount;
	private int retweetCount;
	private boolean retweet;
	private long retweetUserId;
	private List<String> hashtags;
	private List<Integer> mentions;
	private String handle;
	private String retweetHandle;
	
	private static long tweetIdGenerator = 9908031;
	
	public Tweet() {
		hashtags = new ArrayList<String>();
		retweet = false;
		mentions = new ArrayList<Integer>();
	}

	public long getRetweetUserId() {
		return retweetUserId;
	}

	public void setRetweetUserId(long retweetUserId) {
		this.retweetUserId = retweetUserId;
	}
	
	public boolean isRetweet(long l) {
		return retweet;
	}
	
	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public void markRetweet() {
		retweet = true;
	}
	
	public String getTweetText() {
		return tweetText;
	}
	
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}

	public long getTweetId() {
		return tweetId;
	}

	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public Long getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}
	
	public int getLikeCount() {
		return likeCount;
	}
	
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	public int getRetweetCount() {
		return retweetCount;
	}
	
	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}

	public List<String> getHashtags() {
		return hashtags;
	}
	
	public void addHashtags(String hashtag) {
		this.hashtags.add(hashtag);
	}

	public List<Integer> getMentions() {
		return mentions;
	}

	public void addMentions(Integer mentions) {
		this.mentions.add(mentions);
	}
	
	public static long generateTweetID() throws ClassNotFoundException, SQLException {
		tweetIdGenerator = QueryTweet.generateNewTweetId() + 1;
		logger.info("New tweet id generated " + tweetIdGenerator);
		return tweetIdGenerator;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRetweetHandle() {
		return retweetHandle;
	}

	public void setRetweetHandle(String retweetHandle) {
		this.retweetHandle = retweetHandle;
	}

	
}
