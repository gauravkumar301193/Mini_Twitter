package request.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import models.User;
import response.util.CreateJSONResponseUsers;
import services.user.GetAllFollowers;
import services.user.GetAllFollowingUsers;

/**
 * @author mayank.ra
 */

/**
 * Servlet implementation class FollowingUsers
 */
@WebServlet("/FetchFollowingGivenUserId")
public class FetchFollowingGivenUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) == null) {
			response.setStatus(504);
			return;
		}
		try {
			long userId = Long.parseLong(request.getParameter("userId"));
			List<User> followersList = GetAllFollowingUsers.getAllFollowingUsers(userId);
			Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
			HttpSession httpSession = request.getSession(false);
			System.out.println((Long)httpSession.getAttribute("userId") + "  " + loggedInUser);			
			if (!httpSession.getAttribute("userId").equals(loggedInUser)) {
				System.out.println("In here to redirect");
				response.setStatus(401);
				return;
			}

			if (loggedInUser != null) {
				response.setStatus(404);
			}
			
			JSONObject jsonObjectOfFollowers = CreateJSONResponseUsers.jsonResponseUsers(followersList, loggedInUser);
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setStatus(200);
			response.getWriter().write(jsonObjectOfFollowers.toString());
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log the error statement
		}
		
	}

}
