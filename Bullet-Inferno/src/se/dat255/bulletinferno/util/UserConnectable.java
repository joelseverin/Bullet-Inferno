package se.dat255.bulletinferno.util;

/**
 * An intermediary for handling the functionality for everything user related, that depend on a 
 * specific platform. For example the intermediary could be used to handle Google Play Services 
 * connectivity.
 * 
 * @author Sebastian Blomberg
 *
 */
public interface UserConnectable {
	/**
	 * Returns whether the user is logged on.
	 * @return loggedon
	 */
	public boolean isLoggedOn();
	
	/**
	 * Requests the user to login. The prompt is dependent on platform.
	 */
	public void login();
	
	/**
	 * Logout the user
	 */
	public void logout();
}
