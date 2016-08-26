package request.controller.tweet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import models.Tweet;
import response.util.CreateJSONResponseTweets;

/**
 * Input: String hashtag
 * Output: JSON object of all tweets
 */

/**
 * Servlet implementation class TweetsWithHash
 */
@WebServlet("/TweetsWithHashtag")
public class TweetsWithHashtag extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (!request.getParameterMap().containsKey("hashtag")) {
				response.setStatus(500);
				return;
			}
			String hashtag = request.getParameter("hashtag");
			long latestTime = System.currentTimeMillis();
			long startTime = 0;
			if (request.getParameterMap().containsKey("latest_time")) {
				latestTime = Long.parseLong(request.getParameter("latest_time"));
			}
			if (request.getParameterMap().containsKey("start_time")) {
				startTime = Long.parseLong(request.getParameter("start_time"));
			}
			List<Tweet> tweetsForHashtag = services.tweet.TweetsWithHashtag.getTweetsOfHashtags(hashtag, startTime, latestTime);
			JSONObject tweets = CreateJSONResponseTweets.jsonResponseTweet(tweetsForHashtag , null);
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
