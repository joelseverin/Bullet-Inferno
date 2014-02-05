package se.dat255.bulletinferno.view.menu;

import java.util.HashMap;
import java.util.List;

import se.dat255.bulletinferno.model.store.StoreItem;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class StoreView extends SimpleToggleSubMenuView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int GLASS_WIDTH = 1182, GLASS_POSITION_X = 285;
	private final static int CELL_PADDING = 30;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private final Image header;
	private final Table mainTable;
	private final Stage stage;
	private final ScrollPane scrollPane;
	private final Table itemsTable;
	private final Label balanceLabel;
	private final HashMap<StoreItem, StoreItemView> items = new HashMap<StoreItem, StoreItemView>();
	
	public StoreView(Stage stage, ResourceManager resources, List<StoreItem> storeItems, 
			int coinBalance) {
		super(new Group(), GLASS_WIDTH, VIRTUAL_HEIGHT, GLASS_POSITION_X, VIRTUAL_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;
		
		// TODO : Replace with skin
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myraidpro.ttf"));
		BitmapFont nameFont = generator.generateFont(32);
		generator.dispose();
		
		header = new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_STORE_HEADER));
		
		mainTable = new Table();
		mainTable.setFillParent(true);
		mainTable.setBackground(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_EXTENSION_GLASS));
		mainTable.top();
		mainTable.padTop(100);
		mainTable.add(header).colspan(2).padBottom(20);
		mainTable.row();
		
		mainTable.add(
				new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_STORE_COIN_LARGE))
				).right().padRight(15);
		balanceLabel = new Label(Integer.toString(coinBalance), 
				new Label.LabelStyle(nameFont, Color.valueOf("4c7d7d")));
		mainTable.add(balanceLabel).left();
		mainTable.row();
		
		StoreItemView itemView;
		itemsTable = new Table();
		itemsTable.top();
		for(StoreItem item : storeItems) {
			itemView = new StoreItemView(item, resources);
			items.put(item, itemView);
			itemsTable.add(itemView).padBottom(CELL_PADDING);
			itemsTable.row();
		}
		
		scrollPane = new ScrollPane(itemsTable);
		mainTable.add(scrollPane).top().height(700).padTop(30).colspan(2);
		
		getToggleActor().addActor(mainTable);
		slideToggle();
		this.stage.addActor(getToggleActor());
	}
	
	public void updateBalance(int balance) {
		balanceLabel.setText(Integer.toString(balance));
	}
	
	/**
	 * Adds a listener to the buy button for the specified StoreItem
	 * @param key StoreItem
	 * @param listener
	 * @return success or failure
	 */
	public boolean addBuyButtonListener(StoreItem key, EventListener listener) {
		if(items.containsKey(key)) {
			items.get(key).addBuyButtonListener(listener);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Removes the specified listener from the buy button for the specified StoreItem
	 * @param key StoreItem
	 * @param listener
	 * @return success or failure
	 */
	public boolean removeBuyButtonListener(StoreItem key, EventListener listener) {
		if(items.containsKey(key)) {
			items.get(key).removeBuyButtonListener(listener);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		stage.getRoot().removeActor(getToggleActor());
		
		for(StoreItemView view : items.values()) {
			view.clear();
		}
	}

}
