package request.controller.session;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import query.database.QueryUser;

/**
 * Servlet implementation class EndUserSession
 */
@WebServlet("/EndUserSession")
public class EndUserSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.addHeader("Access-Control-Allow-Origin", "*");
		Long userId = Long.parseLong(request.getParameter("userId"));
		HttpSession session = request.getSession(false);
		try {
			QueryUser.setLogoutAfterSignout(userId);
			System.out.println("set time");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (session != null) {
			response.setStatus(200);
		    session.invalidate();
		    
		}
	}

}
