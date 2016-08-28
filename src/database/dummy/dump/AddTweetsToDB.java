package database.dummy.dump;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import query.database.QueryUser;

/**
 * @author mayank.ra
 */

public class AddTweetsToDB {
	
	Statement statement;
	BufferedReader tweetBuffer;
	PrintWriter extraTweets;
	
	public AddTweetsToDB() throws IOException {
		extraTweets = new PrintWriter(new BufferedWriter(new FileWriter("extraTTweets.txt", true)));
	}
	
	public void addFile(String pathOfFile) throws ClassNotFoundException, SQLException {
		String filePath = pathOfFile;
		try {
			tweetBuffer = new BufferedReader(new FileReader(filePath));
//			statement = SQLConnection.getExecutableStatement();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private long getRandomTimeStamp() {
		long current = System.currentTimeMillis();
		return (long) (current * Math.random());
	}

	public void addTweetsToDB() {
		int tweetId = 1;
		String tweet = "";
		List<Integer> mentions;
		List<Integer> hashtags;
		List<Long> mentionIds;
		String[] allWordsInTweet;
		try  {
			
			
			tweet = tweetBuffer.readLine();
			while (tweet != null) {
				TweetParser tp = new TweetParser(tweet);
				tp.parseTweet();
				
				String userHandle = tp.getUserName();
				String tweetText = tp.getTweetText();
				long timestamp = getRandomTimeStamp();
				long userId = QueryUser.getUserID(userHandle);

				allWordsInTweet = tp.listToArrayOfWords();
				hashtags = tp.getHashtags();
				mentions = tp.getMentions();
				mentionIds = getIdOfMentions(allWordsInTweet, mentions);
				try {
					insertIntoTweets(timestamp, tweetText, tweetId, userHandle, userId);
					insertIntoMentions(mentionIds, tweetId, timestamp);
					insertIntoHashtags(hashtags, tweetId, allWordsInTweet);
				} catch (SQLException e) {
					extraTweets.println(tweet);
				}
				tweetId++;
				tweet = tweetBuffer.readLine();
			}
			extraTweets.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void insertIntoTweets(long timestamp, String tweet, int tweetId, String userHandle, long userId) 
			throws SQLException, ClassNotFoundException {
		StringBuilder query1 = new StringBuilder("insert into tweets(tweet_id, user_id, tweet_text, created_at, handle)");
		query1.append(" values (").append(tweetId).append(" , ").append(userId).append(", \"")
		.append(tweet).append("\",").append(timestamp).append(",\"").append(userHandle).append("\")");
		
		SQLConnection.executeUpdate(query1.toString());
//		SQLConnection.db.updateDb(query1.toString());
	}
	
	public void insertIntoMentions(List<Long> mentionIds, int tweetId, long timestamp)
			throws SQLException, ClassNotFoundException {
		Iterator<Long> iter = mentionIds.iterator();
		while(iter.hasNext()) {
			StringBuilder query = new StringBuilder("insert into tweet_mentions values(")
					.append(tweetId).append(",").append(iter.next()).append(",").append(timestamp).append(")");
			
			SQLConnection.executeUpdate(query.toString());
//			SQLConnection.db.updateDb(query.toString());
		}
	}
	
	public void insertIntoHashtags(List<Integer> hashtags, int tweetId, String[] tweet)
			throws SQLException, ClassNotFoundException {
		Iterator<Integer> iter = hashtags.iterator();

		while(iter.hasNext()) {
			String hashtag = tweet[iter.next()];
			hashtag = hashtag.substring(1, hashtag.length());
			StringBuilder query = new StringBuilder("insert into hashtags values(\"").append(hashtag)
					.append("\",").append(tweetId).append(")");
			
			SQLConnection.executeUpdate(query.toString());
//			SQLConnection.db.updateDb(query.toString());
		}
	}
	
	public List<Long> getIdOfMentions(String[] tweet, List<Integer> mentions) 
			throws ClassNotFoundException, SQLException {
		List<Long> mentionIds = new ArrayList<>();
		Iterator<Integer> iter = mentions.iterator();
		while (iter.hasNext()) {
			String handle = tweet[iter.next()];
			handle = handle.substring(1, handle.length());
			Long id = QueryUser.getUserID(handle);
			mentionIds.add(id);
		}
		return mentionIds;
	}

	public static void main(String[] args) {
		try {
			AddTweetsToDB obj = new AddTweetsToDB();
			obj.addFile("tweets.txt");
			obj.addTweetsToDB();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
