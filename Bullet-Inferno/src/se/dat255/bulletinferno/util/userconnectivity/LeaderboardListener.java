package se.dat255.bulletinferno.util.userconnectivity;

import java.util.List;

public interface LeaderboardListener {
	public void onLeaderboardLoded(List<LeaderboardEntry> entries);
}
