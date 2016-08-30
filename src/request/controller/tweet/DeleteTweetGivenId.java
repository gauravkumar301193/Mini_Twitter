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

import request.controller.session.AuthenticateUser;
import response.util.CheckValidity;
import services.tweet.RemoveTweet;
import services.user.RemoveUser;

/**
 * Servlet implementation class DeleteTweet
 */
@WebServlet("/DeleteTweetGivenId")
public class DeleteTweetGivenId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(DeleteTweetGivenId.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteTweetGivenId() {
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
		
		if (request.getParameterMap().containsKey("tweetId")) {
			String tweet = request.getParameter("tweetId");
		
		long tweetId =  Long.parseLong(tweet); 
		
		try {
			
			response.addHeader("Access-Control-Allow-Origin", "*");
			if(CheckValidity.isValidTweet(tweetId)) {
			logger.info("deleting tweet");	
				response.setContentType("text/html");
				if( RemoveTweet.deleteTweet(tweetId)) {
					response.setStatus(200);
//					response.getWriter().write("Tweet Successfully Removed");
				}
				else {
					response.setStatus(404);
//					response.getWriter().write("Tweet Not Present");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQl excetion occurred: " + e.getStackTrace());
		}
		}
		else {
			logger.error("email id empty");
			return;
		}
		
	}

}
