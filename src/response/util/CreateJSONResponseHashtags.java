package response.util;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateJSONResponseHashtags {

	public static JSONObject jsonResponseHashtags(List<String> trendingHashtags) {
		JSONObject obj = new JSONObject();
		JSONArray hashtags = new JSONArray();
		for(String hashtag: trendingHashtags) {
			JSONObject hashtagObj = new JSONObject();
        	hashtagObj.put("word", hashtag);
            hashtags.add(hashtagObj);    
		}
		obj.put("hashtags", hashtags);	
		return obj;
	}
}
