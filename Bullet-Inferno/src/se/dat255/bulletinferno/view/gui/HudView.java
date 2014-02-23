package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class HudView implements Disposable {
	private static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920, BARMETER_WIDTH = 275, 
			BARMETER_HEIGHT = 19;
	private final Label scoreLabel;
	private final Label coinLabel;
	private final Image bar;
	private final Image specialAbilityButtonBg;
	private final Button specialAbilityButton, pauseButton;
	private final Stage stage;
	
	private final TextureRegion healthTexture;
	private final Image healthBar;
	
	public HudView(Stage stage, ResourceManager resources) {
		this.stage = stage;
		
		Skin skin = resources.getSkin();
		
		if(stage.getHeight() != VIRTUAL_HEIGHT || stage.getWidth() != VIRTUAL_WIDTH) {
			stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		}
		
		bar = new Image(resources.getTexture(TextureDefinitionImpl.HUD_BAR));
		bar.setPosition(0, VIRTUAL_HEIGHT - bar.getHeight());
		stage.addActor(bar);
		
		LabelStyle style = new LabelStyle(skin.getFont("myraid40"), skin.getColor("darkgrey"));
		scoreLabel = new Label("0", style);
		scoreLabel.setPosition(105, bar.getY() + 82);
		stage.addActor(scoreLabel);
		coinLabel = new Label("0", style);
		coinLabel.setPosition(412, bar.getY() + 82);
		stage.addActor(coinLabel);
		
		specialAbilityButtonBg = new Image(
				resources.getTexture(TextureDefinitionImpl.HUD_SPECIALABILITY_BACKGROUND));
		specialAbilityButtonBg.setPosition(VIRTUAL_WIDTH - specialAbilityButtonBg.getWidth() , 0);
		stage.addActor(specialAbilityButtonBg);
		
		specialAbilityButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.HUD_SPECIALABILITY_BUTTON),
				resources.getDrawableTexture(
						TextureDefinitionImpl.HUD_SPECIALABILITY_BUTTON_DOWN));
		specialAbilityButton.setPosition(VIRTUAL_WIDTH - specialAbilityButton.getWidth(), 0);
		stage.addActor(specialAbilityButton);
		
		pauseButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.HUD_PAUSE_BUTTON),
				resources.getDrawableTexture(TextureDefinitionImpl.HUD_PAUSE_BUTTON_DOWN)
				);
		pauseButton.setPosition(stage.getWidth() - 110, bar.getY() + 78);
		stage.addActor(pauseButton);
		
		healthTexture = resources.getTexture(TextureDefinitionImpl.HUD_BARMETER_GRADIENT);
		healthBar = new Image(healthTexture);
		healthBar.setWidth(275);
		healthBar.setPosition(772, bar.getY() + 92);
		stage.addActor(healthBar);
		setHealth(1);
		
	}

	public void addSpecialAbilityButtonListener(EventListener listener) {
		specialAbilityButton.addListener(listener);
	}
	
	public void removeSpecialAbilityButtonListener(EventListener listener) {
		specialAbilityButton.removeListener(listener);
	}

	public void addPauseButtonListener(EventListener listener) {
		pauseButton.addListener(listener);
	}
	
	public void removePauseButtonListener(EventListener listener) {
		pauseButton.removeListener(listener);
	}
	
	private int cachedScore = 0;
	public void setScore(int score) {
		if(score != cachedScore) {
			scoreLabel.setText(Integer.toString(score));
			cachedScore = score;
		}
	}
	
	private int cachedCoin = 0;
	public void setCoinScore(int coinScore) {
		if(coinScore != cachedCoin) {
			coinLabel.setText(Integer.toString(coinScore));
			cachedCoin = coinScore;
		}
	}
	
	private float cachedHealth = 0;
	public void setHealth(float health) {
		if(health != cachedHealth) {
			if(health > 1f) {
				health = 1f;
			}
			// Calculate where on the gradient to crop
			int x = (int) (health * BARMETER_WIDTH - 5);
			// If calculation turned negative, make x have the lowest value (0)
			if(x < 0) {
				x = 0;
			}

			healthTexture.setRegion(x, 0, 5, BARMETER_HEIGHT);
			healthBar.setWidth(BARMETER_WIDTH * health);
			cachedHealth = health;
		}
	}
	
	@Override
	public void dispose() {
		stage.getRoot().removeActor(scoreLabel);
		stage.getRoot().removeActor(coinLabel);
		stage.getRoot().removeActor(bar);
		stage.getRoot().removeActor(specialAbilityButtonBg);
		stage.getRoot().removeActor(specialAbilityButton);
	}
	
}
