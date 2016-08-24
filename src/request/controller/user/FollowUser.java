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
     * @see HttpServlet#HttpServlet()
     */
    public FollowUser() {
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
		String follower = "";
		String userToFollow = "";
		
		if(request.getParameterMap().containsKey("follower")) {
		 follower = request.getParameter("follower");
		}
		else {
			logger.error("follower empty");
			return;
		}
		if(request.getParameterMap().containsKey("following")) {
		 userToFollow = request.getParameter("following");
		}
		else {
			logger.error("user to follow empty");
			return;
		}
		//response.getWriter().print("hello from user");
		long followerId = Long.parseLong(follower);
		long userToFollowId = Long.parseLong(userToFollow);
		
		boolean status = false;
		
		try {
			if(CheckValidity.isValidUser(followerId) && CheckValidity.isValidUser(followerId)) {
			status = CreateConnection.followUser(followerId, userToFollowId);
			response.setContentType("text/html");
			if(status) {
				response.getWriter().write("Followed Successfully");
			}
			else {
				response.getWriter().write("Can't be followed");
			}
		} 
		}catch (ClassNotFoundException | SQLException e) {
			logger.error("SQl excetion occurred: " + e.getStackTrace());
			
		}
		
		
		
		
	}

}
