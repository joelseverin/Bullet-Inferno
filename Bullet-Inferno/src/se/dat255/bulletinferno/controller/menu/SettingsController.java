package se.dat255.bulletinferno.controller.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.SettingsView;

public class SettingsController implements SubMenuController {
	private SettingsView settingsView;
	
	public SettingsController(Stage stage, ResourceManager resources) {
		settingsView = new SettingsView(stage, resources);
		settingsView.getBackgroundMusicSlider().addListener(backgroundMusicListener);
		settingsView.getSoundEffectsSlider().addListener(soundEffectsListener);
		settingsView.getSensSlider().addListener(sensListener);
		settingsView.getBackgroundMusicMuteButton().addListener(backgroundMusicMuteListener);
		settingsView.getSoundEffectsMuteButton().addListener(soundEffectsMuteListener);
		settingsView.getSenseResetButton().addListener(sensResetListener);
	}
	
	@Override
	public void dispose() {
		settingsView.dispose();
	}
	
	private ChangeListener backgroundMusicListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			float value = settingsView.getBackgroundMusicSlider().getValue();
			if(value > 0) {
				settingsView.getBackgroundMusicMuteButton().setChecked(false);
			}
		}
	};
	
	private ChangeListener soundEffectsListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			float value = settingsView.getSoundEffectsSlider().getValue();
			System.out.println("hej");
			if(value > 0) {
				settingsView.getSoundEffectsMuteButton().setChecked(false);
			}
		}
	};
	
	private ChangeListener sensListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		}
	};
	
	private ChangeListener backgroundMusicMuteListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if(settingsView.getBackgroundMusicMuteButton().isChecked()) {
				settingsView.getBackgroundMusicSlider().setValue(0);
			} else {
				// Reset to previous state
			}
		}
	};
	
	private ChangeListener soundEffectsMuteListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if(settingsView.getSoundEffectsMuteButton().isChecked()) {
				settingsView.getSoundEffectsSlider().setValue(0);
			} else {
				// Reset to previous state
			}
		}
	};
	
	private ChangeListener sensResetListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			settingsView.getSensSlider().setValue(1);
		}
	};
	
	

}
