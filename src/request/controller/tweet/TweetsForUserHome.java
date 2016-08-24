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
import org.json.simple.JSONObject;

import response.util.CheckValidity;
import response.util.CreateJSONResponseTweets;

/**
 * @author mayank.ra
 */

/**
 * Servlet implementation class TweetsForHome
 */
@WebServlet("/TweetsForUserHome")
public class TweetsForUserHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long userId = Long.parseLong(request.getParameter("user_id"));
			long latestTime = System.currentTimeMillis();
			long startTime = 0;
			if (request.getParameterMap().containsKey("latestTime")) {
				latestTime = Long.parseLong(request.getParameter("latestTime"));
			}
			if (request.getParameterMap().containsKey("startTime")) {
				startTime = Long.parseLong(request.getParameter("startTime"));
			}
			if (CheckValidity.isValidUser(userId)) {
				List<Tweet> listOfTweets = new ArrayList<>();
				listOfTweets = services.tweet.TweetsForUserHome.getTweetsForUserHome(userId, startTime, latestTime);
				JSONObject jsonObject = CreateJSONResponseTweets.jsonResponseTweet(listOfTweets);
				response.setContentType("application/json");
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
