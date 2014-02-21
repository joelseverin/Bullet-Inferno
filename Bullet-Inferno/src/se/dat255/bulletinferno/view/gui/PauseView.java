package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;
import se.dat255.bulletinferno.view.menu.SimpleToggleSubMenuView;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

public class PauseView extends SimpleToggleSubMenuView implements Disposable {
	private final static int GLASS_WIDTH = 1112, GLASS_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private final Stage stage;
	private final Button resumeButton, mainMenuButton, restartButton, muteButton;
	private final Table table;
	
	public PauseView(Stage stage, ResourceManager resources) {
		super(new Group(), GLASS_WIDTH, GLASS_HEIGHT, (VIRTUAL_WIDTH - GLASS_WIDTH)/2, GLASS_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;
		
		table = new Table();
		table.padTop(180);
		
		table.add(new Image(resources.getDrawableTexture(TextureDefinitionImpl.PAUSEMENU_HEADER)));
		table.row();
		
		resumeButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.PAUSEMENU_RESUME_BUTTON),
				resources.getDrawableTexture(TextureDefinitionImpl.PAUSEMENU_RESUME_BUTTON_DOWN));
		table.add(resumeButton).padTop(90);
		table.row();
		
		mainMenuButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.PAUSEMENU_MAINMENU_BUTTON),
				resources.getDrawableTexture(TextureDefinitionImpl.PAUSEMENU_MAINMENU_BUTTON_DOWN));
		table.add(mainMenuButton).padTop(35);
		table.row();
		
		restartButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.PAUSEMENU_RESTART_BUTTON),
				resources.getDrawableTexture(TextureDefinitionImpl.PAUSEMENU_RESTART_BUTTON_DOWN));
		table.add(restartButton).padTop(35);
		table.row();
		
		muteButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_SOUND_ON),
				null,
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_SOUND_OFF));
		table.add(muteButton).padTop(60);
		
		getToggleActor().addActor(table);
		stage.addActor(getToggleActor());
	}
	
	public void addResumeButtonListener(EventListener listener) {
		resumeButton.addListener(listener);
	}
	
	public void removeResumeButtonListener(EventListener listener) {
		resumeButton.removeListener(listener);
	}
	
	public void addMainMenuButtonListener(EventListener listener) {
		mainMenuButton.addListener(listener);
	}
	
	public void removeMainMenuButtonListener(EventListener listener) {
		mainMenuButton.addListener(listener);
	}
	
	public void addRestartButtonListener(EventListener listener) {
		restartButton.addListener(listener);
	}
	
	public void removeRestartButtonListener(EventListener listener) {
		restartButton.removeListener(listener);
	}
	
	public Button getMuteButton() {
		return muteButton;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		resumeButton.clear();
		mainMenuButton.clear();
		restartButton.clear();
		muteButton.clear();
		
		stage.getRoot().removeActor(getToggleActor());
	}
	
}
