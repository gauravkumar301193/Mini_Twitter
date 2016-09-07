package request.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
@WebServlet("/DeleteAUserGivenId")
public class DeleteAUserGivenId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(DeleteAUserGivenId.class); 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		response.addHeader("Access-Control-Allow-Origin", "*");
		String user = "";
		if(request.getSession(false) == null) {
			response.setStatus(504);
			return;
		}
		if(request.getParameterMap().containsKey("userId")) {
			user = request.getParameter("userId");
			HttpSession httpSession = request.getSession(false);
			System.out.println((Long)httpSession.getAttribute("userId") + "  " + user);			
			if (!httpSession.getAttribute("userId").equals(user)) {
				System.out.println("In here to redirect");
				response.setStatus(401);
				return;
			}

		}
		else {
			logger.error("user id empty");
			return;
		}
		long userId =  Long.parseLong(user); 
		
		
		boolean status = false;
		
		try {
			if (userId > 0 && CheckValidity.isValidUser(userId)) {
			
				status = RemoveUser.deleteUser(userId);
				
				response.setContentType("text/html");
				
				if(status) {
					response.setStatus(200);
				}
				else {
					response.setStatus(503);
					response.getWriter().write("Sorry User Not Present");
				}
			
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQl excetion occurred: " + e.getStackTrace());
		}
		
		
	}

}
