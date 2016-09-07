package response.util;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.*;

import models.RetweetModel;
import models.Tweet;
import models.User;
import query.database.QueryTweet;

/**
 * @author mayank.ra
 */
public class CreateJSONResponseNotifications {
	private JSONObject jsonObject;
	public CreateJSONResponseNotifications() {
		jsonObject = new JSONObject();
	}
	
	public void addMentionsAfterLogout(List<Tweet> tweets) 
			throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		for (Tweet tweet: tweets) {
			JSONObject mention = new JSONObject();
			mention.put("tweet_id", tweet.getTweetId());
			mention.put("handle", QueryTweet.getAuthorOfTweet(tweet.getTweetId()));
			jsonArray.add(mention);
		}
		jsonObject.put("mentions", jsonArray);
	}
	
	public void addRetweetsAfterLogout(List<RetweetModel> retweets) {
		JSONArray jsonArray = new JSONArray();
		for (RetweetModel retweet : retweets) {
			JSONObject singleRetweetObject = new JSONObject();
			singleRetweetObject.put("handle", retweet.getUserHandle());
			singleRetweetObject.put("tweet_id", retweet.getTweetId());
			singleRetweetObject.put("user_id", retweet.getUserId());
			jsonArray.add(singleRetweetObject);
		}
		jsonObject.put("retweets", jsonArray);
	}
	
	public void addFollowersAfterLogout(List<User> followers) {
		JSONArray jsonArray = new JSONArray();
		for (User user : followers) {
			JSONObject singleFollower = new JSONObject();
			singleFollower.put("user_id", user.getUserId());
			singleFollower.put("handle", user.getHandle());
			jsonArray.add(singleFollower);
		}
		jsonObject.put("followers", jsonArray);
	}
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}
}
