package models;
/**
 * @author gaurav.kum
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private String userName = "";
    private String email = "";
    public String handle = "";
    public String password = "";
    public long userId;
    public long logout;
    
    public long getLogout() {
		return logout;
	}

	public void setLogout(long logout) {
		this.logout = logout;
	}

	private static long userIdGenerator = 9821980;
    
    public int following = 0;
    public int follower = 0;
    

    public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public static String encryptPassword(String password) throws NoSuchAlgorithmException {    	
    	MessageDigest m = MessageDigest.getInstance("MD5");
    	m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        
        return hashtext;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public void setUserId(long userId) {
    	this.userId = userId;
    }
    public long getUserId() {
    	return userId;
    }
    
    public static long generateUserID() {
    	 userIdGenerator++;
    	 return userIdGenerator;
    }
}

