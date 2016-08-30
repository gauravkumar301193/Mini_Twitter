package services.media;

import java.sql.SQLException;

import query.database.QueryMedia;

public class MediaIdGenerator {

	private static Long mediaId = null ;
	
	public static Long generateMediaId() throws ClassNotFoundException, SQLException {
		mediaId = QueryMedia.generateMediIdFromDb() + 1;
		return mediaId;
	}
}
