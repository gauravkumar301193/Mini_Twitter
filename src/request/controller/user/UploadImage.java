package request.controller.user;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import query.database.QueryMedia;
import query.database.UpdateMedia;

/**
 * Servlet implementation class UploadDemoImage
 */
@WebServlet("/UploadImage")
public class UploadImage extends HttpServlet {
	
	public static final String path = "/Users/gaurav.kum/Desktop/media/";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		if(ServletFileUpload.isMultipartContent(request)) {
    		System.out.println("I am here1");
            try {
    			long mediaId = QueryMedia.generateMediIdFromDb();
                List<FileItem> multiparts = new ServletFileUpload(
                                         new DiskFileItemFactory()).parseRequest(request);
        		System.out.println("I am he2 " + mediaId);
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                		System.out.println("I am here2.67");
                        String name = new File(item.getName()).getName();
                        item.write( new File(path + mediaId + ".png"));
                        System.out.println(path + mediaId + ".png");
                        UpdateMedia.insertIntoMediaDb(mediaId);
                    }
                }
        		System.out.println("I am here3 " + mediaId);

               //File uploaded successfully
               request.setAttribute("message", "File Uploaded Successfully");
               response.setContentType("text/html");
               response.getWriter().write(mediaId + "");
       		System.out.println("I am here4");
            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }          
         
        }else{
            request.setAttribute("message",
                                 "Sorry this Servlet only handles file upload request");
        }
     }
}