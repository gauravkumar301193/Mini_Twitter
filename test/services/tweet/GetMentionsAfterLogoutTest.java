package services.tweet;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.Tweet;

public class GetMentionsAfterLogoutTest {
	
	@Test
	public void testGetMentionsAfterLogout() {
		try {
			List<Tweet> mentions = GetMentionsAfterLogout.getMentionsAfterLogout(2622766);
			assertEquals(0, mentions.size());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
