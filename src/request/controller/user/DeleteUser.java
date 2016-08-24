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

import request.controller.session.AuthenticateUser;
import response.util.CheckValidity;
import services.user.RemoveUser;

/**
 * Input: userhandle
 * Output: deletion status (successful/ unsuccessful)
 * Query repository 
 */

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(DeleteUser.class); 
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
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
//		doGet(request, response);
		
		String user = "";
		if(request.getParameterMap().containsKey("userId")) {
		user = request.getParameter("userId");
		}
		else {
			logger.error("user id empty");
			return;
		}
		long userId =  Long.parseLong(user); 
		
		
		boolean status = false;
		
		try {
			if(userId > 0 && CheckValidity.isValidUser(userId)) {
			
				status = RemoveUser.deleteUser(userId);
				
				response.setContentType("text/html");
				
				if(status) {
					response.getWriter().write("User Successfully Removed");
				}
				else {
					response.getWriter().write("Sorry User Not Present");
				}
			
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQl excetion occurred: " + e.getStackTrace());
		}
		
		
	}

}
