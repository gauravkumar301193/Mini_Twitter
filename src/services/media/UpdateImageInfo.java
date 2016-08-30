package services.media;

import java.sql.SQLException;

import models.Media;
import query.database.UpdateMedia;
import query.database.UpdateTweet;
import query.database.UpdateUser;

/**
 * Return the media ID after uploading the image
 * @author mayank.ra
 *
 */
public class UpdateImageInfo {

	public static void insertIntoMedia(Long mediaId) throws ClassNotFoundException, SQLException {
		UpdateMedia.insertIntoMediaDb(mediaId);
		
	}

	public static void insertIntoUser(Long userId, Long mediaId) throws ClassNotFoundException, SQLException {
		UpdateUser.insertIntoUserDb(userId, mediaId);
		
	}

	public static void insertIntoTweet(Long tweetId, Long mediaId) throws ClassNotFoundException, SQLException {
		UpdateTweet.insertIntoTweetDb(tweetId, mediaId);
		
	}

	

}
