package se.dat255.bulletinferno.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import se.dat255.bulletinferno.model.store.StoreItem;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

public class StoreItemView extends Table {
	private final Table infoTable = new Table();
	private final Table barTable = new Table();
	private Button buyButton = null;
	
	public StoreItemView(final StoreItem item, ResourceManager resources) {
		add(new Image(resources.getDrawableTexture(item))).left().padLeft(10);
		
		// TODO replace with skin
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myraidpro.ttf"));
		BitmapFont nameFont = generator.generateFont(30);
		BitmapFont textFont = generator.generateFont(26);
		generator.dispose();
		
		infoTable.add(new Label(item.getName() + 
					(item.getCurrentLevel() < item.getMaxLevel()? 
							" Level " + (item.getCurrentLevel() + 1): ""), 
				new Label.LabelStyle(nameFont, Color.valueOf("4c7d7d")))).left();
		infoTable.add(new Image(
					resources.getDrawableTexture(TextureDefinitionImpl.MENU_STORE_COIN_MEDIUM))
				);
		infoTable.add(new Label(item.getUpgradeCost() + "", 
				new Label.LabelStyle(textFont, Color.valueOf("343433")))).left();
		infoTable.row();
		
		if(item.getCurrentLevel() < item.getMaxLevel()) {
			Label l = new Label(item.getUpdrageDescription(), 
					new Label.LabelStyle(textFont, Color.valueOf("343433")));
			l.setWrap(true);
			infoTable.add(l).width(500).left().padTop(10);
			
			buyButton = new Button(
					resources.getDrawableTexture(TextureDefinitionImpl.MENU_STORE_BUYBUTTON),
					resources.getDrawableTexture(TextureDefinitionImpl.MENU_STORE_BUYBUTTON_OVER));
			infoTable.add(buyButton).right().colspan(2).padTop(10);
			
		}
		add(infoTable).right().padLeft(15);
		row();

		ResourceIdentifier darkBarId = new ResourceIdentifier() {
			@Override
			public String getIdentifier() {
				return "MENU_STORE_BAR_" + item.getMaxLevel() + "_DARK";
			}
		};
		ResourceIdentifier lightkBarId = new ResourceIdentifier() {
			@Override
			public String getIdentifier() {
				return "MENU_STORE_BAR_" + item.getMaxLevel() + "_LIGHT";
			}
		};
		
		int i;
		for(i = 1; i < item.getMaxLevel(); i++) {
			barTable.add(new Image(resources.getDrawableTexture(
					i <= item.getCurrentLevel()? darkBarId : lightkBarId))).padRight(10);
		}
		// Add last bar without padding
		barTable.add(new Image(resources.getDrawableTexture(
				i <= item.getCurrentLevel()? darkBarId : lightkBarId)));
		
		add(barTable).colspan(2).padTop(20);
	}
}
