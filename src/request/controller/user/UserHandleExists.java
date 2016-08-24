package request.controller.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import query.database.QueryUser;

/**
 * @author mayank.ra
 */

/**
 * Servlet implementation class UserHandleExists
 */
@WebServlet("/UserHandleExists")
public class UserHandleExists extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //		response.getWriter().append("Served at: ").append(request.getContextPath());
        String handle = request.getParameter("handle");
        response.setContentType("text/html");
        try {
            if (QueryUser.checkHandleExists(handle)) {
                response.getWriter().write("EXISTS");
            } else {
                response.getWriter().write("DOES NOT EXISTS");
            }
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            response.setStatus(503);
            e.printStackTrace();
        }
    }
    
}
