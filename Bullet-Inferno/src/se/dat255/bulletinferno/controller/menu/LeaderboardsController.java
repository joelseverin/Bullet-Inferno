package se.dat255.bulletinferno.controller.menu;


import java.util.List;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.userconnectivity.LeaderboardEntry;
import se.dat255.bulletinferno.util.userconnectivity.LeaderboardListener;
import se.dat255.bulletinferno.util.userconnectivity.UserConnectable;
import se.dat255.bulletinferno.view.menu.LeaderboardsView;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LeaderboardsController implements SubMenuController, LeaderboardListener {
	private final LeaderboardsView view;
	
	public LeaderboardsController(Stage stage, ResourceManager resources, UserConnectable uc) {
		view = new LeaderboardsView(stage, resources);
		view.setSlideToggleListener(slideToggleListener);
		view.addHighScoreBoardButtonListener(highScoreBoardListener);
		view.addCollectedCoinsBoardButtonListener(collectedCoinsBoardListener);
		view.addLongestRunBoardButtonListener(longestRunBoardListener);
		view.showLoadingIcon();
		
	}
	
	private ChangeListener slideToggleListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			// If the event was fired upon a slide down;
			if(view.isToggledVisible()) {
				return;
			}
			
			dispose();
			if(subMenuHandler != null) {
				subMenuHandler.onDisposeFinished(LeaderboardsController.this);
				subMenuHandler = null;
			}
		}
	};
	
	private SubMenuControllHandler subMenuHandler = null;
	
	@Override
	public void disposeRequest(SubMenuControllHandler listener) {
		subMenuHandler = listener;
		disposeRequest();
	}

	@Override
	public void disposeRequest() {
		// If view is already out of the screen
		if(!view.isToggledVisible()) {
			dispose();
		} else {
			view.slideToggle();
		}
	}

	@Override
	public void dispose() {
		view.dispose();
	}

	private ClickListener highScoreBoardListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			//view.showLeaderboard(LeaderboardsView.LeaderboardType.HIGHSCORE);
		}
	};
	
	private ClickListener collectedCoinsBoardListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			//view.showLeaderboard(LeaderboardsView.LeaderboardType.COLLECTED_COINS);
		}
	};
	
	private ClickListener longestRunBoardListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			//view.showLeaderboard(LeaderboardsView.LeaderboardType.LONGEST_RUN);
		}
	};

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void render(float arg0) {}

	@Override
	public void resize(int arg0, int arg1) {}

	@Override
	public void resume() {}

	@Override
	public void show() {

	}

	@Override
	public void onLeaderboardLoded(List<LeaderboardEntry> entries) {
		
	}
}
