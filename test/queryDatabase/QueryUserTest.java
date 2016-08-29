package queryDatabase;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

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
			assertEquals(2 ,QueryUser.getFollowingCount(17));
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
			assertEquals(6 ,  QueryUser.getAllFollowers(2));
			assertEquals(3 ,  QueryUser.getAllFollowers(10));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllFollowing() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllMentionsForTweet() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRetweetUsersAfterLogout() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckEmailExists() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckHandleExists() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrepareUserObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFollowersAfterTimestamp() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLogoutTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsNoConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllUsersWithNameStartingWith() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateUserId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserHandle() {
		fail("Not yet implemented");
	}

}
