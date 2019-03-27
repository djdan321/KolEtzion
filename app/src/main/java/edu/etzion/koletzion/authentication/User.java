package edu.etzion.koletzion.authentication;

import edu.etzion.koletzion.models.Profile;

public class User {
	private static final User ourInstance = new User();
	private String email, firstName, lastName, password;
	private Profile profile;
	
	public static User getInstance() {
		return ourInstance;
	}
	
	private User() {
		profile = new Profile(firstName, lastName);
	}
	
	public Profile getProfile(){
		return profile;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCredentials(String email, String firstName, String lastName, String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
}
