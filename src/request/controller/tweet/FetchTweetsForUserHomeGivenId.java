package request.controller.tweet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import models.Tweet;
import javax.servlet.http.HttpServletResponse;
import services.tweet.*;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import response.util.CheckValidity;
import response.util.CreateJSONResponseTweets;

/**
 * @author mayank.ra
 */

/**
 * Servlet implementation class TweetsForHome
 */
@WebServlet("/FetchTweetsForUserHomeGivenId")
public class FetchTweetsForUserHomeGivenId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = Logger.getLogger(FetchTweetsForUserHomeGivenId.class);
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.addHeader("Access-Control-Allow-Origin", "*");
			long userId = Long.parseLong(request.getParameter("userId"));
			Long latestTime = null;
			long startTime = 0;
			if (request.getParameterMap().containsKey("latestTime")) {
					latestTime = Long.parseLong(request.getParameter("latestTime"));
			}
			else {
					latestTime = System.currentTimeMillis();
			}
			if (request.getParameterMap().containsKey("startTime")) {
				startTime = Long.parseLong(request.getParameter("startTime"));
			}
			else {
				startTime = 0;
			}
			Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
		
			if (CheckValidity.isValidUser(userId)) {
				List<Tweet> listOfTweets = new ArrayList<>();
				listOfTweets = services.tweet.TweetsForUserHome.getTweetsForUserHome(userId, startTime, latestTime);
				
				logger.info("Tweets received ");
				
				JSONObject jsonObject = CreateJSONResponseTweets.jsonResponseTweet(listOfTweets ,loggedInUser);
				logger.info(jsonObject.toString());
				response.setContentType("application/json");
				
				response.setStatus(200);
				response.getWriter().write(jsonObject.toString());
			}
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log
		}
	}
}
