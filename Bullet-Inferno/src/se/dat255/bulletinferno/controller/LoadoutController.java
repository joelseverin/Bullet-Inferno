package se.dat255.bulletinferno.controller;

import java.util.LinkedList;
import java.util.List;

import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinitionImpl;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinitionImpl;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.LoadoutView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LoadoutController extends SimpleController {
	private final Stage stage;

	private final MasterController masterController;
	private final ResourceManager resources;

	private final LoadoutView view;
	
	/**
	 * Main controller used for the loadout screen
	 * 
	 * @param masterController
	 *        The master controller that creates this screen
	 * @param resourceManager
	 */
	public LoadoutController(final MasterController masterController,
			final ResourceManager resourceManager) {
		this.resources = resourceManager;
		this.masterController = masterController;

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

		// TODO : Replace with avalible weapons for the user
		List<WeaponDefinition> standard = new LinkedList<WeaponDefinition>();
		standard.add(WeaponDefinitionImpl.STANDARD_MACHINE_GUN);
		standard.add(WeaponDefinitionImpl.STANDARD_MINI_GUN);
		standard.add(WeaponDefinitionImpl.STANDARD_PLASMA_GUN);
		
		List<WeaponDefinition> heavy = new LinkedList<WeaponDefinition>();
		heavy.add(WeaponDefinitionImpl.HEAVY_EGG_CANNON);
		heavy.add(WeaponDefinitionImpl.HEAVY_LASER_CANNON);
		
		List<PassiveAbilityDefinition> passive = new LinkedList<PassiveAbilityDefinition>();
		passive.add(PassiveAbilityDefinitionImpl.LOADOUT_PASSIVE_DAMAGE_MODIFIER);
		passive.add(PassiveAbilityDefinitionImpl.LOADOUT_PASSIVE_RELOADING_TIME);
		passive.add(PassiveAbilityDefinitionImpl.LOADOUT_PASSIVE_TAKE_DAMAGE_MODIFIER);
		
		List<SpecialAbilityDefinition> special = new LinkedList<SpecialAbilityDefinition>();
		special.add(SpecialAbilityDefinitionImpl.LOADOUT_SPECIAL_NUKE);
		special.add(SpecialAbilityDefinitionImpl.LOADOUT_SPECIAL_PROJECTILE_RAIN);
		
		view = new LoadoutView(resourceManager, stage, standard, heavy, passive, special);
		
		view.getStandardWeaponButton().addListener(standardButtonListener);
		view.getHeavyWeaponButton().addListener(heavyButtonListener);
		view.getSpecialAbilityButton().addListener(specialAbilityListener);
		view.getPassiveAbilityButton().addListener(passiveAbilityListener);
		
		view.getStandardWeaponSelector().addListener(standardSelectorListener);
		view.getHeavyWeaponSelector().addListener(heavySelectorListener);
		view.getPassiveAbilitySelector().addListener(passiveSelectorListener);
		view.getSpecialAbilitySelector().addListener(specialSelectorListener);
		
		view.getStandardWeaponButton().setGearImage(resources, standard.get(0));
		view.getHeavyWeaponButton().setGearImage(resources, heavy.get(0));
		view.getSpecialAbilityButton().setGearImage(resources, special.get(0));
		view.getPassiveAbilityButton().setGearImage(resources, passive.get(0));
	}


	@Override
	public void show() {
		Gdx.app.debug("LoadoutScreen", "Screen shown");
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		// Clear the screen every frame
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		//stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
	}

	@Override
	public void dispose() {
		Gdx.app.debug("LoadoutScreen", "Screen Disposed");
		stage.dispose();
		view.dispose();
	}
	
	public void startGame(WeaponDefinition[] weapons, SpecialAbilityDefinition special,
			PassiveAbilityDefinition passive) {
		System.gc();
		GameController gameController = new GameController(masterController, resources);
		gameController.createNewGame(weapons, special, passive);
		masterController.setScreen(gameController);
	}

	public class StartButtonClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {

				WeaponDefinition[] weapons = new WeaponDefinition[] { 
						view.getStandardWeaponSelector().getSelected(), 
						view.getHeavyWeaponSelector().getSelected() };
				startGame(weapons, view.getSpecialAbilitySelector().getSelected(), 
						view.getPassiveAbilitySelector().getSelected());
		}
	}
	
	private ClickListener standardButtonListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			view.toggleStandardWeaponSelector();
		}
	};
	
	private ClickListener heavyButtonListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			view.toggleHeavyWeaponSelector();
		}
	};
	
	private ClickListener passiveAbilityListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			view.togglePassiveAbilitySelector();
		}
	};
	
	private ClickListener specialAbilityListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y)  {
			view.toggleSpecialAbilitySelector();
		}
	};
	
	private ChangeListener standardSelectorListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			view.getStandardWeaponButton().setGearImage(resources, 
					view.getStandardWeaponSelector().getSelected());
		}
	};
	
	private ChangeListener heavySelectorListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			view.getHeavyWeaponButton().setGearImage(resources, 
					view.getHeavyWeaponSelector().getSelected());
		}
	};
	
	private ChangeListener passiveSelectorListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			view.getPassiveAbilityButton().setGearImage(resources, 
					view.getPassiveAbilitySelector().getSelected());
		}
	};
	
	private ChangeListener specialSelectorListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			view.getSpecialAbilityButton().setGearImage(resources, 
					view.getSpecialAbilitySelector().getSelected());
		}
	};
}
