package se.dat255.bulletinferno.view.menu;

import java.util.List;

import javax.swing.event.ChangeEvent;

import se.dat255.bulletinferno.util.Descriptable;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;
import se.dat255.bulletinferno.view.menu.widget.LoadoutGearButton;
import se.dat255.bulletinferno.view.menu.widget.LoadoutSelector;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class LoadoutView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int TABLE_X = 390, GLASS_WIDTH = 1113;
	private final static float SLIDE_ANIMATION_DURATION = 0.4f;
	
	private final ResourceManager resources;
	private final Stage stage;
	private final Table table, extensionTable;
	private final MenuBackgroundView backgroundView;
	private final LoadoutGearButton standardWeaponButton, heavyWeaponButton;
	private final LoadoutGearButton passiveAbilityButton, specialAbilityButton;
	private final Button doneButton;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final LoadoutSelector standardSelector, heavySelector, passiveSelector, specialSelector;
	private final Image chooseLabel, standardSelectorLabel, heavySelectorLabel;
	private final Image passiveSelectorLabel, specialSelectorLabel;
	
	private LoadoutSelector activeSelector = null;
	private Image activeSelectorLabel = null;
	private boolean isExtensionTabledown = false;
	
	public LoadoutView(ResourceManager resources, Stage stage, 
			List<Descriptable> standardWeaponOptions,
			List<Descriptable> heavyWeaponOptions,
			List<Descriptable> passiveAbilityOptions,
			List<Descriptable> specialAbilityOptions) {
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
		
		// Setup labels
		table.add(new Image(resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_HEADER))
					).padTop(120);
		table.row();
		table.add(new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_CLICK_INSTRUCTIONS)
				)).padTop(20);
		table.row();

		// Setup the buttons
		Group loadoutGroup = new Group();
		loadoutGroup.setSize(900, 470);
		ButtonStyle bigButtonStyle = new ButtonStyle(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_BIG),
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_BIG_ACTIVE),
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_BIG_ACTIVE));
		
		standardWeaponButton = new LoadoutGearButton(bigButtonStyle);
		standardWeaponButton.setPosition(600, 255);
		Image standardLabel = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_STANDARD_LABEL));
		standardLabel.setPosition(standardWeaponButton.getX() + 12, 
				standardWeaponButton.getY() + standardWeaponButton.getHeight() + 10);
		loadoutGroup.addActor(standardLabel);
		loadoutGroup.addActor(standardWeaponButton);
		buttonGroup.add(standardWeaponButton);
		
		heavyWeaponButton = new LoadoutGearButton(bigButtonStyle);
		heavyWeaponButton.setPosition(600, 35);
		Image heavyLabel = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_HEAVY_LABEL));
		heavyLabel.setPosition(heavyWeaponButton.getX() + 40, heavyWeaponButton.getY() - 30);
		loadoutGroup.addActor(heavyLabel);
		loadoutGroup.addActor(heavyWeaponButton);
		buttonGroup.add(heavyWeaponButton);
		
		ButtonStyle smallButtonStyle = new ButtonStyle(
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_SMALL),
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_SMALL_ACTIVE),
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_SMALL_ACTIVE));
		
		specialAbilityButton = new LoadoutGearButton(smallButtonStyle);
		specialAbilityButton.setPosition(0, 255);
		Image specialLabel = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_SPECIAL_LABEL));
		specialLabel.setPosition(specialAbilityButton.getX() + 5, 
				specialAbilityButton.getY() + specialAbilityButton.getHeight() + 10);
		loadoutGroup.addActor(specialAbilityButton);
		loadoutGroup.addActor(specialLabel);
		buttonGroup.add(specialAbilityButton);
		
		passiveAbilityButton = new LoadoutGearButton(smallButtonStyle);
		passiveAbilityButton.setPosition(0, 35);
		Image passiveLabel = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_PASSIVE_LABEL));
		passiveLabel.setPosition(passiveAbilityButton.getX() + 3, passiveAbilityButton.getY() - 30);
		loadoutGroup.addActor(passiveAbilityButton);
		loadoutGroup.addActor(passiveLabel);
		buttonGroup.add(passiveAbilityButton);
		
		table.add(loadoutGroup).padTop(50);
		table.row();
		
		// Setup done button
		doneButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_DONE_BUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_DONE_BUTTON_DOWN));
		table.add(doneButton).padTop(140);
		
		// Setup the extension table and it's selectors
		extensionTable = new Table();
		extensionTable.setBackground(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_GLASS_SELECT));
		extensionTable.setSize(extensionTable.getBackground().getMinWidth(), VIRTUAL_HEIGHT);
		extensionTable.setPosition(VIRTUAL_WIDTH - extensionTable.getWidth(), VIRTUAL_HEIGHT);
		extensionTable.top();
		
		standardSelector = new LoadoutSelector(resources, standardWeaponOptions);
		heavySelector = new LoadoutSelector(resources, heavyWeaponOptions);
		passiveSelector = new LoadoutSelector(resources, passiveAbilityOptions);
		specialSelector = new LoadoutSelector(resources, specialAbilityOptions);
		
		chooseLabel = new Image(
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_CHOOSA_LABEL));
		standardSelectorLabel = new Image(
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_STANDARD_LABEL));
		heavySelectorLabel = new Image(
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_HEAVY_LABEL));
		passiveSelectorLabel = new Image(
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_PASSIVE_LABEL));
		specialSelectorLabel = new Image(
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_SPECIAL_LABEL));
		
		extensionTable.add(chooseLabel).padTop(64);
		extensionTable.row();
		extensionTable.add(standardSelectorLabel).padTop(12);
		extensionTable.row();
		extensionTable.add(standardSelector).width(extensionTable.getWidth() - 50).padTop(20);
		
		stage.addActor(extensionTable);
		stage.addActor(table);
	}

	@Override
	public void dispose() {
		stage.getRoot().removeActor(table);
		backgroundView.dispose();
		doneButton.clear();
	}

	public void toggleStandardWeaponSelector() {
		if(activeSelector == standardSelector) {
			extensionTable.addAction(isExtensionTabledown? slideUpAction() : slideDownAction());
		} else {
			switchActiveSelector(standardSelector, standardSelectorLabel);
		}
	}
	
	public void toggleHeavyWeaponSelector() {
		if(activeSelector == heavySelector) {
			extensionTable.addAction(isExtensionTabledown? slideUpAction() : slideDownAction());
		} else {
			switchActiveSelector(heavySelector, heavySelectorLabel);
		}
	}
	
	public void togglePassiveAbilitySelector() {
		if(activeSelector == passiveSelector) {
			extensionTable.addAction(isExtensionTabledown? slideUpAction() : slideDownAction());
		} else {
			switchActiveSelector(passiveSelector, passiveSelectorLabel);
		}
	}
	
	public void toggleSpecialAbilitySelector() {
		if(activeSelector == specialSelector) {
			extensionTable.addAction(isExtensionTabledown? slideUpAction() : slideDownAction());
		} else {
			switchActiveSelector(specialSelector, specialSelectorLabel);
		}
	}
	
	public LoadoutSelector getStandardWeaponSelector() {
		return standardSelector;
	}
	
	public LoadoutSelector getHeavyWeaponSelector() {
		return heavySelector;
	}
	
	public LoadoutSelector getSpecialAbilitySelector() {
		return specialSelector;
	}
	
	public LoadoutSelector getPassiveAbilitySelector() {
		return passiveSelector;
	}
	
	public LoadoutGearButton getStandardWeaponButton() {
		return standardWeaponButton;
	}
	
	public LoadoutGearButton getHeavyWeaponButton() {
		return heavyWeaponButton;
	}
	
	public LoadoutGearButton getPassiveAbilityButton() {
		return passiveAbilityButton;
	}
	
	public LoadoutGearButton getSpecialAbilityButton() {
		return specialAbilityButton;
	}
	
	private void switchActiveSelector(final LoadoutSelector selector, final Image selectorLabel) {
		Action switchAction =  new Action() {
			@Override
			public boolean act(float arg0) {
				extensionTable.swapActor(activeSelector, selector);
				extensionTable.swapActor(activeSelectorLabel, selectorLabel);
				activeSelector = selector;
				activeSelectorLabel = selectorLabel;
				return true;
			}
		};
		
		if(isExtensionTabledown) {
			extensionTable.addAction(Actions.sequence(slideUpAction(), switchAction, 
					slideDownAction()));
		} else {
			extensionTable.addAction(Actions.sequence(switchAction, slideDownAction()));
		}
	}
	
	private Action slideDownAction() {
		isExtensionTabledown = true;
		return Actions.moveTo(extensionTable.getX(), 0, SLIDE_ANIMATION_DURATION);
	}
	
	private Action slideUpAction() {
		return Actions.sequence(Actions.moveTo(extensionTable.getX(), SLIDE_ANIMATION_DURATION), 
				new Action() {
					@Override
					public boolean act(float arg0) {
						isExtensionTabledown = false;
						return true;
					}
				});
	}
}
