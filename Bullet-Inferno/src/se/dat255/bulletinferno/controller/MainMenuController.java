package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.controller.menu.AchievementsController;
import se.dat255.bulletinferno.controller.menu.LeaderboardsController;
import se.dat255.bulletinferno.controller.menu.SettingsController;
import se.dat255.bulletinferno.controller.menu.StoreController;
import se.dat255.bulletinferno.controller.menu.SubMenuControllHandler;
import se.dat255.bulletinferno.controller.menu.SubMenuController;
import se.dat255.bulletinferno.util.MusicDefinitionImpl;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.audio.AudioPlayer;
import se.dat255.bulletinferno.view.audio.AudioPlayerImpl;
import se.dat255.bulletinferno.view.menu.MenuBackgroundView;
import se.dat255.bulletinferno.view.menu.MainMenuView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
	private final AudioPlayer audioPlayer;
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
		
		audioPlayer = new AudioPlayerImpl(resources);
		Preferences preferences = MasterController.getUserDefaults();
		if(preferences.contains("backgroundMusicVolume")) {
			if(preferences.contains("backgroundMusicMuted") 
					&& preferences.getBoolean("backgroundMusicMuted")) {
				audioPlayer.mute();
			}
			audioPlayer.setVolume(preferences.getFloat("backgroundMusicVolume"));
		}
		audioPlayer.playMusic(MusicDefinitionImpl.MENU_BACKGROUND, true);
	}
	
	@Override
	public void render(float delta) {
		stage.act(Math.min(delta, 1 / 30f));
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
		stage.clear();
		stage.dispose();
		backgroundView.dispose();
		audioPlayer.dispose();
		if(activeSubController != null) {
			activeSubController.dispose();
		}
	}
	
	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(stage);
		if(activeSubController != null) {
			activeSubController.show();
		}
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		if(activeSubController != null) {
			activeSubController.hide();
		}
	}
	
	@Override
	public void resume() {
		if(activeSubController != null) {
			activeSubController.resume();
		}
	}
	
	@Override
	public void pause() {
		if(activeSubController != null) {
			activeSubController.pause();
		}
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
		private RunLater runlater = new RunLater() {
			@Override
			public SubMenuController startSubController() {
				return new StoreController(stage, resources);
			}
		};
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			switchSubController(runlater);
		}
	};
	
	private ChangeListener playListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			masterController.setScreenWithDisposal(new LoadoutController(masterController, resources));
		}
	};
	
	private ChangeListener settingListener = new ChangeListener() {
		private RunLater runlater = new RunLater() {
			@Override
			public SubMenuController startSubController() {
				return new SettingsController(stage, resources, MasterController.getUserDefaults(), 
						audioPlayer);
			}
		};
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			switchSubController(runlater);
		}
	};
	
	private ChangeListener achievementsListener = new ChangeListener() {
		private RunLater runlater = new RunLater() {
			@Override
			public SubMenuController startSubController() {
				return new AchievementsController(stage, resources);
			}
		};
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			switchSubController(runlater);
		}
	};
	
	private ChangeListener leaderboardsListener = new ChangeListener() {
		private RunLater runlater = new RunLater() {
			@Override
			public SubMenuController startSubController() {
				return new LeaderboardsController(stage, resources);
			}
		};
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			switchSubController(runlater);
		}
	};
	
	private interface RunLater {
		public SubMenuController startSubController();
	}
}
