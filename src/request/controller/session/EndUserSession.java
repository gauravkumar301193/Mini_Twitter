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

		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpSession session = request.getSession(false);
		if (request.getParameter("userId") == null) {
			request.getSession().invalidate();
			response.setStatus(200);
			return;
		}
				
		try {
			Long userId = Long.parseLong(request.getParameter("userId"));
			HttpSession httpSession = request.getSession(false);
			System.out.println((Long)httpSession.getAttribute("userId") + "  " + userId);			
			if (!httpSession.getAttribute("userId").equals(userId)) {
				System.out.println("In here to redirect");
				response.setStatus(401);
				return;
			}
			QueryUser.setLogoutAfterSignout(userId);

			if (session != null) {
				response.setStatus(200);
			    session.invalidate();			    
			}
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(503);
			e.printStackTrace();
			return;
		}
		
	}

}
