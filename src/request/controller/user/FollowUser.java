package request.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import response.util.CheckValidity;
import services.user.CreateConnection;

/**
 * Input: handle of user to be followed and the follower's handle.
 * Output: none
 */

/**
 * Servlet implementation class FollowUser
 */
@WebServlet("/FollowUser")
public class FollowUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(FollowUser.class); 
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String follower = "";
		String userToFollow = "";
		response.addHeader("Access-Control-Allow-Origin", "*");
		if(request.getParameterMap().containsKey("userId")) {
		 userToFollow = request.getParameter("userId");
		}
		else {
			logger.error("follower empty");
			return;
		}
		if(request.getParameterMap().containsKey("loggedInUser")) {
		 follower = request.getParameter("loggedInUser");
		}
		else {
			logger.error("user to follow empty");
			return;
		}
		//response.getWriter().print("hello from user");
		long userId = Long.parseLong(userToFollow);
		long loggedInUser = Long.parseLong(follower);
		
		boolean status = false;
		
		try {
			if(CheckValidity.isValidUser(userId) && CheckValidity.isValidUser(loggedInUser)) {
			status = CreateConnection.followUser(loggedInUser, userId);
			response.setContentType("text/html");
			if(status) {
				response.setStatus(200);
			}
			else {
				response.setStatus(503);
			}
		} 
		}catch (ClassNotFoundException | SQLException e) {
			
			logger.error("SQl excetion occurred: " + e.getStackTrace());
			response.setStatus(503);
			
		}
		
		
		
		
	}

}
