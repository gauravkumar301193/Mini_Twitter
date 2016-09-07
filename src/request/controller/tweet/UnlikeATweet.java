package request.controller.tweet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import models.Tweet;
import services.tweet.LikeTweetService;
import services.tweet.UnlikeTweetService;

/**
 * Servlet implementation class UnlikeATweet
 */
@WebServlet("/UnlikeATweet")
public class UnlikeATweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(UnlikeATweet.class);
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String tweetLiked = "";
		String unLikedBy = "";
		response.addHeader("Access-Control-Allow-Origin", "*");
		if(request.getSession(false) == null) {
			response.setStatus(504);
			return;
		}
		if (request.getParameterMap().containsKey("tweetId")) {
		 tweetLiked = request.getParameter("tweetId");
		}
		else {
			logger.error("tweet id empty");
			return;
		}
		
		if (request.getParameterMap().containsKey("userId")) {
			unLikedBy = request.getParameter("userId");
		}
		else {
			logger.error("user id empty");
			return;
		}
		
	
		long tweetId =  Long.parseLong(tweetLiked); 
		long unlikeByUserId =  Long.parseLong(unLikedBy); 
		HttpSession httpSession = request.getSession(false);
		System.out.println((Long)httpSession.getAttribute("userId") + "  " + unlikeByUserId);			
		if (!httpSession.getAttribute("userId").equals(unlikeByUserId)) {
			System.out.println("In here to redirect");
			response.setStatus(401);
			return;
		}

		boolean status = false;
		try {
			status = UnlikeTweetService.unlikeTweet(unlikeByUserId, tweetId );
			response.setContentType("text/html");
			if(status) {		
				response.setStatus(200);
			}
			else {
					response.setStatus(503);
				}
			}
			 catch (ClassNotFoundException | SQLException e) {
				logger.error("SQl excetion occurred: " + e.getStackTrace());
			}

	}

}
