package request.controller.tweet;

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
import response.util.CreateJSONResponseHashtags;
import response.util.CreateJSONResponseUsers;
import services.tweet.GetHashtags;
import services.user.GetAllFollowers;

/**
 * Servlet implementation class FetchFiveMostUsedHashtags
 */
@WebServlet("/FetchFiveMostUsedHashtags")
public class FetchFiveMostUsedHashtags extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchFiveMostUsedHashtags() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			List<String> trendingHashtags = GetHashtags.getTrendingHashtags();
			
			Long loggedInUserId = (Long) request.getSession().getAttribute("userId");
			if (loggedInUserId != null) {
				response.setStatus(404);
			}
			JSONObject jsonObjectOfHashtags = CreateJSONResponseHashtags.jsonResponseHashtags(trendingHashtags);
			
			response.setStatus(200);
			response.getWriter().write(jsonObjectOfHashtags.toString());
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			response.setStatus(500);
			// TODO log the error statement
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
