package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.controller.menu.SettingsController;
import se.dat255.bulletinferno.controller.menu.SubMenuControllHandler;
import se.dat255.bulletinferno.controller.menu.SubMenuController;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.MenuBackgroundView;
import se.dat255.bulletinferno.view.menu.MainMenuView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuController extends SimpleController implements SubMenuControllHandler {
	private final Stage stage;
	private final ResourceManager resources;
	private final MenuBackgroundView backgroundView;
	private final MainMenuView menuView;
	private final MasterController masterController;
	private SubMenuController activeSubController = null;
	private RunLater nextSubController = null;
	
	public MainMenuController(MasterController masterController, ResourceManager resources) {
		this.masterController = masterController;
		this.resources = resources;
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		backgroundView = new MenuBackgroundView(stage, resources);
		menuView = new MainMenuView(stage, resources);
		
		menuView.addStoreListener(storeListener);
		menuView.addLeaderBoardsListener(leaderboardsListener);
		menuView.addSettingsListener(settingListener);
		menuView.addAchievementsListener(achievementsListener);
		menuView.addPlayListener(playListener);
	}
	
	@Override
	public void render(float delta) {
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		//stage.setViewport(width, height, false);
	}

	@Override
	public void dispose() {
		menuView.dispose();
		stage.dispose();
		if(activeSubController != null) {
			activeSubController.dispose();
		}
	}
	
	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void onDisposeFinished(SubMenuController controller) {
		// Disposal has finished on old subcontroller, we can now start a the new
		activeSubController = nextSubController.startSubController();
		nextSubController = null;
	}
	
	private void switchSubController(RunLater subController) {
		if(activeSubController != null) {
			// Send a dispose request and wait for active sub controller to finish disposing.
			activeSubController.disposeRequest(this);
			nextSubController = subController;
		} else {
			// No subcontroller is active and we can start new one right away
			activeSubController = subController.startSubController();
		}
	}
	
	private ChangeListener storeListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		}
	};
	
	private ChangeListener playListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		}
	};
	
	private ChangeListener settingListener = new ChangeListener() {
		private RunLater runlater = new RunLater() {
			@Override
			public SubMenuController startSubController() {
				return new SettingsController(stage, resources);
			}
		};
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			switchSubController(runlater);
		}
	};
	
	private ChangeListener achievementsListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		}
	};
	
	private ChangeListener leaderboardsListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		}
	};
	
	private interface RunLater {
		public SubMenuController startSubController();
	}
}
