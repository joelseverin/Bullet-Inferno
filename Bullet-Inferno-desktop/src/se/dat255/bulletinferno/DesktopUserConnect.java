package se.dat255.bulletinferno;

import java.util.List;

import se.dat255.bulletinferno.util.userconnectivity.Leaderboard;
import se.dat255.bulletinferno.util.userconnectivity.LeaderboardEntry;
import se.dat255.bulletinferno.util.userconnectivity.UserConnectable;
import se.dat255.bulletinferno.util.userconnectivity.UserConnectableListener;

public class DesktopUserConnect implements UserConnectable {
	@Override
	public boolean isLoggedOn() {
		return false;
	}

	@Override
	public void login() {
	}

	@Override
	public void logout() {
	}

	@Override
	public List<LeaderboardEntry> getLeaderboardEntries(
			Leaderboard leaderboard, int limit) {
		return null;
	}

	@Override
	public void getLeaderboardEntriesAsync(
			UserConnectableListener<List<LeaderboardEntry>> listener,
			Leaderboard leaderboard, int limit) {
	}

	@Override
	public boolean insertLeaderboardEntry(Leaderboard leaderboard, int score) {
		return false;
	}
}
