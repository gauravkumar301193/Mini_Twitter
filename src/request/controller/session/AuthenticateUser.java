package request.controller.session;

import org.apache.log4j.Logger;
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
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailId = "";
		String password = "";
		System.out.println("here");
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
			if (CheckUserCredentials.checkIfUserExists(emailId, password)) {
				logger.info("Authentication success");
				HttpSession session = request.getSession(true);
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.setStatus(200);
				session.setAttribute("email_id", emailId);
				session.setAttribute("password", password);
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("SQl excetion occurred: " + e.getStackTrace());
		}
	}

}
