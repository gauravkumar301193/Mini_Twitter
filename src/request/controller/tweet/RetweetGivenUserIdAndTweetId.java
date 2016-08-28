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
@WebServlet("/RetweetGivenUserIdAndTweetId")
public class RetweetGivenUserIdAndTweetId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RetweetGivenUserIdAndTweetId.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetweetGivenUserIdAndTweetId() {
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
		long tweetId = 0 ;
		String loggedInUserHandle = "" ;
		long authorId = 0;
		if(request.getParameterMap().containsKey("tweetId")) {
		 tweetId = Long.parseLong(request.getParameter("tweetId"));
		}
		else {
			logger.error("tweet Id empty");
			return;
		}
		
		if(request.getParameterMap().containsKey("loggedInUserHandle")) {
			loggedInUserHandle = request.getParameter("loggedInUserHandle");
		}
		else {
			logger.error("loggedInUserHandle empty");
			return;
		}
		if(request.getParameterMap().containsKey("authorId")) {
			 authorId = Long.parseLong(request.getParameter("authorId"));
			}
		else {
				logger.error("authorId empty");
				return;
			}
		
		Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
		if (loggedInUser != null) {
			response.setStatus(404);
		}
		
		
		boolean status = false;
		try {
			if(CheckValidity.isValidTweet(tweetId)) {
			status = PostRetweet.retweetPost(loggedInUserHandle, tweetId, authorId, loggedInUser);
			response.setContentType("text/html");
			
			if(status) {
				response.setStatus(200);
				logger.info("Retweeted successfully");
			}
			else {
				response.setStatus(503);
			}
			
		} 
		}catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.error("SQl excetion occurred: " + e.getStackTrace());
		}
		
		
		
	}

}
