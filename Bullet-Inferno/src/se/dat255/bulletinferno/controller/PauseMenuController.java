package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.gui.PauseView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseMenuController extends SimpleController {
	private final PauseView view;
	private final Stage stage;
	private final MasterController masterController;
	private final GameController gameController;
	private final ResourceManager resources;
	
	
	public PauseMenuController(MasterController masterController, GameController gameController, 
			ResourceManager resources) {
		this.masterController = masterController;
		this.gameController = gameController;
		this.resources = resources;
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		
		view = new PauseView(stage, resources);
		
		view.addMainMenuButtonListener(mainMenuListener);
		view.addRestartButtonListener(restartListener);
		view.addResumeButtonListener(resumeListener);
		view.getMuteButton().addListener(muteListener);
		
		Preferences pref = MasterController.getUserDefaults();
		if(pref.contains("soundEffectsMuted") && pref.contains("backgroundMusicMuted")) {
			if(pref.getBoolean("soundEffectsMuted") && pref.getBoolean("backgroundMusicMuted")) {
				view.getMuteButton().setChecked(true);
			}
		}
		
	}
	
	@Override
	public void show() {
		view.setBackground(ScreenUtils.getFrameBufferTexture(0, 0, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		
		Gdx.input.setInputProcessor(stage);
		if(!view.isToggledVisible()) {
			view.slideToggle();
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		view.dispose();
	}
	
	private ChangeListener resumeListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent arg0, Actor arg1) {
			masterController.setScreen(gameController);
		}
	};
	
	private ChangeListener mainMenuListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent arg0, Actor arg1) {
			masterController.setScreen(new MainMenuController(masterController, resources));
		}
	};
	
	private ChangeListener restartListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent arg0, Actor arg1) {
			// TODO : gameController.restart();
			masterController.setScreen(gameController);
		}
	};
	
	private ClickListener muteListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			boolean muted = view.getMuteButton().isChecked();
			MasterController.getUserDefaults().putBoolean("soundEffectsMuted", !muted);
			MasterController.getUserDefaults().putBoolean("backgroundMusicMuted", !muted);
			
			// TODO audioplayer.mute()
		}
	};
}
