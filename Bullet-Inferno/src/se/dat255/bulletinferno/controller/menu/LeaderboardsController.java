package se.dat255.bulletinferno.controller.menu;


import java.util.LinkedList;
import java.util.List;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import se.dat255.bulletinferno.controller.SimpleController;
import se.dat255.bulletinferno.model.leaderboard.LeaderboardEntry;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.LeaderboardsView;

public class LeaderboardsController extends SimpleController implements SubMenuController {
	private final LeaderboardsView view;
	
	public LeaderboardsController(Stage stage, ResourceManager resources) {
		List<LeaderboardEntry> highscoreEntries = new LinkedList<LeaderboardEntry>();
		highscoreEntries.add(new LeaderboardEntry("Sebastian Blomberg", 2839210));
		highscoreEntries.add(new LeaderboardEntry("Mr Bear", 1834969));
		for(int i = 0; i < 8; i++) {
			highscoreEntries.add(new LeaderboardEntry("Joel Severin", -1));
		}
		
		List<LeaderboardEntry> coinEntries = new LinkedList<LeaderboardEntry>();
		coinEntries.add(new LeaderboardEntry("Sebastian Blomberg", 9720));
		coinEntries.add(new LeaderboardEntry("Mr Bear", 8401));
		for(int i = 0; i < 8; i++) {
			coinEntries.add(new LeaderboardEntry("Joel Severin", 1));
		}
		
		
		view = new LeaderboardsView(stage, resources, highscoreEntries, coinEntries, 
				new LinkedList<LeaderboardEntry>());
		view.setSlideToggleListener(slideToggleListener);
		view.addHighScoreBoardButtonListener(highScoreBoardListener);
		view.addCollectedCoinsBoardButtonListener(collectedCoinsBoardListener);
		view.addLongestRunBoardButtonListener(longestRunBoardListener);
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
			view.showLeaderboard(LeaderboardsView.LeaderboardType.HIGHSCORE);
		}
	};
	
	private ClickListener collectedCoinsBoardListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			view.showLeaderboard(LeaderboardsView.LeaderboardType.COLLECTED_COINS);
		}
	};
	
	private ClickListener longestRunBoardListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			view.showLeaderboard(LeaderboardsView.LeaderboardType.LONGEST_RUN);
		}
	};
}
