package queryDatabase;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import models.RetweetModel;
import models.Tweet;
import models.User;
import query.database.QueryTweet;
import query.database.QueryUser;

public class QueryUserTest {

	@Test
	public void testCheckUserExists() throws ClassNotFoundException, SQLException {
			assertTrue(QueryUser.checkUserExists(1));
	}
	
	@Test
	public void testCheckUserExistsWhenUserNotPresent() throws ClassNotFoundException, SQLException {
			assertFalse(QueryUser.checkUserExists(0));
	}


	@Test
	public void testGetLastLogout() throws ClassNotFoundException, SQLException {
			long time = QueryUser.getLastLogout(1);
			assertEquals(0,time);
	}

	@Test
	public void testGetUserEmail() throws ClassNotFoundException, SQLException {
			assertEquals("abc@mail.com" ,QueryUser.getUserEmail(1));
	}


	@Test
	public void testGetUserID() throws ClassNotFoundException, SQLException {
			assertEquals(2 ,QueryUser.getUserID("abcd"));
	}

	@Test
	public void testGetUserPasswordLong() throws ClassNotFoundException, SQLException{
			assertEquals("abc" ,QueryUser.getUserPassword(1));
	}

	@Test
	public void testGetUserPasswordString() throws ClassNotFoundException, SQLException {
			assertEquals("abcd" ,QueryUser.getUserPassword("abcd@mail.com"));
	}

	@Test
	public void testGetUserDetailsFromDb() throws ClassNotFoundException, SQLException {
			User user = QueryUser.getUserDetailsFromDb(2);
			assertEquals("abcd" , user.getHandle());
			
	}

	@Test
	public void testGetAllFollowers() throws ClassNotFoundException, SQLException {
			List<User> users = QueryUser.getAllFollowers(1);
			assertEquals(1 , users.size());
	}

	@Test
	public void testGetAllFollowing() throws ClassNotFoundException, SQLException {
			List<User> users = QueryUser.getAllFollowing(2);
			assertEquals(1 , users.size());
	}

	@Test
	public void testGetAllMentionsForTweet() throws ClassNotFoundException, SQLException {
			List<Long> mentions = QueryUser.getAllMentionsForTweet(1);
			assertEquals(0 , mentions.size());
	}

	@Test
	public void testGetRetweetUsersAfterLogout() throws ClassNotFoundException, SQLException {
			List<RetweetModel> retweetsCount = QueryUser.getRetweetUsersAfterLogout(15, 0);
			assertEquals(0 , retweetsCount.size());
	}

	@Test
	public void testCheckEmailExists() throws ClassNotFoundException, SQLException {
			assertTrue(QueryUser.checkEmailExists("abc@mail.com"));
	}

	@Test
	public void testCheckHandleExists() throws ClassNotFoundException, SQLException {
			assertTrue(QueryUser.checkHandleExists("abcd"));
	}

	@Test
	public void testIsConnection() throws ClassNotFoundException, SQLException {
			assertTrue(QueryUser.isConnection(2, 1));
	}

	@Test
	public void testGetAllUsersWithNameStartingWith() throws ClassNotFoundException, SQLException {
			List<User> users = QueryUser.getAllUsersWithNameStartingWith("ab");
			assertEquals(2, users.size());
	}

	@Test
	public void testGetUserHandle() throws ClassNotFoundException, SQLException {
			String handle = QueryUser.getUserHandle(2);
			assertEquals("abcd" , handle);
	}

}
