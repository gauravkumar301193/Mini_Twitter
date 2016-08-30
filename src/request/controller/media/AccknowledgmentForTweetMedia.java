package request.controller.media;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.media.UpdateImageInfo;

/**
 * Servlet implementation class AccknowledgmentForTweetMedia
 */
@WebServlet("/AccknowledgmentForTweetMedia")
public class AccknowledgmentForTweetMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		Long userId = null;
		Long mediaId = null;
		if (request.getParameterMap().containsKey("id")) 
			userId = Long.parseLong(request.getParameter("id"));
		if (request.getParameterMap().containsKey("mediaId")) 
			mediaId = Long.parseLong(request.getParameter("mediaId"));
		
		try {
			
			UpdateImageInfo.insertIntoTweet(userId , mediaId);
			response.setStatus(200);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			response.setStatus(503);
			e.printStackTrace();
		}
		
	}

}
