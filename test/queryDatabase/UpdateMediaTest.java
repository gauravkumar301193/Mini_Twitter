package queryDatabase;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import query.database.UpdateMedia;
import services.media.MediaIdGenerator;

public class UpdateMediaTest {
	@Test
	public void insertIntoMediaTableTest() throws ClassNotFoundException, SQLException {
		assertEquals(1, UpdateMedia.insertIntoMediaDb(MediaIdGenerator.generateMediaId()));
		UpdateMedia.deleteFromMedia(MediaIdGenerator.generateMediaId() - 1);
	}
}
