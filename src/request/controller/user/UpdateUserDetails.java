package request.controller.user;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.user.UpdateUserService;

/**
 * Servlet implementation class UpdateUserDetails
 */
@WebServlet("/UpdateUserDetails")
public class UpdateUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	Logger logger = Logger.getLogger(UpdateUserDetails.class);
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Long userId = null;
		boolean updatePassword = false , updateUsername = false , updateEmail = false;
		if(request.getSession(false) == null) {
			response.setStatus(504);
			return;
		}
		try {
			if(request.getParameterMap().containsKey("userId")) {
				userId = Long.parseLong(request.getParameter("userId"));
				logger.info("received userId");
			}
			
			if(request.getParameter("username")!="") {
				String username = request.getParameter("username");
				logger.info("received username");
				updateUsername = UpdateUserService.updateUserName(username, userId);
			}
			
			if(request.getParameter("emailId")!= "") {
				String email = request.getParameter("emailId");
				logger.info("received emailId");
				updateEmail = UpdateUserService.updateEmail(email, userId);
			}
			
			if(request.getParameter("password") != "") {
				String password = request.getParameter("password");
				logger.info("received password");
				updatePassword = UpdateUserService.updatePassword(password, userId);
			}		
			response.setStatus(200);
		} catch (Exception e) {
			response.setStatus(504);
		}
	}

}
