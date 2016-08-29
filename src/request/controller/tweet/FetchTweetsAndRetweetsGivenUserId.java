package request.controller.tweet;
/**
 * @author gaurav.kum
 */
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
import services.tweet.GetTweetsForUserProfileTest;
import services.user.GetAllTweetsForUserIdService;

/**
 * Servlet implementation class AllTweetsForUserId
 */
@WebServlet("/FetchTweetsAndRetweetsGivenUserId")
public class FetchTweetsAndRetweetsGivenUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchTweetsAndRetweetsGivenUserId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			if (!request.getParameterMap().containsKey("userId")) {
				
				response.setStatus(500);
				return;
			}
			
			
			long userId = Long.parseLong(request.getParameter("userId"));
			long latestTime = System.currentTimeMillis();
			long startTime = 0;
			if (request.getParameterMap().containsKey("startTime")) {
				startTime = Long.parseLong(request.getParameter("startTime"));
			}

			Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
			if (loggedInUser != null) {
				response.setStatus(404);
			}
			//response.getWriter().write("hello here");
			List<Tweet> allTweetsForUser = GetAllTweetsForUserIdService.allTweetsForUserId(userId, startTime, latestTime);
			
			//System.out.println(allTweetsForUser.size());
			JSONObject tweets = CreateJSONResponseTweets.jsonResponseTweet(allTweetsForUser, loggedInUser);
			response.setContentType("application/json");
			
		
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
