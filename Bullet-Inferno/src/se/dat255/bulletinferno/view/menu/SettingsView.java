package se.dat255.bulletinferno.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

public class SettingsView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int GLASS_WIDTH = 1182, GLASS_POSITION_X = 285, SLIDER_PADDING_LEFT = 80;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private final Stage stage;
	private final Image label;
	private final Table table;
	private final Group group;
	private final Slider backgroundMusicSlider, soundEffectSlider;
	
	public SettingsView(Stage stage, ResourceManager resources) {
		this.stage = stage;
		
		// Create a group (<div>) for animation purposes
		group = new Group();
		group.setSize(GLASS_WIDTH, VIRTUAL_HEIGHT);
		group.setPosition(GLASS_POSITION_X, 0);
		
		// Setup the content table
		table = new Table();
		table.setFillParent(true);
		table.setBackground(resources.getDrawableTexture(TextureDefinitionImpl.MENU_EXTENSION_GLASS));
		table.top();
		table.padTop(100);
		table.debug();
		
		label = new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_SETTINGS_LABEL));
		table.add(label);
		table.row();
		
		Slider.SliderStyle sliderStyle = new Slider.SliderStyle(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_SLIDER_BG),
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_SLIDER_KNOB));
		
		backgroundMusicSlider = new Slider(0, 1, 0.005f, false, sliderStyle);
		soundEffectSlider = new Slider(0, 1, 0.005f, false, sliderStyle);
		
		setUpAudioSettings(resources);
		
		group.addActor(table);
		SequenceAction sequence = Actions.sequence(Actions.moveTo(GLASS_POSITION_X, VIRTUAL_HEIGHT),
				Actions.moveTo(GLASS_POSITION_X, 0, GLASS_ANIMATION_DURATION));
		group.addAction(sequence);
		this.stage.addActor(group);
	}


	@Override
	public void dispose() {
		stage.getRoot().removeActor(table);
		table.clear();
	}
	
	private void setUpAudioSettings(ResourceManager resources) {
		Image audioLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SETTINGS_AUDIO_LABEL));
		table.add(audioLabel).left().padTop(150);
		table.row();
		
		// Background music slider
		Image backgroundMusicLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SETTINGS_BGMUSIC_LABEL));
		table.add(backgroundMusicLabel).left().pad(30).padBottom(10);
		table.row();
		
		table.add(backgroundMusicSlider).width(755).padLeft(SLIDER_PADDING_LEFT);
		table.row();
		
		Image volumeSliderValuesLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SLIDER_0_TO_100_VALUES));
		table.add(volumeSliderValuesLabel).padLeft(SLIDER_PADDING_LEFT).padTop(10);
		table.row();
		
		// Sound effects settings
		Image soundEffectsLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SETTINGS_SOUNDEF_LABEL));
		table.add(soundEffectsLabel).left().pad(30).padBottom(10);
		table.row();
		
		table.add(soundEffectSlider).width(755).padLeft(SLIDER_PADDING_LEFT);
		table.row();
		
		volumeSliderValuesLabel = new Image(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_SLIDER_0_TO_100_VALUES));
		table.add(volumeSliderValuesLabel).padLeft(SLIDER_PADDING_LEFT).padTop(10);
		table.row();
	}
}
