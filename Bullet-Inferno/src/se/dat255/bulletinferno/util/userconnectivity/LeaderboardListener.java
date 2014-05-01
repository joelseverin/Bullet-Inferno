package se.dat255.bulletinferno.util.userconnectivity;

import java.util.List;

public interface LeaderboardListener {
	public void onLeaderboardLoded(Leaderboard source, List<LeaderboardEntry> entries);
}
