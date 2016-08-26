package request.controller.user;
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
import services.tweet.GetTweetsForUserProfile;
import services.user.GetAllTweetsForUserIdService;

/**
 * Servlet implementation class AllTweetsForUserId
 */
@WebServlet("/AllTweetsForUserId")
public class AllTweetsForUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllTweetsForUserId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			if (!request.getParameterMap().containsKey("userId")) {
				
				response.setStatus(500);
				return;
			}
			
			
			long userId = Long.parseLong(request.getParameter("userId"));
			long latestTime = System.currentTimeMillis();
			long startTime = 0;
			if (request.getParameterMap().containsKey("latestTime")) {
				latestTime = Long.parseLong(request.getParameter("latestTime"));
			}
			if (request.getParameterMap().containsKey("startTime")) {
				startTime = Long.parseLong(request.getParameter("startTime"));
			}
			
			//response.getWriter().write("hello here");
			List<Tweet> allTweetsForUser = GetAllTweetsForUserIdService.allTweetsForUserId(userId, startTime, latestTime);
			
			//System.out.println(allTweetsForUser.size());
			JSONObject tweets = CreateJSONResponseTweets.jsonResponseTweet(allTweetsForUser, userId);
			response.setContentType("application/json");
			response.getWriter().write(tweets.toString());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			e.printStackTrace();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
