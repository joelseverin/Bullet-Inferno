package se.dat255.bulletinferno.controller;

import java.util.LinkedList;
import java.util.List;

import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinitionImpl;
import se.dat255.bulletinferno.util.Descriptable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.LoadoutView;
import se.dat255.bulletinferno.view.menu.PassiveButtonsView;
import se.dat255.bulletinferno.view.menu.SpecialButtonsView;
import se.dat255.bulletinferno.view.menu.WeaponButtonsView;

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


	private  WeaponButtonsView weaponButtonsView;
	private  SpecialButtonsView specialButtonsView;
	private  PassiveButtonsView passiveButtonsView;


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
		List<Descriptable> standard = new LinkedList<Descriptable>();
		standard.add(new Descriptable() {
			
			@Override
			public String getIdentifier() {
				return WeaponDefinitionImpl.STANDARD_MACHINE_GUN.getIdentifier();
			}
			
			@Override
			public String getName() {
				return "Machine gun";
			}
			
			@Override
			public String getDescription() {
				return "Damage : " + WeaponDefinitionImpl.STANDARD_MACHINE_GUN.getProjectileType().getDamage()
						+ "\nReloading time : " + WeaponDefinitionImpl.STANDARD_MACHINE_GUN.getReloadTime();
			}
		});
		
		standard.add(new Descriptable() {
					
			@Override
			public String getIdentifier() {
				return WeaponDefinitionImpl.STANDARD_PLASMA_GUN.getIdentifier();
			}
			
			@Override
			public String getName() {
				return "Laser Gun";
			}
			
			@Override
			public String getDescription() {
				return "Damage : " + WeaponDefinitionImpl.STANDARD_PLASMA_GUN.getProjectileType().getDamage()
						+ "\nReloading time : " + WeaponDefinitionImpl.STANDARD_PLASMA_GUN.getReloadTime();
			}
		});
		standard.add(standard.get(0));
		view = new LoadoutView(resourceManager, stage, standard, standard,
				new LinkedList<Descriptable>(), new LinkedList<Descriptable>());
		
		view.getStandardWeaponButton().addListener(standardButtonListener);
		view.getHeavyWeaponButton().addListener(heavyButtonListener);
		view.getSpecialAbilityButton().addListener(specialAbilityListener);
		view.getPassiveAbilityButton().addListener(passiveAbilityListener);
		
		view.getStandardWeaponSelector().addListener(standardSelectorListener);
		view.getHeavyWeaponSelector().addListener(heavySelectorListener);
		view.getPassiveAbilitySelector().addListener(passiveSelectorListener);
		view.getSpecialAbilitySelector().addListener(specialSelectorListener);
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
			WeaponDefinition standardWeapon = weaponButtonsView.getStandardSelectionButton()
					.getData();
			WeaponDefinition heavyWeapon = weaponButtonsView.getHeavySelectionButton().getData();
			SpecialAbilityDefinition special = specialButtonsView.getSelectionButton().getData();
			PassiveAbilityDefinition passive = passiveButtonsView.getSelectionButton().getData();

			WeaponDefinition[] weapons = new WeaponDefinition[] { standardWeapon, heavyWeapon };
			startGame(weapons, special, passive);
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
