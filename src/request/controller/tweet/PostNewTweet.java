package request.controller.tweet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import models.Tweet;
import models.User;
import query.database.QueryUser;
import request.controller.session.AuthenticateUser;
import response.util.CheckValidity;
import services.tweet.NewTweet;
import services.user.AddNewUser;

// Remember to handle Media

/**
 * Servlet implementation class PostTweet
 */
@WebServlet("/PostNewTweet")
public class PostNewTweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(PostNewTweet.class); 
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostNewTweet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.addHeader("Access-Control-Allow-Origin", "*");
		String tweetText = "";
		long userId = 0;
		if (request.getParameterMap().containsKey("tweetText")) {
		 tweetText = request.getParameter("tweetText");
		}
		else {
			logger.error("tweet text empty");
			return;
		}
		
		if(request.getParameterMap().containsKey("userId")) {
		 userId = Long.parseLong(request.getParameter("userId"));
		}
		else {
			logger.error("user id empty");
			return;
		}
		
		Tweet tweet = null;
		try {
			if(CheckValidity.isValidUser(userId)) {
				
				tweet = new Tweet();
				tweet.setLikeCount(0);
				tweet.setTweetText(tweetText);
				tweet.setTimestamp(System.currentTimeMillis());
				tweet.setTweetId(tweet.generateTweetID());
				tweet.setUserId(userId);
				tweet.setMediaId(null);
				tweet.setHandle(QueryUser.getUserHandle(userId));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			logger.error("SQl excetion occurred: " + e1.getStackTrace());
		}
		response.setContentType("text/html");
		
		boolean status = false;
		
		try {
			status = NewTweet.postTweet(tweet);
			//System.out.println("hre");
			response.setContentType("text/html");
			if(status) {
				response.setStatus(200);
				response.getWriter().write(tweet.getTweetId() + "");
			}
			else {
				response.setStatus(404);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQl excetion occurred: " + e.getStackTrace());
		}
		 
		
	}

}
