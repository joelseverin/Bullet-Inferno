package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.model.store.StoreItem;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * A view to display a store item. Shows the item's properties such as name, description, level,
 * price and image. Also contains a buy button if the item isn't already at its max level.
 * @author Sebastian Blomberg
 *
 */
public class StoreItemView extends Table {
	// Cache variables
	private static LabelStyle nameLabelStyle = null, infoLabelStyle = null;
	
	private final Table infoTable = new Table();
	private final Table barTable = new Table();
	private Button buyButton = null;
	
	/**
	 * Constructs a new item view. The texture/image to represent the item is expected to be
	 * found in the specified ResourceManager.
	 * @pre Item's identifier are expected to be found in the ResourceManager
	 * @param item The store item
	 * @param resources The ResourceManager
	 */
	public StoreItemView(final StoreItem item, ResourceManager resources) {
		add(new Image(resources.getDrawableTexture(item))).left().padLeft(10);
		
		if(nameLabelStyle == null) {
			nameLabelStyle = new Label.LabelStyle(resources.getSkin().getFont("myraid38"), 
					resources.getSkin().getColor("darkgreen"));
		}
		if(infoLabelStyle == null) {
			infoLabelStyle = new Label.LabelStyle(resources.getSkin().getFont("myraid34"), 
					resources.getSkin().getColor("darkgrey"));
			
		}
		
		infoTable.add(new Label(item.getName() + 
					(item.getCurrentLevel() < item.getMaxLevel()? 
							" Level " + (item.getCurrentLevel() + 1): ""), nameLabelStyle)).left();
		infoTable.add(new Image(
					resources.getDrawableTexture(TextureDefinitionImpl.MENU_STORE_COIN_MEDIUM))
				);
		infoTable.add(new Label(item.getUpgradeCost() + "", infoLabelStyle)).left();
		infoTable.row();
		
		if(item.getCurrentLevel() < item.getMaxLevel()) {
			Label l = new Label(item.getUpdrageDescription(), infoLabelStyle);
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
	
	/**
	 * Adds a listener to the buy button contained in this item view. 
	 * <strong>ONLY</strong> adds the listener if the button is actually contained in the view,
	 * i.e. if the item isn't at its max level.
	 * @param listener
	 */
	public void addBuyButtonListener(EventListener listener) {
		if(buyButton != null) {
			buyButton.addListener(listener);
		}
	}
	
	/**
	 * Removes the specified listener from the buy button contained in this view.
	 * @param listener
	 */
	public void removeBuyButtonListener(EventListener listener) {
		if(buyButton != null) {
			buyButton.removeListener(listener);
		}
	}
	
	@Override
	public void clearListeners() {
		super.clearListeners();
		buyButton.clearListeners();
	}
}
