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
@WebServlet("/FetchNotificationsGivenUserId")
public class FetchNotificationsGivenUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FetchNotificationsGivenUserId.class);
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			long userId = Long.parseLong(request.getParameter("userId"));
			List<RetweetModel> retweetsAfterLogout = RetweetUsersAfterLogout.getRetweetUsersAfterLogout(userId);
			List<Tweet> mentionsAfterLogout = GetMentionsAfterLogout.getMentionsAfterLogout(userId);
			List<User> followersAfterLogout = GetFollowersAfterLogout.getFollowersAfterLogout(userId);
			
			CreateJSONResponseNotifications jsonResponse = new CreateJSONResponseNotifications();
			
			jsonResponse.addRetweetsAfterLogout(retweetsAfterLogout);
			logger.info("json response after logout");
			jsonResponse.addMentionsAfterLogout(mentionsAfterLogout);
			logger.info("json response after mentions");
			jsonResponse.addFollowersAfterLogout(followersAfterLogout);
			logger.info("json response after followers");
			
			logger.info(jsonResponse.getJsonObject().toString());
			
			response.setStatus(200);
			response.getWriter().write(jsonResponse.getJsonObject().toString());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log the error statement
		}
	}

}
