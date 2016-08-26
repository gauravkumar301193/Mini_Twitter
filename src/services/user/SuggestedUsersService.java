package services.user;

import java.sql.SQLException;
import java.util.List;

import models.Tweet;
import models.User;
import query.database.QueryUser;

public class SuggestedUsersService {


	public static List<User> getAllUsersForUsername(String usernameLike) throws ClassNotFoundException, SQLException {
		
		return QueryUser.getAllUsersWithNameStartingWith(usernameLike);
	}

}
