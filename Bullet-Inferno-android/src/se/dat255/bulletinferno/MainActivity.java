package se.dat255.bulletinferno;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.controller.MasterController;
import se.dat255.bulletinferno.util.userconnectivity.Leaderboard;
import se.dat255.bulletinferno.util.userconnectivity.LeaderboardEntry;
import se.dat255.bulletinferno.util.userconnectivity.UserConnectable;
import se.dat255.bulletinferno.util.userconnectivity.UserConnectableListener;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

/**
 * Main entry for the Android deployment
 * Game development should be done in the "Bullet-Inferno" project.
 * Here all the menu screens should be made.
 * @author Sebastian Blomberg, Marc Jamot
 */
public class MainActivity extends AndroidApplication implements UserConnectable, GameHelperListener{
	private GameHelper gameHelper;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        gameHelper = new GameHelper(this,1);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useGL20 = true;
        cfg.useWakelock = true;
        cfg.getTouchEventsForLiveWallpaper = false;
        cfg.hideStatusBar = true;
        
        initialize(new MasterController(this), cfg);
        gameHelper.setup(this);
    }

	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public boolean isLoggedOn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void login() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void logout() {
		gameHelper.signOut();
	}
	
	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
		
	}

	@Override
	public List<LeaderboardEntry> getLeaderboardEntries(Leaderboard leaderboard, int limit) {
		Leaderboards.LoadScoresResult res = Games.Leaderboards.loadTopScores(
												gameHelper.getApiClient(), 
												leaderboard.getId(), 
												LeaderboardVariant.TIME_SPAN_ALL_TIME, 
												LeaderboardVariant.COLLECTION_PUBLIC, 
												limit).await();
		
		LeaderboardScoreBuffer buffer = res.getScores();
		List<LeaderboardEntry> result = new ArrayList<LeaderboardEntry>();
		
		for(LeaderboardScore score : buffer) {
			result.add(new LeaderboardEntry(score.getScoreHolderDisplayName(), score.getRawScore(), 
												score.getRank()));
		}

		
		return result;
	}

	@Override
	public void getLeaderboardEntriesAsync(UserConnectableListener<List<LeaderboardEntry>> listener,
			Leaderboard leaderboard, int limit) {
		Games.Leaderboards.loadTopScores(gameHelper.getApiClient(), leaderboard.getId(), 
				LeaderboardVariant.TIME_SPAN_ALL_TIME, 
				LeaderboardVariant.COLLECTION_PUBLIC, 
				limit).setResultCallback(new LeaderboardResultListAdapter(listener));
	}
	
	private class LeaderboardResultListAdapter extends ArrayList<LeaderboardEntry> implements ResultCallback<LoadScoresResult> {
		private static final long serialVersionUID = -9116006803604091757L;
		private UserConnectableListener<List<LeaderboardEntry>> listener;
		public LeaderboardResultListAdapter(UserConnectableListener<List<LeaderboardEntry>> listener) {
			this.listener = listener;
		}
		
		@Override
		public void onResult(LoadScoresResult res) {
			LeaderboardScoreBuffer buffer = res.getScores();
			
			for(LeaderboardScore score : buffer) {
				add(new LeaderboardEntry(score.getScoreHolderDisplayName(), score.getRawScore(), 
													score.getRank()));
			}
			listener.onDoneLoading(this);
		}
		
	}
}