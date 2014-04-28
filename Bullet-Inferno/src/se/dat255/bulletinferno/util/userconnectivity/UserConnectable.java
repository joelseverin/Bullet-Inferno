package se.dat255.bulletinferno.util.userconnectivity;

import java.util.List;

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
	
	/**
	 * Returns the first leaderboard entries within the specified limit
	 * @param leaderboard
	 * @param limit
	 */
	List<LeaderboardEntry> getLeaderboardEntries(Leaderboard leaderboard, int limit);
	
	/**
	 * Loads the first leaderboard entries in the, within the specified limit, asynchronously and returns them in 
	 * a callback to the specified listener
	 * @param listener
	 * @param leaderboard
	 * @param limit
	 */
	void getLeaderboardEntriesAsync(UserConnectableListener<List<LeaderboardEntry>> listener,
			Leaderboard leaderboard, int limit);
}
