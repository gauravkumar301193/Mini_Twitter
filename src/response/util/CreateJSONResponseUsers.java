package response.util;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import models.User;

public class CreateJSONResponseUsers {
	public static JSONObject jsonResponseUsers(List<User> users) {
		
		JSONObject obj;
		JSONArray allUsers = new JSONArray();
		for (User usr : users) {
			obj = new JSONObject();
			obj.put("userId", usr.getUserId());
			obj.put("name", usr.getUserName());
			obj.put("handle", usr.getHandle());
			obj.put("email", usr.getEmail());
			
			allUsers.add(obj);
		}
		
		JSONObject mainObj = new JSONObject();
		mainObj.put("Users", allUsers);
		
		return mainObj;
	}
}

