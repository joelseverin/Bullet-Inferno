package se.dat255.bulletinferno.controller.menu;


import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import se.dat255.bulletinferno.model.leaderboard.LeaderboardEntry;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.LeaderboardsView;

public class LeaderboardsController implements SubMenuController {
	private final LeaderboardsView view;
	
	public LeaderboardsController(Stage stage, ResourceManager resources) {
		List<LeaderboardEntry> highscoreEntries = new LinkedList<LeaderboardEntry>();
		highscoreEntries.add(new LeaderboardEntry("Sebastian Blomberg", 2839210));
		highscoreEntries.add(new LeaderboardEntry("Mr Bear", 1834969));
		for(int i = 0; i < 20; i++) {
			highscoreEntries.add(new LeaderboardEntry("Joel Severin", -1));
		}
		view = new LeaderboardsView(stage, resources, highscoreEntries, 
				new LinkedList<LeaderboardEntry>(), new LinkedList<LeaderboardEntry>());
		view.setSlideToggleListener(slideToggleListener);
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

}
