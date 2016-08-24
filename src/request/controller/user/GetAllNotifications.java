package request.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.RetweetModel;
import models.Tweet;
import models.User;
import response.util.CreateJSONResponseNotifications;
//import response.util.CreateJSONResponseNotifiactions;
import services.tweet.GetMentionsAfterLogout;
import services.tweet.RetweetUsersAfterLogout;
import services.user.GetFollowersAfterLogout;

/**
 * @author mayank.ra
 */

/**
 * Servlet implementation class GetAllNotifications
 */
@WebServlet("/GetAllNotifications")
public class GetAllNotifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			long userId = Long.parseLong(request.getParameter("user_id"));
			List<RetweetModel> retweetsAfterLogout = RetweetUsersAfterLogout.getRetweetUsersAfterLogout(userId);
			List<Tweet> mentionsAfterLogout = GetMentionsAfterLogout.getMentionsAfterLogout(userId);
			List<User> followersAfterLogout = GetFollowersAfterLogout.getFollowersAfterLogout(userId);
			
			CreateJSONResponseNotifications jsonResponse = new CreateJSONResponseNotifications();
			jsonResponse.addRetweetsAfterLogout(retweetsAfterLogout);
			jsonResponse.addMentionsAfterLogout(mentionsAfterLogout);
			jsonResponse.addFollowersAfterLogout(followersAfterLogout);
			
			response.setContentType("application/json");
			response.getWriter().write(jsonResponse.toString());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log the error statement
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
