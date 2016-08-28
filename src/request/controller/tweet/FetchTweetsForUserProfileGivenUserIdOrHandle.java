package request.controller.tweet;

import models.Tweet;
import query.database.QueryUser;

import org.apache.log4j.Logger;
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

@WebServlet(urlPatterns = "/FetchTweetsForUserProfileGivenUserIdOrHandle", loadOnStartup = 1)
public class FetchTweetsForUserProfileGivenUserIdOrHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(FetchTweetsForUserProfileGivenUserIdOrHandle.class);
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			long userId = 0;
			Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
		
			logger.info("logged in user set "+ loggedInUser);
			if (!request.getParameterMap().containsKey("userId")) {
				if(request.getParameterMap().containsKey("handle")) {
					String handle = request.getParameter("handle");
					userId = QueryUser.getUserID(handle);
				}
			}
			else {
				 userId = Long.parseLong(request.getParameter("userId"));
			}
			logger.info("user id set");
			long latestTime = System.currentTimeMillis();
			long startTime = 0;
			if (request.getParameterMap().containsKey("latestTime")) {
				latestTime = Long.parseLong(request.getParameter("latestTime"));
			}
			if (request.getParameterMap().containsKey("startTime")) {
				startTime = Long.parseLong(request.getParameter("startTime"));
			}
			List<Tweet> tweetsForProfile = GetTweetsForUserProfile.tweetsForUserProfile(userId, startTime, latestTime);
			JSONObject tweets = CreateJSONResponseTweets.jsonResponseTweet(tweetsForProfile , loggedInUser);
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setStatus(200);
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
