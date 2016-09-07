package request.controller.tweet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import models.Tweet;
import response.util.CreateJSONResponseTweets;
/*
 * @author: gaurav.kum
 */
/**
 * Input: String hashtag
 * Output: JSON object of all tweets
 */

/**
 * Servlet implementation class TweetsWithHash
 */
@WebServlet("/TweetsWithHashtag")
public class FetchTweetsForHashtagGivenHashtag extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		if(request.getSession(false) == null) {
			response.setStatus(504);
			return;
		}
		try {
			if (!request.getParameterMap().containsKey("userId")) {
				response.setStatus(500);
				return;
			}
			String hashtag = request.getParameter("userId");//userId is basically hashtag
			long latestTime = System.currentTimeMillis();
			long startTime = 0;
			if (request.getParameterMap().containsKey("latestTime")) {
				latestTime = Long.parseLong(request.getParameter("latestTime"));
			}
			if (request.getParameterMap().containsKey("startTime")) {
				startTime = Long.parseLong(request.getParameter("startTime"));
			}
			Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
			HttpSession httpSession = request.getSession(false);
			System.out.println((Long)httpSession.getAttribute("userId") + "  " + loggedInUser);			
			if (!httpSession.getAttribute("userId").equals(loggedInUser)) {
				System.out.println("In here to redirect");
				response.setStatus(401);
				return;
			}

			if (loggedInUser != null) {
				response.setStatus(404);
			}
			List<Tweet> tweetsForHashtag = services.tweet.TweetsWithHashtag.getTweetsOfHashtags(hashtag, startTime, latestTime);
			JSONObject tweets = CreateJSONResponseTweets.jsonResponseTweet(tweetsForHashtag , loggedInUser);
			
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
