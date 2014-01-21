package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.MenuBackgroundView;
import se.dat255.bulletinferno.view.menu.MainMenuView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuController extends SimpleController {
	private final Stage stage;
	private final MenuBackgroundView backgroundView;
	private final MainMenuView menuView;
	private final MasterController masterController;
	
	
	public MainMenuController(MasterController masterController, ResourceManager resources) {
		this.masterController = masterController;
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
	}

	@Override
	public void resize(int width, int height) {
		//stage.setViewport(width, height, false);
	}

	@Override
	public void dispose() {
		menuView.dispose();
		stage.dispose();
	}
	
	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(stage);
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
		@Override
		public void changed(ChangeEvent event, Actor actor) {
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
}
