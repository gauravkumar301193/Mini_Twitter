package models;

/**
 * @author mayank.ra
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Tweet {
	private String tweetText;
	private long tweetId;
	private long userId;
	private long timestamp;
	private long mediaId;
	private int likeCount;
	private int retweetCount;
	private boolean retweet;
	private long retweetUserId;
	private List<String> hashtags;
	private List<Long> mentions;
	private String handle;
	
	private static long tweetIdGenerator = 9908028;
	static Logger logger = Logger.getLogger(Tweet.class);
	public Tweet() {
		hashtags = new ArrayList<String>();
		retweet = false;
		mentions = new ArrayList<Long>();
	}

	public long getRetweetUserId() {
		return retweetUserId;
	}

	public void setRetweetUserId(long retweetUserId) {
		this.retweetUserId = retweetUserId;
	}
	
	public boolean isRetweet() {
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
	
	public long getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(long mediaId) {
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

	public List<Long> getMentions() {
		return mentions;
	}

	public void addMentions(long mentions) {
		this.mentions.add(mentions);
	}
	
	public static long generateTweetID() {
		tweetIdGenerator++;
		logger.info("New tweet id generated " + tweetIdGenerator);
		return tweetIdGenerator;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
