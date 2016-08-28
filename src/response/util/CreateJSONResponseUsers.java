package response.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import models.User;
import query.database.QueryTweet;
import query.database.QueryUser;

public class CreateJSONResponseUsers {
	public static JSONObject jsonResponseUsers(List<User> users, Long loggedInUserId) throws ClassNotFoundException, SQLException {
		
		JSONObject obj;
		JSONArray allUsers = new JSONArray();
		for (User usr : users) {
			obj = jsonResponseOfSingleUser(usr , loggedInUserId);
			allUsers.add(obj);
			}
		
			JSONObject mainObj = new JSONObject();
			mainObj.put("Users", allUsers);
		
			return mainObj;
	}
	
	public static JSONObject jsonResponseOfSingleUser(User usr, Long loggedInUserId) throws ClassNotFoundException, SQLException {
				
			JSONObject obj = new JSONObject();
			obj.put("userId", usr.getUserId());
			obj.put("name", usr.getUserName());
			obj.put("handle", usr.getHandle());
			obj.put("email", usr.getEmail());
			obj.put("tweetCount", QueryTweet.getTweetCount(usr.getUserId()));
			obj.put("followerCount", QueryUser.getFollowersCount(usr.getUserId()));
			obj.put("followingCount", QueryUser.getFollowingCount(usr.getUserId()));
			
			boolean isFollowing= false;
			System.out.println(loggedInUserId + " " + usr.getUserId());
			if(loggedInUserId != usr.getUserId() && QueryUser.isConnection(loggedInUserId, usr.getUserId())) {
				isFollowing = true;
			}
			
			obj.put("isFollowing" , isFollowing);
			
			return obj;
	}
}


