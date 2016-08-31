package request.controller.session;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import models.User;
import query.database.QueryUser;
import response.util.CreateJSONResponseUsers;
import services.user.CheckUserCredentials;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginAuthentication
 */
@WebServlet("/AuthenticateUser")
public class AuthenticateUser extends HttpServlet {
	
    static Logger logger = Logger.getLogger(AuthenticateUser.class); 
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailId = "";
		String password = "";
		if (request.getParameterMap().containsKey("emailId")) {
			emailId = request.getParameter("emailId");
		} else {
			logger.error("email id empty");
			return;
		}
		
		if (request.getParameterMap().containsKey("password")) {
			password = request.getParameter("password");
		} else {
			logger.error("password empty");
			return;
		}
		try {
			
			Long userId = CheckUserCredentials.checkIfUserExists(emailId, password);
			if (userId != null) {
				logger.info("Authentication success");
				//QueryUser.setLogoutAfterLogin(userId);
				User user = QueryUser.getUserDetailsFromDb(userId);
				
				JSONObject jsonObject = CreateJSONResponseUsers.jsonResponseOfSingleUser(user, userId);
				HttpSession session = request.getSession(true);
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.setStatus(200);
				
				logger.info(jsonObject.toString());
				session.setAttribute("email_id", emailId);
				session.setAttribute("password", password);
				session.setAttribute("userId", userId);
				response.setContentType("application/json");
				response.getWriter().write(jsonObject.toString());
			}
		} catch (Exception e) {
			logger.error("SQl excetion occurred: " + e.getStackTrace());
			response.setStatus(503);
		}
	}

}
