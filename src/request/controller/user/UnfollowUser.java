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
import services.user.DeleteConnection;

/**
 * Input: handle of both the users
 * Output: none
 */

/**
 * Servlet implementation class UnfollowUser
 */
@WebServlet("/UnfollowUser")
public class UnfollowUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UnfollowUser.class);

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		Long userId = null;
		Long loggedInUser = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		if(request.getParameterMap().containsKey("userId")) {
			userId = Long.parseLong(request.getParameter("userId"));
		}
		else {
			response.setStatus(503);
			return;
		}
		
		if(request.getParameterMap().containsKey("loggedInUser")) {
			loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
		}
		else {
			response.setStatus(503);
			return;
		}
		boolean status = false;
		try {
			if(CheckValidity.isValidUser(userId) && CheckValidity.isValidUser(loggedInUser))
			{
				status = DeleteConnection.unfollowUser(loggedInUser, userId);
				response.setContentType("text/html");
				if(status) {
					response.setStatus(200);
				}
				else {
					response.setStatus(503);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			logger.error("SQl excetion occurred: " + e.getStackTrace());			
		}
		
		
	}

}
