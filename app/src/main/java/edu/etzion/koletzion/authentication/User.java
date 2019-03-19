package edu.etzion.koletzion.authentication;

public class User {
	private static final User ourInstance = new User();
	private String email, name, password;
	
	public static User getInstance() {
		return ourInstance;
	}
	
	private User() {
	}
	
	void setEmail(String email) {
		this.email = email;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	void setPassword(String password) {
		this.password = password;
	}
	
	void setCredentials(String email, String name, String password){
		this.email = email;
		this.name = name;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return name;
	}
	
}
