package edu.etzion.koletzion.authentication;

import edu.etzion.koletzion.models.Profile;

public class User {
	private static final User ourInstance = new User();
	private String email, password;
	private Profile profile;
	private long timeStamp;
	
	public static User getInstance() {
		return ourInstance;
	}
	//todo YAIR delete that class , make sure that i can get username from firebase
	private User() {
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	private User setProfile() {
		ourInstance.profile = getProfileFromServer();
		return ourInstance;
	}
	
	private Profile getProfileFromServer() {
		return null;
	}
	
	public void setCredentials(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	void setPassword(String password) {
		this.password = password;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getTimeStamp() {
		return timeStamp;
	}
}
