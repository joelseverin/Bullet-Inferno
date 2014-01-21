package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public abstract class SimpleMainMenuView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int BUTTON_WIDTH = 386, BUTTON_HEIGHT = 105;
	private final static int BUTTONGROUP_POSITION_X = 1508, BUTTONGROUP_POSITION_Y = 130, 
			BUTTONGROUP_MARGIN_Y = 35;
	
	private final Stage stage;
	private final Button playButton;
	private final Button leaderboardsButton;
	private final Button settingsButton;
	private final Button achievementsButton;
	private final Button storeButton;
	
	public SimpleMainMenuView(Stage stage, ResourceManager resources) {
		this.stage = stage;
		
		// Set to the view's correct viewport in order to place content in the corrent position
		float preHeight = stage.getHeight(), preWidth = stage.getWidth();
		if(preHeight != VIRTUAL_HEIGHT || preWidth != VIRTUAL_WIDTH) {
			stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		}
		
		storeButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_STOREBUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_STOREBUTTON_OVER));
		storeButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		storeButton.setPosition(BUTTONGROUP_POSITION_X, BUTTONGROUP_POSITION_Y);
		
		achievementsButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_ACHIEVEMENTSBUTTON), 
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_MAIN_ACHIEVEMENTSBUTTON_OVER));
		achievementsButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		achievementsButton.setPosition(BUTTONGROUP_POSITION_X, 
				BUTTONGROUP_POSITION_Y + BUTTONGROUP_MARGIN_Y + BUTTON_HEIGHT);
		
		settingsButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_SETTINGSBUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_SETTINGSBUTTON_OVER));
		settingsButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		achievementsButton.setPosition(BUTTONGROUP_POSITION_X, 
				BUTTONGROUP_POSITION_Y + 2*( BUTTONGROUP_MARGIN_Y + BUTTON_HEIGHT));
		
		leaderboardsButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_LEADERBOARDSBUTTON), 
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_MAIN_LEADERBOARDSBUTTON_OVER));
		leaderboardsButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		leaderboardsButton.setPosition(BUTTONGROUP_POSITION_X, 
				BUTTONGROUP_POSITION_Y + 3*( BUTTONGROUP_MARGIN_Y + BUTTON_HEIGHT));
		
		playButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_PLAYBUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_PLAYBUTTON_OVER));
		playButton.setSize(BUTTON_WIDTH, 164);
		leaderboardsButton.setPosition(BUTTONGROUP_POSITION_X, 
				BUTTONGROUP_POSITION_Y + 4*( BUTTONGROUP_MARGIN_Y + BUTTON_HEIGHT) + 100);
		
		
		this.stage.addActor(playButton);
		this.stage.addActor(leaderboardsButton);
		this.stage.addActor(settingsButton);
		this.stage.addActor(achievementsButton);
		this.stage.addActor(storeButton);
		
		// Set back the viewport to it's state before entering this construct
		if(preHeight != VIRTUAL_HEIGHT || preWidth != VIRTUAL_WIDTH) {
			stage.setViewport(preHeight, preWidth, false);
		}
	}
	
	@Override
	public void dispose() {
		
	}
	
	public void addPlayListener(EventListener listener) {
		playButton.addListener(listener);
	}
	
	public void removePlayListener(EventListener listener) {
		playButton.removeListener(listener);
	}
	
	public void addAchievementsListener(EventListener listener) {
		achievementsButton.addListener(listener);
	}
	
	public void removeAchievementsListener(EventListener listener) {
		achievementsButton.removeListener(listener);
	}
	
	public void addStoreListener(EventListener listener) {
		storeButton.addListener(listener);
	}
	
	public void removeStoreListener(EventListener listener) {
		storeButton.removeListener(listener);
	}
	
	public void addLeaderBoardsListener(EventListener listener) {
		leaderboardsButton.addListener(listener);
	}
	
	public void removeLeaderBoardsListener(EventListener listener) {
		leaderboardsButton.removeListener(listener);
	}
	
	public void addSettingsListener(EventListener listener) {
		settingsButton.addListener(listener);
	}
	
	public void removeSettingsListener(EventListener listener) {
		settingsButton.removeListener(listener);
	}
	
}
