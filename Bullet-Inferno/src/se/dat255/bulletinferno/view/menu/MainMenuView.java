package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainMenuView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int BUTTON_WIDTH = 386, BUTTON_HEIGHT = 105, BUTTON_PLAY_HEIGHT = 164,
			BUTTON_PLAY_MARGIN_Y = 135;
	private final static int BUTTONGROUP_POSITION_X = 1508, BUTTONGROUP_POSITION_Y = 130, 
			BUTTONGROUP_MARGIN_Y = 35;
	private final static int GLAS_WIDTH = 453;
	private final static int MR_BEAR_X = 110, MR_BEAR_Y = 40;
	
	private final Stage stage;
	private final Button playButton;
	private final Button leaderboardsButton;
	private final Button settingsButton;
	private final Button achievementsButton;
	private final Button storeButton;
	private final Image glass, mrBear;
	private final MenuBackgroundView backgroundView;
	
	public MainMenuView(Stage stage, ResourceManager resources) {
		this.stage = stage;
		
		
		if(stage.getHeight() != VIRTUAL_HEIGHT || stage.getWidth() != VIRTUAL_WIDTH) {
			stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		}
		
		backgroundView = new MenuBackgroundView(stage, resources);
		
		glass = new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_GLAS));
		glass.setSize(GLAS_WIDTH, VIRTUAL_HEIGHT);
		glass.setPosition(VIRTUAL_WIDTH - GLAS_WIDTH, 0);
		
		mrBear = new Image(resources.getTexture(TextureDefinitionImpl.MENU_MR_BEAR));
		mrBear.setPosition(MR_BEAR_X, MR_BEAR_Y);
		
		playButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_PLAYBUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_PLAYBUTTON_OVER));
		playButton.setSize(BUTTON_WIDTH, BUTTON_PLAY_HEIGHT);
		playButton.setPosition(BUTTONGROUP_POSITION_X, BUTTONGROUP_POSITION_Y);
		
		leaderboardsButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_LEADERBOARDSBUTTON), 
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_MAIN_LEADERBOARDSBUTTON_OVER));
		leaderboardsButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		leaderboardsButton.setPosition(BUTTONGROUP_POSITION_X, 
				BUTTONGROUP_POSITION_Y + BUTTON_PLAY_HEIGHT + BUTTON_PLAY_MARGIN_Y);
		
		settingsButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_SETTINGSBUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_SETTINGSBUTTON_OVER));
		settingsButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		settingsButton.setPosition(BUTTONGROUP_POSITION_X, BUTTONGROUP_POSITION_Y + 
				BUTTON_PLAY_HEIGHT + BUTTON_PLAY_MARGIN_Y + BUTTONGROUP_MARGIN_Y + BUTTON_HEIGHT);
		
		achievementsButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_ACHIEVEMENTSBUTTON), 
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_MAIN_ACHIEVEMENTSBUTTON_OVER));
		achievementsButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		achievementsButton.setPosition(BUTTONGROUP_POSITION_X, BUTTONGROUP_POSITION_Y + 
				BUTTON_PLAY_HEIGHT + BUTTON_PLAY_MARGIN_Y + 2*(BUTTON_HEIGHT + BUTTONGROUP_MARGIN_Y));
		
		storeButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_STOREBUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_MAIN_STOREBUTTON_OVER));
		storeButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		storeButton.setPosition(BUTTONGROUP_POSITION_X, BUTTONGROUP_POSITION_Y + 
				BUTTON_PLAY_HEIGHT + BUTTON_PLAY_MARGIN_Y + 3*(BUTTON_HEIGHT + BUTTONGROUP_MARGIN_Y));
		
		this.stage.addActor(mrBear);
		this.stage.addActor(glass);
		this.stage.addActor(playButton);
		this.stage.addActor(leaderboardsButton);
		this.stage.addActor(settingsButton);
		this.stage.addActor(achievementsButton);
		this.stage.addActor(storeButton);
	}
	
	@Override
	public void dispose() {
		backgroundView.dispose();
		stage.getRoot().removeActor(playButton);
		playButton.clear();
		stage.getRoot().removeActor(leaderboardsButton);
		leaderboardsButton.clear();
		stage.getRoot().removeActor(settingsButton);
		settingsButton.clear();
		stage.getRoot().removeActor(achievementsButton);
		achievementsButton.clear();
		stage.getRoot().removeActor(storeButton);
		storeButton.clear();
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
