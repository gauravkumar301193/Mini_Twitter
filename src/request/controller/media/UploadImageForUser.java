package request.controller.media;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;

import models.User;
import services.media.MediaIdGenerator;
import services.media.UpdateImageInfo;

/**
 * Servlet implementation class UploadImageForUser
 */
@WebServlet("/UploadImageForUser")
public class UploadImageForUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMG_PATH = "/Users/gaurav.kum/Desktop/Media/";
	private static final Logger logger = Logger.getLogger(UploadImageForTweet.class);
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long mediaId = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		if(ServletFileUpload.isMultipartContent(request)) {
		try {
			mediaId = MediaIdGenerator.generateMediaId();
			UpdateImageInfo.insertIntoMedia(mediaId);
			logger.info(mediaId);
			logger.info("uploading image to " + IMG_PATH + mediaId);
			List<FileItem> multiparts = new ServletFileUpload(
                                 new DiskFileItemFactory()).parseRequest(request);
			for(FileItem item : multiparts) {
				logger.info("uploading");
				if(!item.isFormField()){
					System.out.println("in here yes..!");
					String name = new File(item.getName()).getName();
					item.write( new File(IMG_PATH + mediaId + ".png"));
				}
			}
			response.setStatus(200);
//			response.getWriter().write(mediaId + "");
		} catch (Exception ex) {
			response.setStatus(503);
			request.setAttribute("message", "File Upload Failed due to " + ex);
		}          
 
		}else{
			response.setStatus(503);
			request.setAttribute("message",
                         "Sorry this Servlet only handles file upload request");
		}
	}

}
