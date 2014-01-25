package se.dat255.bulletinferno.view.menu;

import javax.swing.event.ChangeEvent;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingsView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int GLASS_WIDTH = 1182, GLASS_POSITION_X = 285, SLIDER_PADDING_LEFT = 80;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private boolean isDown = false;
	private final Stage stage;
	private final Image label;
	private final Table table;
	private final Group group;
	private final Slider backgroundMusicSlider, soundEffectSlider, sensSlider;
	private final CheckBox soundEffectsMuteButton, backgroundMusicMuteButton;
	private final Button sensResetButton;
	private EventListener toggleListener = null;
	
	public SettingsView(Stage stage, ResourceManager resources) {
		this.stage = stage;
		
		// Create a group (<div>) for animation purposes
		group = new Group();
		group.setSize(GLASS_WIDTH, VIRTUAL_HEIGHT);
		group.setPosition(GLASS_POSITION_X, VIRTUAL_HEIGHT);
		
		// Setup the content table
		table = new Table();
		table.setFillParent(true);
		table.setBackground(resources.getDrawableTexture(TextureDefinitionImpl.MENU_EXTENSION_GLASS));
		table.top();
		table.padTop(100);
		
		label = new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_SETTINGS_LABEL));
		table.add(label).colspan(2);
		table.row();
		
		Slider.SliderStyle sliderStyle = new Slider.SliderStyle(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_SLIDER_BG),
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_SLIDER_KNOB));
		
		backgroundMusicSlider = new Slider(0, 1, 0.02f, false, sliderStyle);
		soundEffectSlider = new Slider(0, 1, 0.02f, false, sliderStyle);
		sensSlider = new Slider(0, 2, 0.01f, false, sliderStyle);
		
		CheckBox.CheckBoxStyle checkboxStyle = new CheckBox.CheckBoxStyle(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_SOUND_ON), 
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_SOUND_OFF), new BitmapFont(), Color.BLACK);
		
		soundEffectsMuteButton = new CheckBox("", checkboxStyle);
		backgroundMusicMuteButton = new CheckBox("", checkboxStyle);
		
		sensResetButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_RESETBUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_RESETBUTTON));
		
		setUpAudioSettings(resources);
		setUpTouchSettings(resources);
		
		group.addActor(table);
		slideToggle();
		this.stage.addActor(group);
	}

	/**
	 * Toggles the view so that it's animated up/down and is/isn't visible in the views given 
	 * VIRTUAL_WIDTH and VIRTUAL_HEIGHT
	 */
	public void slideToggle() {
		// Remove previous action
		if(oldSlideAction != null) {
			group.removeAction(oldSlideAction);
		}
		
		if(isDown) {
			group.addAction(getAnimateUpAction());
			isDown = false;
		} else {
			group.addAction(getAnimateDownAction());
			isDown = true;
		}
	}
	
	private Action oldSlideAction = null;
	// A helper in order to always get the same speed for the animation, no matter where
	// the group currently is positioned
	private Action getAnimateUpAction() {
		oldSlideAction = Actions.sequence(
							Actions.moveTo(GLASS_POSITION_X, VIRTUAL_HEIGHT, 
									GLASS_ANIMATION_DURATION * (1 - group.getY()/VIRTUAL_HEIGHT)),
									onActionComplete);
		return oldSlideAction;
	}
	
	private Action getAnimateDownAction() {
		oldSlideAction = Actions.sequence(
							Actions.moveTo(GLASS_POSITION_X, 0, 
									GLASS_ANIMATION_DURATION * group.getY()/VIRTUAL_HEIGHT),
									onActionComplete);
		return oldSlideAction;
	}
	
	/**
	 * Returns whether the view is in place, i.e. has been toggled so that it's visible
	 * in it's VIRTUAL_WIDTH and VIRTUAL_HEIGHT. See {@link SettingsView#slideToggle()}
	 * @return is in place
	 */
	public boolean isInPlace() {
		return isDown;
	}
	
	/**
	 * Set listener that receives a callback on a complete toggle. 
	 * See {@link SettingsView#slideToggle()} 
	 * @param listener
	 */
	public void setSlideToggleListener(EventListener listener) {
		this.toggleListener = listener;
	}
	
	private boolean inActionComplete = false;
	private Action onActionComplete = new Action() {
		@Override
		public boolean act(float delta) {
			inActionComplete = true;
			if(toggleListener != null) {
				// Due to a design flaw in libgdx, we need to add and remove listener since it
				// otherwise would be notified by all of its children's event too.
				group.addListener(toggleListener);
				group.fire(new ChangeListener.ChangeEvent());
				if(toggleListener == null) {
					// If toggleListener is null now a dispose call has been placed to the view
					// but since that happened during a firing of an event, the view can't remove
					// all listeners, so we need to here.
					group.clearListeners();
				} else {
					group.removeListener(toggleListener);
				}
			}
			inActionComplete = false;
			return true;
		}
		
	};
	
	@Override
	public void dispose() {
		toggleListener = null;
		if(inActionComplete) {
			// If in action complete, let it worry about clearing the rest.
			group.clearChildren();
		} else {
			group.clear();
		}
		stage.getRoot().removeActor(group);
		backgroundMusicSlider.clear();
		backgroundMusicMuteButton.clear();
		soundEffectSlider.clear();
		soundEffectsMuteButton.clear();
		sensSlider.clear();
		sensResetButton.clear();
	}
	
	public Slider getBackgroundMusicSlider() {
		return backgroundMusicSlider;
	}
	
	public Slider getSoundEffectsSlider() {
		return soundEffectSlider;
	}
	
	public Slider getSensSlider() {
		return sensSlider;
	}
	
	public CheckBox getBackgroundMusicMuteButton() {
		return backgroundMusicMuteButton;
	}
	
	public CheckBox getSoundEffectsMuteButton() {
		return soundEffectsMuteButton;
	}
	
	public Button getSenseResetButton() {
		return sensResetButton;
	}
	
	private void setUpAudioSettings(ResourceManager resources) {
		Image audioLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SETTINGS_AUDIO_LABEL));
		table.add(audioLabel).left().padTop(75);
		table.row();
		
		// Background music slider
		Image backgroundMusicLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SETTINGS_BGMUSIC_LABEL));
		table.add(backgroundMusicLabel).left().pad(30).padBottom(10);
		table.row();
		
		// Create table to get a row span on the mute button
		Image volumeSliderValuesLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SLIDER_0_TO_100_VALUES));
		Table bgMusicTable = new Table();
		bgMusicTable.add(backgroundMusicSlider).width(755);
		bgMusicTable.row();
		bgMusicTable.add(volumeSliderValuesLabel).padTop(10);
		
		table.add(bgMusicTable).padLeft(SLIDER_PADDING_LEFT);
		table.add(backgroundMusicMuteButton).padLeft(20).padBottom(25);
		table.row();
		
		// Sound effects settings
		Image soundEffectsLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SETTINGS_SOUNDEF_LABEL));
		table.add(soundEffectsLabel).left().pad(30).padBottom(10);
		table.row();
		
		// Create table to get a row span on the mute button
		volumeSliderValuesLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SLIDER_0_TO_100_VALUES));
		Table soundEffectsTable = new Table();
		soundEffectsTable.add(soundEffectSlider).width(755);
		soundEffectsTable.row();
		soundEffectsTable.add(volumeSliderValuesLabel).padTop(10);
		
		table.add(soundEffectsTable).padLeft(SLIDER_PADDING_LEFT);
		table.add(soundEffectsMuteButton).padLeft(20).padBottom(25);
		table.row();
		
	}
	
	private void setUpTouchSettings(ResourceManager resources) {
		Image touchLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SETTINGS_TOUCH_LABEL));
		table.add(touchLabel).left().padTop(100);
		table.row();
		
		Image sensLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SETTINGS_SENSE_LABEL));
		table.add(sensLabel).left().pad(30).padBottom(10);
		table.row();
		
		Image tocuhSliderValuesLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SLIDER_LOW_TO_HIGH_VALUES));
		Table sensTable = new Table();
		sensTable.add(sensSlider).width(755);
		sensTable.row();
		sensTable.add(tocuhSliderValuesLabel);
		
		table.add(sensTable).padLeft(SLIDER_PADDING_LEFT);
		table.add(sensResetButton).padLeft(20).padBottom(36);
		table.row();
	}
}
