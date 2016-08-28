package request.controller.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import query.database.QueryUser;
/**
 * @author mayank.ra
 */

/**
 * Servlet implementation class UserEmailExists
 */
@WebServlet("/CheckEmailExists")
public class CheckEmailExists extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(CheckEmailExists.class);
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //		response.getWriter().append("Served at: ").append(request.getContextPath());
        String email = request.getParameter("emailId");
        response.setContentType("text/html");
        try {
        	response.addHeader("Access-Control-Allow-Origin", "*");
			response.setStatus(200);
            if (QueryUser.checkEmailExists(email)) {
                response.getWriter().write("true");
            } else {
                response.getWriter().write("false");
            }
            logger.info("response sent");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            response.setStatus(503);
            e.printStackTrace();
        }
    }
}
