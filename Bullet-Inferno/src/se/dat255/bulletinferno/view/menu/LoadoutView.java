package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LoadoutView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int TABLE_X = 390, GLASS_WIDTH = 1113;
	
	private final ResourceManager resources;
	private final Stage stage;
	private final Table table;
	private final MenuBackgroundView backgroundView;
	private final Button standardWeaponButton, heavyWeaponButton;
	private final Button passiveAbilityButton, specialAbilityButton;
	private final Button doneButton;
	
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
		table.row();

		Group loadoutGroup = new Group();
		loadoutGroup.setSize(900, 470);
		ButtonStyle bigButtonStyle = new ButtonStyle(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_BIG),
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_BIG_ACTIVE),
				null);
		
		standardWeaponButton = new Button(bigButtonStyle);
		standardWeaponButton.setPosition(600, 255);
		Image standardLabel = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_STANDARD_LABEL));
		standardLabel.setPosition(standardWeaponButton.getX() + 12, 
				standardWeaponButton.getY() + standardWeaponButton.getHeight() + 10);
		loadoutGroup.addActor(standardLabel);
		loadoutGroup.addActor(standardWeaponButton);
		
		
		heavyWeaponButton = new Button(bigButtonStyle);
		heavyWeaponButton.setPosition(600, 35);
		Image heavyLabel = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_HEAVY_LABEL));
		heavyLabel.setPosition(heavyWeaponButton.getX() + 40, heavyWeaponButton.getY() - 30);
		loadoutGroup.addActor(heavyLabel);
		loadoutGroup.addActor(heavyWeaponButton);
		
		ButtonStyle smallButtonStyle = new ButtonStyle(
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_SMALL),
				resources.getDrawableTexture(
						TextureDefinitionImpl.LOADOUTMENU_SELECTOR_BUTTON_SMALL_ACTIVE),
				null);
		
		specialAbilityButton = new Button(smallButtonStyle);
		specialAbilityButton.setPosition(0, 255);
		Image specialLabel = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_SPECIAL_LABEL));
		specialLabel.setPosition(specialAbilityButton.getX() + 5, 
				specialAbilityButton.getY() + specialAbilityButton.getHeight() + 10);
		loadoutGroup.addActor(specialAbilityButton);
		loadoutGroup.addActor(specialLabel);
		
		passiveAbilityButton = new Button(smallButtonStyle);
		passiveAbilityButton.setPosition(0, 35);
		Image passiveLabel = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_PASSIVE_LABEL));
		passiveLabel.setPosition(passiveAbilityButton.getX() + 3, passiveAbilityButton.getY() - 30);
		loadoutGroup.addActor(passiveAbilityButton);
		loadoutGroup.addActor(passiveLabel);
		
		table.add(loadoutGroup).padTop(50);
		table.row();
		
		doneButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_DONE_BUTTON), 
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_DONE_BUTTON_DOWN));
		table.add(doneButton).padTop(140);
		
		table.debug();
		stage.addActor(table);
	}

	@Override
	public void dispose() {
		stage.getRoot().removeActor(table);
		backgroundView.dispose();
		doneButton.clear();
	}

}
