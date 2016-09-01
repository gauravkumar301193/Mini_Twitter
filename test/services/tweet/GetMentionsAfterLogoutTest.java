package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;

public class GetMentionsAfterLogoutTest {
	
	@Test
	public void testGetMentionsAfterLogout() throws ClassNotFoundException, SQLException {
			List<Tweet> mentions = GetMentionsAfterLogout.getMentionsAfterLogout(1);
			assertEquals(0, mentions.size());
	}
	
	@Test
	public void testGetMentionsAfterLogoutWhenuserNotPresent() throws ClassNotFoundException, SQLException {
			List<Tweet> mentions = GetMentionsAfterLogout.getMentionsAfterLogout(-12);
			assertEquals(null, mentions);
	}

}
