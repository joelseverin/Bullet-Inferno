package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LoadoutView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int TABLE_X = 390, GLASS_WIDTH = 1113;
	
	private final ResourceManager resources;
	private final Stage stage;
	private final Table table;
	private final MenuBackgroundView backgroundView;
	
	public LoadoutView(ResourceManager resources, Stage stage) {
		this.resources = resources;
		this.stage = stage;
		
		if(stage.getHeight() != VIRTUAL_HEIGHT || stage.getWidth() != VIRTUAL_WIDTH) {
			stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		}
		
		backgroundView = new MenuBackgroundView(stage, resources);
		
		table = new Table();
		table.setSize(GLASS_WIDTH, VIRTUAL_HEIGHT);
		table.top();
		table.setPosition(TABLE_X, 0);
		table.setBackground(resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_GLASS));
		
		
		table.add(new Image(resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_HEADER))
					).padTop(120);
		table.row();
		table.add(new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_CLICK_INSTRUCTIONS)
				)).padTop(20);
		
		table.debug();
		stage.addActor(table);
	}

	@Override
	public void dispose() {
		stage.getRoot().removeActor(table);
		backgroundView.dispose();
	}

}
