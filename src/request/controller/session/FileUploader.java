package request.controller.session;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import models.Media;
import models.MediaType;
import services.media.UploadPicture;


/**
 * Servlet implementation class FileUploader
 */


@WebServlet("/FileUploader")
public class FileUploader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "/Users/gaurav.kum/Documents/MediaUpload";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploader() {
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
		
		String path = "";
		String name = "";
		String type = "";
		if(ServletFileUpload.isMultipartContent(request)) {
            try {
                	List<FileItem> multiparts = new ServletFileUpload(
                			new DiskFileItemFactory()).parseRequest(request);
              
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                         name = new File(item.getName()).getName();
                        
                        item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                        
                         path = UPLOAD_DIRECTORY + File.separator + name ;
                         type = item.getContentType();
                        
                       
                        
                        
                    }
                }
                
                Media media = new Media();
                media.setMediaId(media.generateMediaId());
                media.setMediaPath(path);
                media.setMediaType(MediaType.IMAGE);
                if(UploadPicture.uploadMedia(media)) {
                	request.setAttribute("message", "File Uploaded Successfully");
                    
                }
                } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }          
         
        }else{
            request.setAttribute("message" , "Sorry this Servlet only handles file upload request");
        }  
    		
	}

}
