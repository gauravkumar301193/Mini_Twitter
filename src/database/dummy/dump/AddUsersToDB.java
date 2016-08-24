package database.dummy.dump;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;
import models.User;

/**
 * @author mayank.ra
 */

public class AddUsersToDB {
	private String filePath;
	private BufferedReader fileReader;
	private Statement statement;
	
	public AddUsersToDB(String filePath) {
		this.filePath = filePath;
		try {
			fileReader = new BufferedReader(new FileReader(this.filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not connect to the file");
			e.printStackTrace();
		}
	}
	
	
	public void processFile() throws ClassNotFoundException, NoSuchAlgorithmException {
		Integer user_id = 1;
		String user;
		try {
			user = fileReader.readLine();
			while (user != null) {
				try {
						String query = "insert into Authentication values(" +
						"\"" + user + "@mail.com\"," +				// Email
						"\"" + user + "\"," + 						// Name
						"\"" + user + "\"," +						// Handle
						user_id + "," +								// User_ID
						"\"" + User.encryptPassword(user) + "\")"; 	// Password
						// TODO to be logged
						user_id++;
						int added = SQLConnection.executeUpdate(query);
//							System.out.println("added");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user = fileReader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchAlgorithmException {
		AddUsersToDB addUsersToDB = new AddUsersToDB("usernames.txt");
		addUsersToDB.processFile();
	}	
}
