package models;

/**
 * @author mayank.ra
 */
public class RetweetModel {
	private long userId;
	private String userHandle;
	private long tweetId;
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserHandle() {
		return userHandle;
	}
	
	public void setUserHandle(String userHandle) {
		this.userHandle = userHandle;
	}

	public long getTweetId() {
		return tweetId;
	}

	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}
}
