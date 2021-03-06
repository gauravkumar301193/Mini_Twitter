package request.controller.tweet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import models.Tweet;
import query.database.QueryTweet;
import response.util.CreateJSONResponseNotifications;
import response.util.CreateJSONResponseTweets;
/**
 * @author mayank.ra
 */

@WebServlet("/GetTweetGivenId")
public class GetTweetGivenId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getSession(false) == null) {
			response.setStatus(504);
			return;
		}
		try {
			long tweetId = Long.parseLong(request.getParameter("tweetId"));
			Tweet tweet = QueryTweet.getTweetByTweetId(tweetId);
			Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
			HttpSession httpSession = request.getSession(false);
			System.out.println((Long)httpSession.getAttribute("userId") + "  " + loggedInUser);			
			if (!httpSession.getAttribute("userId").equals(loggedInUser)) {
				System.out.println("In here to redirect");
				response.setStatus(401);
				return;
			}

			List<Tweet> twts = new ArrayList<>() ;
			twts.add(tweet);
			System.out.println(twts.size());
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setStatus(200);
			JSONObject obj = CreateJSONResponseTweets.jsonResponseTweet(twts, loggedInUser);
			response.setContentType("application/json");
			response.getWriter().write(obj.toString());
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log the result
		}
		
//		response.getWriter().print("hello");
	}

}
