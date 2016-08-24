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
import query.database.QueryTweet;
import response.util.CheckValidity;
import services.tweet.PostRetweet;

/**
 * Servlet implementation class Retweet
 */
@WebServlet("/Retweet")
public class Retweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(Retweet.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Retweet() {
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
		String tweet_id = "";
		String user_id = "";
		if(request.getParameterMap().containsKey("tweetId")) {
		 tweet_id = request.getParameter("tweetId");
		}
		else {
			logger.error("tweet Id empty");
			return;
		}
		
		if(request.getParameterMap().containsKey("userId")) {
		 user_id = request.getParameter("userId");
		}
		else {
			logger.error("user Id empty");
			return;
		}
		long tweetId = Long.parseLong(tweet_id);
		long userId = Long.parseLong(user_id);
		
		boolean status = false;
		Tweet tweet;
		
		try {
			if(CheckValidity.isValidTweet(tweetId) && CheckValidity.isValidUser(userId)) {
			
			status = PostRetweet.retweetPost(userId, tweetId);
			response.setContentType("text/html");
			
			if(status) {
				response.getWriter().write("Post Retweeted");
			}
			else {
				response.getWriter().write("Try again");
			}
			
		} 
		}catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQl excetion occurred: " + e.getStackTrace());
		}
		
		
		
	}

}
