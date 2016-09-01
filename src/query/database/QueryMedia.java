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

	public static Long generateMediIdFromDb() throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select max(media_id) from media");
		ResultSet rs = SQLConnection.executeQuery(query.toString());
		if(rs.next()) {
			return rs.getLong(1) + 1;
		}
		return null;
	}

	public static Long getMediaId(Long userId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("select media_id from user_details where user_id = ")
				.append(userId);
		ResultSet rs = SQLConnection.executeQuery(query.toString());
		if (rs.next()) {
			return rs.getLong("media_id");
		}
		return new Long(0);
	}	
}
