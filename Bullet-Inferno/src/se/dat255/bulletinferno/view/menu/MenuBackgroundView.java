package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MenuBackgroundView {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int PLANE_POSITION_Y = 460, PLANE_ANIMATION_DURATION = 10;

	private final Stage stage;
	private final Image background;
	private final Image airplane;
	
	
	public MenuBackgroundView(Stage stage, ResourceManager resources) {
		this.stage = stage;
		
		background = new Image(resources.getTexture(TextureDefinitionImpl.MENU_BACKGROUND));
		background.setPosition(0, 0);
		stage.addActor(background);
		
		airplane = new Image(resources.getTexture(TextureDefinitionImpl.MENU_PLANE));
		SequenceAction sequence= Actions.sequence(
				Actions.moveTo(-40, PLANE_POSITION_Y),
				Actions.moveTo(VIRTUAL_WIDTH + background.getWidth(), PLANE_POSITION_Y, 
						PLANE_ANIMATION_DURATION));
		
		airplane.addAction(Actions.forever(sequence));
		stage.addActor(airplane);
	}
}
