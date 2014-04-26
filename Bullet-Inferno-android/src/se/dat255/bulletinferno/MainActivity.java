package se.dat255.bulletinferno;

import se.dat255.bulletinferno.controller.MasterController;
import se.dat255.bulletinferno.util.UserConnectable;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

/**
 * Main entry for the Android deployment
 * Game development should be done in the "Bullet-Inferno" project.
 * Here all the menu screens should be made.
 * @author Marc Jamot
 * @version 1.0
 * @since 2013-09-12
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
}