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
import org.json.simple.JSONObject;

import models.User;
import query.database.QueryUser;
import response.util.CreateJSONResponseUsers;
import services.user.AddNewUser;

/**
 * Input: User details
 * output: Status (successful/ unsuccessful)
 */

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RegisterUser.class); 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUser() {
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
		
		String userName = "";
		String handle = "";
		String password = "";
		String emailId = "";
		
		if(request.getParameterMap().containsKey("userName")) {
			userName = request.getParameter("userName");
		}
		else {
			logger.error("userName empty");
			return;
		}
		if(request.getParameterMap().containsKey("handle")) {
			handle = request.getParameter("handle");
		}
		else {
			logger.error("handle empty");
			return;
		}
		if(request.getParameterMap().containsKey("password")) {
			password = request.getParameter("password");
		}
		else {
			logger.error("password empty");
			return;
		}
		
		if(request.getParameterMap().containsKey("emailId")) {
			emailId = request.getParameter("emailId");
		}
		else {
			logger.error("emailId empty");
			return;
		}
		
		User user = new User();
		
		user.setEmail(emailId);
		user.setHandle(handle);
		user.setPassword(password);
		user.setUserName(userName);
		try {
			user.setUserId(User.generateUserID());
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			logger.error("can't generate userId");
			e1.printStackTrace();
			return;
		}
		user.setLogout(null);
		user.setTweetCount(0);
		user.setFollower(0);
		user.setFollowing(0);
		boolean status = false;
		try {
			response.addHeader("Access-Control-Allow-Origin", "*");
			
			
			status = AddNewUser.addNewUser(user);
			response.setContentType("text/html");
			if(status) {
				JSONObject jsonObject = CreateJSONResponseUsers.jsonResponseOfSingleUser(user , user.getUserId());
				response.setStatus(200);
				logger.info(jsonObject.toString());
				response.setContentType("application/json");
				response.getWriter().write(jsonObject.toString());				response.setContentType("text/html");
//				response.getWriter().write("adasd");
				logger.info("user added");
				return;
			}
			else {
				response.setStatus(404);
				logger.info("user not added");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("SQl excetion occurred: " + e.getStackTrace());			
		}
		 
		
	}

}
