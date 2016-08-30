package request.controller.media;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class FetchImageGivenUserId
 */
@WebServlet("/FetchImageGivenTweetId")
public class FetchImageGivenTweetId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMG_PATH = "/Users/gaurav.kum/Desktop/Media/";
	private static final Logger logger = Logger.getLogger(FetchImageGivenTweetId.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Long mediaId = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		if(request.getParameterMap().containsKey("mediaId")) {
			mediaId = Long.parseLong(request.getParameter("mediaId"));
		}
		
		try {
			logger.info("Sending media information for id = " + mediaId);
			response.setContentType("image/png");
			File f = new File(IMG_PATH + mediaId + ".png");
			BufferedImage bi ;
			if(f.exists() && !f.isDirectory()) { 
				 bi = ImageIO.read(f);
			}
			else {
				f = new File(IMG_PATH + 0 + ".png");
				bi = ImageIO.read(f);
			}
			OutputStream out = response.getOutputStream();
			ImageIO.write(bi, "png", out);
			out.close();
		} catch (Exception e) {
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
