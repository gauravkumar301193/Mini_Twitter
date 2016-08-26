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
import request.controller.session.AuthenticateUser;
import response.util.CheckValidity;
import services.tweet.LikeTweetService;
import services.user.RemoveUser;

/**
 * Input: tweet_id, user handle
 * Ouput: none
 * Like repository
 */

/**
 * Servlet implementation class LikeTweet
 */
@WebServlet("/LikeTweet")
public class LikeTweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(LikeTweet.class); 
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeTweet() {
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
		String tweetLiked = "";
		String likedBy = "";
		
		if (request.getParameterMap().containsKey("tweetId")) {
		 tweetLiked = request.getParameter("tweetId");
		}
		else {
			logger.error("tweet id empty");
			return;
		}
		
		if (request.getParameterMap().containsKey("tweetId")) {
		 likedBy = request.getParameter("userId");
		}
		else {
			logger.error("user id empty");
			return;
		}
		
	
		long tweetId =  Long.parseLong(tweetLiked); 
		long likesByUserId =  Long.parseLong(likedBy); 
		Tweet tweet;
		boolean status = false;
		try {
			
			
			status = LikeTweetService.likeTweet(likesByUserId, tweetId );
			response.setContentType("text/html");
			
			if(status) {
				response.getWriter().write("Tweet Liked");
			}
			else {
					response.getWriter().write("Tweet can't be liked");
				}
			}
			 catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				logger.error("SQl excetion occurred: " + e.getStackTrace());
			}
		
		
	}

}
