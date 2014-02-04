package se.dat255.bulletinferno.controller.menu;

import java.util.LinkedList;
import java.util.List;

import se.dat255.bulletinferno.model.store.StoreItem;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.StoreView;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class StoreController implements SubMenuController {
	private final StoreView view;
	
	public StoreController(Stage stage, ResourceManager resources) {
		StoreItem item = new StoreItem() {
			@Override
			public String getIdentifier() {
				return "SHIELD_LVL_1";
			}
			
			@Override
			public int getUpgradeCost() {
				return 150;
			}
			
			@Override
			public String getUpdrageDescription() {
				return "Gives you the endurance to withstand heavier crashes";
			}
			
			@Override
			public String getName() {
				return "Crash Shield";
			}
			
			@Override
			public int getMaxLevel() {
				return 5;
			}
			
			@Override
			public int getCurrentLevel() {
				return 2;
			}
		};
		List<StoreItem> items = new LinkedList<StoreItem>();
		items.add(item);
		items.add(item);

		view = new StoreView(stage, resources, items, 200);
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
				subMenuHandler.onDisposeFinished(StoreController.this);
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
