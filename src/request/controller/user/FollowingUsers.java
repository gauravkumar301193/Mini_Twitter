package request.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@WebServlet("/FollowingUsers")
public class FollowingUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long userId = Long.parseLong(request.getParameter("user_id"));
			List<User> followersList = GetAllFollowingUsers.getAllFollowingUsers(userId);
			JSONObject jsonObjectOfFollowers = CreateJSONResponseUsers.jsonResponseUsers(followersList);
			response.setContentType("application/json");
			response.getWriter().write(jsonObjectOfFollowers.toString());
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log the error statement
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
