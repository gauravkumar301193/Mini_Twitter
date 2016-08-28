package request.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import models.Tweet;
import models.User;
import response.util.CheckValidity;
import response.util.CreateJSONResponseTweets;
import response.util.CreateJSONResponseUsers;
import services.user.SuggestedUsersService;

/**
 * Servlet implementation class GetUserHandlesAndIDs
 */
@WebServlet("/GetBestMatchingUserGivenPattern")
public class GetBestMatchingUserGivenPattern extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBestMatchingUserGivenPattern() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String textPattern = "";
		
		try {
			if (request.getParameterMap().containsKey("textPattern")) {
				textPattern = request.getParameter("textPattern");
			}
			
			//System.out.println(usernameLike);
				List<User> listOfUsers = new ArrayList<>();
				listOfUsers = SuggestedUsersService.getAllUsersForUsername(textPattern);
				Long loggedInUser = Long.parseLong(request.getParameter("loggedInUser"));
				if (loggedInUser != null) {
					response.setStatus(404);
				}
				
				JSONObject jsonObject = CreateJSONResponseUsers.jsonResponseUsers(listOfUsers, loggedInUser);
				response.setContentType("application/json");
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.setStatus(200);
				response.getWriter().write(jsonObject.toString());
			
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
