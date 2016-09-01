package queryDatabase;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import query.database.QueryMedia;
import services.media.MediaIdGenerator;

public class QueryMediaTest {

	@Test
	public void testGenerateMediIdFromDb() throws ClassNotFoundException, SQLException {
		assertTrue(QueryMedia.generateMediIdFromDb() > 0);
	}

	@Test
	public void testGetMediaId() throws NumberFormatException, ClassNotFoundException, SQLException {
		assertTrue(QueryMedia.getMediaId(Long.parseLong("1"))==0);
	}

}
