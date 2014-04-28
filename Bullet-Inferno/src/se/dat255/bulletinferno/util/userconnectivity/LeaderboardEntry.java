package se.dat255.bulletinferno.util.userconnectivity;

public class LeaderboardEntry {
	private String name;
	private long score, rank;
	
	public LeaderboardEntry(String name, long score, long rank) {
		this.name = name;
		this.score = score;
		this.rank = rank;
	}
	
	public String getName() {
		return name;
	}
	
	public long getScore() {
		return score;
	}
	
	public long getRank() {
		return rank;
	}
}
