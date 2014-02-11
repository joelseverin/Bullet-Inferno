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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class LoadoutController extends SimpleController {
	private final Stage stage;

	private final MasterController masterController;
	private final ResourceManager resourceManager;


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
		this.resourceManager = resourceManager;
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
		view = new LoadoutView(resourceManager, stage, standard, new LinkedList<Descriptable>(),
				new LinkedList<Descriptable>(), new LinkedList<Descriptable>());
		view.toggleStandardWeaponSelector();
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
		//System.gc();
		GameController gameController = new GameController(masterController, resourceManager);
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
}
