package se.dat255.bulletinferno.util.userconnectivity;

import java.util.List;

public interface Leaderboard {
	/**
	 * Returns the ID of the leaderboard
	 * @return ID
	 */
	public String getId();
	
	/**
	 * Returns the name of the leaderboard
	 * @return name
	 */
	public String getName();
	
	/**
	 * Returns the specified amount of leaderboard entries, taken from the top of the board
	 * @param uc
	 * @param limit The maximum amount of entries
	 * @return entries
	 */
	public List<LeaderboardEntry> getEntries(UserConnectable uc, int limit);
	
	/**
	 * Loads the specified amount of leaderboard entries, taken from the top of the board, 
	 * asynchronously and deliver the results through a callback to the specified listener, when
	 * done loading.
	 * @param listener The listener that gets the callback when done loading
	 * @param uc
	 * @param limit The maximum amount of entries
	 */
	public void getEntriesAsync(LeaderboardListener listener, UserConnectable uc, 
			int limit);
	
	
	/**
	 * Inserts a score into this leaderboard. If a higher score by the same player
	 * already exist this score will be disregarded.
	 * @pre Requires {@link UserConnectable#isLoggedOn()} to be true
	 * @return Returns true if the score was inserted, false if it was disregarded
	 */
	public boolean insertScore(UserConnectable uc, int score);
}
