package se.dat255.bulletinferno.controller.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import se.dat255.bulletinferno.model.achievement.Achievement;
import se.dat255.bulletinferno.model.achievement.AchievementImpl;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.AchievementsView;

public class AchievementsController implements SubMenuController {
	private final AchievementsView view;
	
	public AchievementsController(Stage stage, ResourceManager resources) {
		List<Achievement> achievements = new LinkedList<Achievement>();
		achievements.add(new AchievementImpl("", "Distance noob", "Fly 100km", true));
		achievements.add(new AchievementImpl("", "Distance Intermediate", "Fly 800km", false));
		achievements.add(new AchievementImpl("", "Distance Pro", "Fly 1500km", false));
		view = new AchievementsView(stage, resources, achievements);
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
				subMenuHandler.onDisposeFinished(AchievementsController.this);
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
