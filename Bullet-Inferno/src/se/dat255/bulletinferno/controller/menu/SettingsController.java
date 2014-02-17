package se.dat255.bulletinferno.controller.menu;

import se.dat255.bulletinferno.controller.SimpleController;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.audio.AudioPlayer;
import se.dat255.bulletinferno.view.menu.SettingsView;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * Controller for the settings sub menu.
 * 
 * <strong>Observe</strong>
 * To make sure user preferences are persistent, call 
 * {@link SettingsController#saveUserPreferences()} or {@link SettingsController#dispose()}.
 * 
 * @author Sebastian Blomberg
 *
 */
public class SettingsController extends SimpleController implements SubMenuController {
	private final SettingsView settingsView;
	private AudioPlayer audioPlayer = null;
	private final Preferences preferences;
	
	public SettingsController(Stage stage, ResourceManager resources, Preferences preferences) {
		this.preferences = preferences;
		
		settingsView = new SettingsView(stage, resources);
		settingsView.getBackgroundMusicSlider().addListener(backgroundMusicListener);
		settingsView.getSoundEffectsSlider().addListener(soundEffectsListener);
		settingsView.getSensSlider().addListener(sensListener);
		settingsView.getBackgroundMusicMuteButton().addListener(backgroundMusicMuteListener);
		settingsView.getSoundEffectsMuteButton().addListener(soundEffectsMuteListener);
		settingsView.getSenseResetButton().addListener(sensResetListener);
		settingsView.setSlideToggleListener(slideToggleListener);
		
		// Initialize slider and buttons with user preference
		if(preferences.contains("backgroundMusicVolume")) {
			float volume = 0;
			if(preferences.getBoolean("backgroundMusicMuted")) {
				settingsView.getBackgroundMusicMuteButton().setChecked(true);
			} else {
				volume = preferences.getFloat("backgroundMusicVolume");
			}
			settingsView.getBackgroundMusicSlider().setValue(volume);
		} else {
			settingsView.getBackgroundMusicSlider().setValue(0.7f);
		}
		
		if(preferences.contains("backgroundMusicVolume")) {
			float volume = 0;
			if(preferences.getBoolean("soundEffectsMuted")) {
				settingsView.getSoundEffectsMuteButton().setChecked(true);
			} else {
				volume = preferences.getFloat("soundEffectsVolume");
			}
			settingsView.getSoundEffectsSlider().setValue(volume);
		} else {
			settingsView.getSoundEffectsSlider().setValue(1);
		}
		
		if(preferences.contains("steeringsense")) {
			settingsView.getSensSlider()
						.setValue(preferences.getFloat("steeringsense"));
		} else {
			settingsView.getSensSlider().setValue(1);
		}
	}
	
	public SettingsController(Stage stage, ResourceManager resources, Preferences preferences,
			AudioPlayer backgroundAudioPlayer) {
		this(stage, resources, preferences);
		this.audioPlayer = backgroundAudioPlayer;
	}
	
	/**
	 * Disposes of the controller's resources (including views) and saves the user settings (see 
	 * {@link SettingsController#saveUserPreferences()}
	 */
	@Override
	public void dispose() {
		saveUserPreferences();
		settingsView.dispose();
	}
	
	private DragListener backgroundMusicListener = new DragListener() {
		@Override
		public void dragStop(InputEvent event, float x, float y, int pointer)  {
			float value = settingsView.getBackgroundMusicSlider().getValue();
			if(value > 0) {
				// Uncheck mute button if checked
				if(settingsView.getBackgroundMusicMuteButton().isChecked()) {
					settingsView.getBackgroundMusicMuteButton().setChecked(false);
					preferences.putBoolean("backgroundMusicMuted", false);
				}
				// Update the user's preferences
				preferences.putFloat("backgroundMusicVolume", value);
				
				if(audioPlayer != null) {
					audioPlayer.setVolume(value);
					
				} 
			} else {
				settingsView.getBackgroundMusicMuteButton().setChecked(true);
				preferences.putBoolean("backgroundMusicMuted", true);
			}
		}
	};
	
	private DragListener soundEffectsListener = new DragListener() {
		@Override
		public void dragStop(InputEvent event, float x, float y, int pointer)  {
			float value = settingsView.getSoundEffectsSlider().getValue();
			if(value > 0) {
				if(settingsView.getSoundEffectsMuteButton().isChecked()) {
					settingsView.getSoundEffectsMuteButton().setChecked(false);
					preferences.putBoolean("soundEffectsMuted", false);
				}
				preferences.putFloat("soundEffectsVolume", value);
			} else {
				settingsView.getSoundEffectsMuteButton().setChecked(true);
				preferences.putBoolean("soundEffectsMuted", true);
			}
		}
	};
	
	private DragListener sensListener = new DragListener() {
		@Override
		public void dragStop(InputEvent event, float x, float y, int pointer)  {
			preferences.putFloat("steeringsense", settingsView.getSensSlider().getValue());
		}
	};
	
	private ClickListener backgroundMusicMuteListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			if(settingsView.getBackgroundMusicMuteButton().isChecked()) {
				settingsView.getBackgroundMusicSlider().setValue(0);
				preferences.putBoolean("backgroundMusicMuted", true);
			} else {
				settingsView.getBackgroundMusicSlider().setValue(
						preferences.getFloat("backgroundMusicVolume"));
				preferences.putBoolean("backgroundMusicMuted", false);
			}
		}
	};
	
	
	private ClickListener soundEffectsMuteListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			if(settingsView.getSoundEffectsMuteButton().isChecked()) {
				settingsView.getSoundEffectsSlider().setValue(0);
				preferences.putBoolean("soundEffectsMuted", true);
			} else {
				settingsView.getSoundEffectsSlider().setValue(
						preferences.getFloat("soundEffectsVolume"));
				preferences.putBoolean("soundEffectsMuted", false);
			}
		}
	};
	
	private ChangeListener sensResetListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			settingsView.getSensSlider().setValue(1);
			preferences.putFloat("steeringsense", 1);
		}
	};

	private ChangeListener slideToggleListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			// If the event was fired upon a slide down;
			if(settingsView.isToggledVisible()) {
				return;
			}
			
			dispose();
			if(subMenuHandler != null) {
				subMenuHandler.onDisposeFinished(SettingsController.this);
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
		if(!settingsView.isToggledVisible()) {
			dispose();
		} else {
			settingsView.slideToggle();
		}
	}
	
	public void saveUserPreferences() {
		preferences.flush();
	}
	
	@Override
	public void pause() {
		saveUserPreferences();
	}
}
