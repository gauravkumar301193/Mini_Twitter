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

/**
 * Servlet implementation class FetchImageGivenuserId
 */
@WebServlet("/FetchImageGivenuserId")
public class FetchImageGivenuserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FetchImageGivenuserId.class);
	private static final String IMG_PATH = "/Users/gaurav.kum/Desktop/Media/";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchImageGivenuserId() {
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
		
		String type = "user";
		Long mediaId = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		if(request.getParameterMap().containsKey("mediaId")) {
			mediaId = Long.parseLong(request.getParameter("mediaId"));
		}
		else {
			logger.error("media Id empty");
			return;
		}
		
		try {
//			String imgName = request.getParameter("image");ssss
			response.setContentType("image/png");
			File f = new File(IMG_PATH + type + mediaId + ".png");
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
