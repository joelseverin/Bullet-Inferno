package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class StoreView extends SimpleToggleSubMenuView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int GLASS_WIDTH = 1182, GLASS_POSITION_X = 285;
	private final static int CELL_PADDING = 30;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private final Image header;
	private final Table mainTable;
	private final Stage stage;
	
	public StoreView(Stage stage, ResourceManager resources) {
		super(new Group(), GLASS_WIDTH, VIRTUAL_HEIGHT, GLASS_POSITION_X, VIRTUAL_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;

		header = new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_STORE_HEADER));
		
		mainTable = new Table();
		mainTable.setFillParent(true);
		mainTable.setBackground(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_EXTENSION_GLASS));
		mainTable.top();
		mainTable.padTop(100);
		mainTable.add(header).colspan(2);
		
		getToggleActor().addActor(mainTable);
		slideToggle();
		this.stage.addActor(getToggleActor());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		stage.getRoot().removeActor(getToggleActor());
	}

}
