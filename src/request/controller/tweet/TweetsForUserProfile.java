package request.controller.tweet;

import models.Tweet;
import query.database.QueryUser;

import org.json.simple.JSONObject;
import response.util.CreateJSONResponseTweets;
import services.tweet.GetTweetsForUserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class TweetForUser
 */

@WebServlet(urlPatterns = "/TweetsForUserProfile", loadOnStartup = 1)
public class TweetsForUserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			long userId = 0;
			if (!request.getParameterMap().containsKey("user_id")) {
				if(request.getParameterMap().containsKey("handle")) {
					String handle = request.getParameter("handle");
					userId = QueryUser.getUserID(handle);
				}
			}
			else {
				 userId = Long.parseLong(request.getParameter("user_id"));
			}
			long latestTime = System.currentTimeMillis();
			long startTime = 0;
			if (request.getParameterMap().containsKey("latest_time")) {
				latestTime = Long.parseLong(request.getParameter("latest_time"));
			}
			if (request.getParameterMap().containsKey("start_time")) {
				startTime = Long.parseLong(request.getParameter("start_time"));
			}
			List<Tweet> tweetsForProfile = GetTweetsForUserProfile.tweetsForUserProfile(userId, startTime, latestTime);
			JSONObject tweets = CreateJSONResponseTweets.jsonResponseTweet(tweetsForProfile , userId);
			response.setContentType("application/json");
			response.getWriter().write(tweets.toString());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			e.printStackTrace();
		}
	}
	
}
