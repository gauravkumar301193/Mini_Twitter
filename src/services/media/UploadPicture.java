package services.media;

import java.sql.SQLException;

import models.Media;
import query.database.UpdateMedia;

/**
 * Return the media ID after uploading the image
 * @author mayank.ra
 *
 */
public class UploadPicture {

	public static boolean uploadMedia(Media media) throws ClassNotFoundException, SQLException {
		
		if(media.getMediaId() > 0) {
			 if(UpdateMedia.addMediaToDB(media)) {
				 return true;
			 }
			 else {
				 return false;
			 }
		}
		return false;
	}

}
