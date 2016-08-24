package query.database;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import database.dummy.dump.SQLConnection;
import models.*;

public class UpdateMedia {
	private Statement statement;
	
	static Logger logger = Logger.getLogger(UpdateMedia.class);
	public static boolean addMediaToDB(Media media) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder("insert into media values(").append(media.getMediaId()).append(",") 
		.append(SqlQuerySeparators.DOUBLEQUOTE).append(media.getMediaType().getType()).append(SqlQuerySeparators.DOUBLEQUOTE)
		.append(",").append(SqlQuerySeparators.DOUBLEQUOTE).append(media.getMediaPath()).append(SqlQuerySeparators.DOUBLEQUOTE).append(")");
		
		logger.info("executing sql query: " + query.toString());
		
		return SQLConnection.executeUpdate(query.toString()) > 0;
	}
	
}
