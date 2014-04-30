package se.dat255.bulletinferno.util.userconnectivity;

import java.util.List;

public enum LeaderboardImpl implements Leaderboard {
	HIGHSCORE("CgkI5M20-JYXEAIQAQ", "Highscore"),
	LONGEST_RUN("CgkI5M20-JYXEAIQAQ", "Longest run"),
	COIN_SCORE("CgkI5M20-JYXEAIQAQ", "Collected coins");
	
	private final String id, name;
	
	private LeaderboardImpl(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<LeaderboardEntry> getEntries(UserConnectable uc, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getEntriesAsync(LeaderboardListener listener, UserConnectable uc, int limit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean insertScore(UserConnectable uc, int score) {
		// TODO Auto-generated method stub
		return false;
	}
}
