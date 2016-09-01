package services.user;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;

public class GetAllTweetsForUserIdServiceTest {

	@Test
	public void testAllTweetsForUserId() throws NumberFormatException, ClassNotFoundException, SQLException {
		List<Tweet> twts = GetAllTweetsForUserIdService.allTweetsForUserId(1, 0, Long.parseLong("1472020664186"));
		assertEquals(6 , twts.size());
		
	}

}
