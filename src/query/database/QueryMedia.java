package query.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import database.dummy.dump.SQLConnection;
import models.MediaType;
import models.Media;

/**
  @author gaurav.kum
 */

public class QueryMedia {
	
	static Logger logger = Logger.getLogger(QueryMedia.class);
	
	public Media getMedia(int media_id) throws SQLException, ClassNotFoundException {
		String sql = "select * from media where media_id = " + media_id ;
		
		logger.info("executing sql query: " + sql.toString());
		ResultSet rs = SQLConnection.executeQuery(sql);
		
		Media media = new Media();
		if(rs.next()) {
			media.setMediaPath(rs.getString(3));
			media.setMediaType(MediaType.valueOf(rs.getString(2)));
			media.setMediaId(media_id);
			return media;
		}
		return null;
	}

	public static Long generateMediIdFromDb() throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select max(media_id) from media");
		ResultSet rs = SQLConnection.executeQuery(query.toString());
		if(rs.next()) {
			return rs.getLong(1);
		}
		return null;
	}
	
}
