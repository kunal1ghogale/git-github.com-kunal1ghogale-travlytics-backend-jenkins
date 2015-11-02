package com.travlytics.model;

/**
 * @author kghogale
 *
 */
public class UserAccount {
	private String username;
	private String password;
	private String name;
	private String facebookToken;
	
	
	/**
	 * @param username
	 * @param password
	 * @param name
	 */
	public UserAccount(String username, String password, String name, String facebookToken) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.facebookToken = facebookToken;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getFacebookToken() {
		return facebookToken;
	}
	public void setFacebookToken(String facebookToken) {
		this.facebookToken = facebookToken;
	}
}
