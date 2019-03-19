package edu.etzion.koletzion.authentication;

public class User {
	private static final User ourInstance = new User();
	
	public static User getInstance() {
		return ourInstance;
	}
	
	private User() {
	}
}
