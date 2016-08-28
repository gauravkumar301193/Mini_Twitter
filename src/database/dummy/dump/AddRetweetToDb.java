package database.dummy.dump;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import query.database.QueryUser;

public class AddRetweetToDb {
	Statement statement;
	BufferedReader retweetBuffer;
	PrintWriter extraRetweets;
	long originalAuthor;
	int authorLength = 0;
	
	public AddRetweetToDb() throws IOException {
		extraRetweets = new PrintWriter(new BufferedWriter(new FileWriter("extraReTweets.txt", true)));
	}
	
	public void addFile(String pathOfFile) throws ClassNotFoundException, SQLException {
		String filePath = pathOfFile;
		try {
			retweetBuffer = new BufferedReader(new FileReader(filePath));
//			statement = SQLConnection.getExecutableStatement();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private long getRandomTimeStamp(long tweetTime) {
		long current = System.currentTimeMillis();
		
		return tweetTime+ (long)((current - tweetTime) * Math.random());
	}

	public void addRetweetsToDB() {
		//int tweetId = 1;
		String retweet = "";
		List<Integer> mentions;
		String[] allWordsInTweet;
		try  {
			retweet = retweetBuffer.readLine();
			while (retweet != null) {
				TweetParser tp = new TweetParser(retweet);
				tp.parseTweet();
				
				String userHandle = tp.getUserName();
				
				String tweetText = tp.getTweetText();
				mentions = tp.getMentions();
				allWordsInTweet = tp.listToArrayOfWords();
				Iterator<Integer> iter = mentions.iterator();
				String originalAuthor = allWordsInTweet[iter.next()];
				originalAuthor = originalAuthor.substring(1, originalAuthor.length());
				System.out.println(originalAuthor);
				tweetText = tweetText.substring(5 + originalAuthor.length(), tweetText.length()-1);
				
				long originalAuthorId = QueryUser.getUserID(originalAuthor);

				System.out.println(tweetText);
				
				StringBuilder sql = new StringBuilder("select tweet_id, created_at from tweets where user_id = ")
						.append(originalAuthorId)
						.append(" and ")
						.append("tweet_text like ")
						.append("\'%")
						.append(tweetText)
						.append("%\'");
				System.out.println(sql.toString());
//				ResultSet rs = SQLConnection.executeQuery(sql.toString());
				ResultSet rs = SQLConnection.executeQuery(sql.toString());
				
				long tweetTime = 0, originalTweetId = 0;
				
				if(rs.next())
				{
				
				 originalTweetId = rs.getLong(1);
				 tweetTime = rs.getLong(2);
				 System.out.println(originalTweetId + "  " + tweetTime);
				}
				//long originalId = rs.getLong(1);
				
				System.out.println(originalTweetId);
				System.out.println(tweetTime);
				
				long timestamp = getRandomTimeStamp(tweetTime);
				long userId = QueryUser.getUserID(userHandle);
				
				try {
					insertIntoReTweets(timestamp, originalTweetId, originalAuthorId, userId);
				} catch (SQLException e) {
					extraRetweets.println(retweet);
				}
				
				retweet = retweetBuffer.readLine();
				break;
			}
			extraRetweets.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void insertIntoReTweets(long timestamp, long tweetId, long author, long userId) 
			throws SQLException, ClassNotFoundException {
		StringBuilder query1 = new StringBuilder("insert into retweets(tweet_id, user_id, created_at, author_id)");
		query1.append(" values (").append(tweetId).append(" , ").append(userId).append(",").append(timestamp).append(",").append(author).append(")");
		
		SQLConnection.executeUpdate(query1.toString());
	}
	

	public static void main(String[] args) {
		try {
			AddRetweetToDb obj = new AddRetweetToDb();
			obj.addFile("retweets.txt");
			obj.addRetweetsToDB();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}