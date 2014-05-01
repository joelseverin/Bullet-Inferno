package se.dat255.bulletinferno.util.userconnectivity;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public enum LeaderboardImpl implements Leaderboard, UserConnectableListener<List<LeaderboardEntry>> {
	HIGHSCORE("CgkI5M20-JYXEAIQAQ", "Highscore"),
	LONGEST_RUN("CgkI5M20-JYXEAIQAQ", "Longest run"),
	COIN_SCORE("CgkI5M20-JYXEAIQAQ", "Collected coins");
	
	private final String id, name;
	private final Queue<ListenerQueueEntry> queue = new PriorityQueue<ListenerQueueEntry>();
	
	private class ListenerQueueEntry implements Comparable<ListenerQueueEntry> {
		private LeaderboardListener listener;
		private int limit;
		@Override
		public int compareTo(ListenerQueueEntry o) {
			if(limit == o.limit) {
				return 0;
			} else if(limit > o.limit) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
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
		return uc.getLeaderboardEntries(this, limit);
	}

	@Override
	public void getEntriesAsync(LeaderboardListener listener, UserConnectable uc, int limit) {
		uc.getLeaderboardEntriesAsync(this, this, limit);
		ListenerQueueEntry l = new ListenerQueueEntry();
		l.listener = listener;
		l.limit = limit;
		queue.add(l);
	}

	@Override
	public boolean insertScore(UserConnectable uc, int score) {
		return uc.insertLeaderboardEntry(this, score);
	}

	@Override
	public void onDoneLoading(List<LeaderboardEntry> result) {
		while(!queue.isEmpty() &&  result.size() <= queue.peek().limit) {
			queue.remove().listener.onLeaderboardLoded(this, result);
		}
	}
}
