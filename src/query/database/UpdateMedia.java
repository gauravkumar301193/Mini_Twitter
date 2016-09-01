package query.database;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import database.dummy.dump.SQLConnection;
import models.*;

public class UpdateMedia {
	
	static Logger logger = Logger.getLogger(UpdateMedia.class);
	public static int insertIntoMediaDb(Long mediaId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("insert into media values(").append(mediaId).append(",") 
				.append("\"image\"").append(")");
		
		logger.info("executing sql query: " + query.toString());
		
		return SQLConnection.executeUpdate(query.toString());
	}
	
	public static int deleteFromMedia(Long mediaId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("delete from media where media_id = ").append(mediaId);
		return SQLConnection.executeUpdate(query.toString());
		
	}
	
}
