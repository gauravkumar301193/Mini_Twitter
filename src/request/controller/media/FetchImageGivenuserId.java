package request.controller.media;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import query.database.QueryMedia;

/**
 * Servlet implementation class FetchImageGivenuserId
 */
@WebServlet("/FetchImageGivenUserId")
public class FetchImageGivenUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FetchImageGivenUserId.class);
	private static final String IMG_PATH = "/Users/gaurav.kum/Desktop/Media/";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long mediaId = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		Long userId = null;
		if(request.getParameterMap().containsKey("userId")) {
			userId = Long.parseLong(request.getParameter("userId"));
		}
		if (userId == null) {
			mediaId = new Long(0);
		}
		
		
		try {
//			String imgName = request.getParameter("image");ssss
			mediaId = QueryMedia.getMediaId(userId);
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

}
