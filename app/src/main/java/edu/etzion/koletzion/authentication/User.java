package edu.etzion.koletzion.authentication;

import edu.etzion.koletzion.models.Profile;

public class User {
	private static final User ourInstance = new User();
	private String email, password;
	private Profile profile;
	
	public static User getInstance() {
		return ourInstance;
	}
	
	private User() {
//		this.setProfile(); fixme
		//todo find profile
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	private User setProfile() {
		ourInstance.profile = getProfileFromServer();
		return ourInstance;
	}
	
	private Profile getProfileFromServer() {
		//todo: yossi todo
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
}
