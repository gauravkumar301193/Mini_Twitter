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

import models.User;
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

	static Logger logger = Logger.getLogger(RegisterUser.class); 
       
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
		String email = "";
		
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
		 email = request.getParameter("emailId");
		}
		else {
			logger.error("emailId empty");
			return;
		}
		
		User user = new User();
		
		user.setEmail(email);
		user.setHandle(handle);
		user.setPassword(password);
		user.setUserName(userName);
		user.setUserId(User.generateUserID());
		user.setLogout(0);
		
		boolean status = false;
		
		try {
			status = AddNewUser.addNewUser(user);
			response.setContentType("text/html");
			if(status) {
				response.getWriter().write("User Added Successfully");
			}
			else {
				response.getWriter().write("User not added");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("SQl excetion occurred: " + e.getStackTrace());
			
		}
		 
		
	}

}
