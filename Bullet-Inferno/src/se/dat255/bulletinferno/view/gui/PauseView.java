package se.dat255.bulletinferno.view.gui;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;
import se.dat255.bulletinferno.view.menu.SimpleToggleSubMenuView;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

public class PauseView extends SimpleToggleSubMenuView implements Disposable {
	private final static int GLASS_WIDTH = 1112, GLASS_HEIGHT = 1080, VIRTUAL_HEIGHT = 1080,
			VIRTUAL_WIDTH = 1920;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private final Stage stage;
	private final Button resumeButton, mainMenuButton, restartButton, muteButton;
	private final Table table;
	private final Image background;
	
	public PauseView(Stage stage, ResourceManager resources) {
		super(new Group(), GLASS_WIDTH, GLASS_HEIGHT, (VIRTUAL_WIDTH - GLASS_WIDTH)/2, GLASS_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;
		
		if(stage.getHeight() != VIRTUAL_HEIGHT || stage.getWidth() != VIRTUAL_WIDTH) {
			stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		}
		
		background = new Image();
		background.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		stage.addActor(background);
		
		table = new Table();
		table.padTop(180);
		table.setFillParent(true);
		table.top();
		table.setBackground(resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_GLASS));
		
		table.add(new Image(resources.getDrawableTexture(TextureDefinitionImpl.PAUSEMENU_HEADER)))
			.padTop(190);
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
		
		// Create textures for the overlay of the background
		Pixmap p = new Pixmap((VIRTUAL_WIDTH - GLASS_WIDTH)/2, VIRTUAL_HEIGHT, Format.RGBA8888);
		p.setColor(0, 0, 0, 0.3f);
		p.fillRectangle(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		TextureRegionDrawable trd = new TextureRegionDrawable(new TextureRegion(new Texture(p)));
		Image leftDarkOverlay = new Image(trd);
		Image rightDarkOverlay = new Image(trd);
		rightDarkOverlay.setPosition((VIRTUAL_WIDTH - GLASS_WIDTH)/2 + GLASS_WIDTH, 0);
		p.dispose();
		
		stage.addActor(leftDarkOverlay);
		stage.addActor(rightDarkOverlay);
		
		
		getToggleActor().addActor(table);
		stage.addActor(getToggleActor());
	}
	
	public void setBackground(TextureRegion texture) {
		TextureRegionDrawable trd = new TextureRegionDrawable(texture);
		trd.setMinHeight(VIRTUAL_HEIGHT);
		trd.setMinWidth(VIRTUAL_WIDTH);
		background.setDrawable(trd);
		background.pack();
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
