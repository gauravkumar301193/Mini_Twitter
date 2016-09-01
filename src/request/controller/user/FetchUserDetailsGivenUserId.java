package request.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import models.User;
import query.database.QueryUser;
import response.util.CreateJSONResponseUsers;
import services.user.FetchUserDetails;
import services.user.GetAllFollowers;

/**
 * Servlet implementation class UserDetailsGivenUserId
 */
@WebServlet("/FetchUserDetailsGivenUserId")
public class FetchUserDetailsGivenUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(FetchUserDetailsGivenUserId.class);   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		if(request.getSession(false) == null) {
			response.setStatus(504);
			return;
		}
		try {
			Long userId = null;
			Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
			if (!request.getParameter("userId").equals("")) {
				userId = Long.parseLong(request.getParameter("userId"));
				logger.info("userID :" + userId);
			}
			User user = null;
			if(userId == null){
				String handle = request.getParameter("handle");
				userId = QueryUser.getUserID(handle);
				if (userId == null) {
					response.setStatus(404);
					return;
				}
			}
			user= FetchUserDetails.getUserDetails(userId);			
			JSONObject userDetails = CreateJSONResponseUsers.jsonResponseOfSingleUser(user, loggedInUser);
			logger.info("Request has been served");
			response.setContentType("application/json");
			response.setStatus(200);
			logger.info(userDetails.toString());
			response.getWriter().write(userDetails.toString());
			
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log the error statement
		}
	}

}
