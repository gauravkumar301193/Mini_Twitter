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
	public void testCheckUserExists() {
		try {
		
			assertTrue(QueryUser.checkUserExists(17));
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckUserExistsWhenUserNotPresent() {
		try {
		
			assertFalse(QueryUser.checkUserExists(1));
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testGetLastLogout() {
		try {
			long time = QueryUser.getLastLogout(913378);
			assertEquals(Long.parseLong("1428109857056") ,time);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetUserEmail() {
		try {
			assertEquals("004Nadleeh@mail.com" ,QueryUser.getUserEmail(913378));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testGetFollowersCount() {
		try {
			assertEquals(6 ,QueryUser.getFollowersCount(17));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFollowingCount() {
		try {
			assertTrue(QueryUser.getFollowingCount(17) > 0);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetUserID() {
		try {
			assertEquals(17 ,QueryUser.getUserID("ChloeS"));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetUserPasswordLong() {
		try {
			assertEquals("99200d237b07d6471431fd3f2973b53e" ,QueryUser.getUserPassword(17));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetUserPasswordString() {
		try {
			assertEquals("99200d237b07d6471431fd3f2973b53e" ,QueryUser.getUserPassword("ChloeS@mail.com"));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetUserDetailsFromDb() {
		try {
			User user = QueryUser.getUserDetailsFromDb(2);
			assertEquals("RealRonHoward" , user.getHandle());
			assertEquals(6 , user.getFollower());
			assertEquals(2 , user.getFollowing());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllFollowers() {
		try {
			List<User> users = QueryUser.getAllFollowers(2);
			List<User> users1 = QueryUser.getAllFollowers(10);
			assertEquals(6 , users.size() );
			assertEquals(6 ,  users1.size());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllFollowing() {
		try {
			List<User> users = QueryUser.getAllFollowing(2);
			List<User> users1 = QueryUser.getAllFollowing(10);
			assertEquals(2 , users.size());
			assertEquals(3 ,  users1.size());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllMentionsForTweet() {
		try {
			List<Long> mentions = QueryUser.getAllMentionsForTweet(7);
			assertEquals(1 , mentions.size());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetRetweetUsersAfterLogout() {
		try {
			List<RetweetModel> retweetsCount = QueryUser.getRetweetUsersAfterLogout(15, 0);
			assertEquals(1 , retweetsCount.size());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckEmailExists() {
		try {
			assertTrue(QueryUser.checkEmailExists("mayank2@mail.com"));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckHandleExists() {
		try {
			assertTrue(QueryUser.checkHandleExists("mayank2"));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testIsConnection() {
		try {
			assertTrue(QueryUser.isConnection(1834729, 2));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllUsersWithNameStartingWith() {
		try {
			List<User> users = QueryUser.getAllUsersWithNameStartingWith("ab");
			assertEquals(5, users.size());
			assertEquals("ab@mail.com" , users.get(0).getEmail());
			assertEquals("ab03@mail.com",users.get(1).getEmail());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetUserHandle() {
		try {
			String handle = QueryUser.getUserHandle(17);
			assertEquals("ChloeS" , handle);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
