package se.dat255.bulletinferno.controller;


import se.dat255.bulletinferno.controller.LoadingScreenController.FinishedLoadingEventListener;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.ResourceManagerImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;

/**
 * The master controller is called every frame. It then calls appropriate classes
 * depending on what currently is active in the game. It handles all sub-controllers
 * and screens.
 */
public class MasterController extends com.badlogic.gdx.Game implements Disposable {
	/** Main controller for the load out screen */
	private LoadoutController loadoutScreen;
	/** Main controller for the game screen */
	private GameController gameScreen;

	
	private Screen queuedForDisposal = null;
	
	/** The controller for the loading screen */
	private LoadingScreenController loadingScreen;

	private ResourceManager resourceManager;
	

	@Override
	public void create() {
		resourceManager = new ResourceManagerImpl();

		// Show loadingscreen while loading and switch to main menu controller
		loadingScreen = new LoadingScreenController(resourceManager, this);
		loadingScreen.addFinishedLoadingEventListener(switchToMenu);
		loadingScreen.setClickToSwitch(true);
		setScreen(loadingScreen);
	}
	
	public void setScreenWithDisposal(Controller controller) {
		if(getScreen() != null && getScreen() != loadingScreen) {
			queuedForDisposal = getScreen();
		}
		setScreen(controller);
	}
	
	@Override
	public void render() {
		super.render();
		if(queuedForDisposal != null) {
			queuedForDisposal.dispose();
			queuedForDisposal = null;
		}
	}
	
	@Override
	public void dispose() {
		if (loadoutScreen != null) {
			loadoutScreen.dispose();
		}
		if (resourceManager != null) {
			resourceManager.dispose();
		}
		getScreen().dispose();

	}
	
	/**
	 * Starts a new game and changes the screen to that game
	 * 
	 * @param passive
	 * @param special
	 */
	public void startGame(GameController gameScreen, WeaponDefinition[] weaponData,
			SpecialAbilityDefinition special, PassiveAbilityDefinition passive, boolean fromLoadout) {

		if (weaponData == null) {
			weaponData = gameScreen.getWeaponData();
		}

		if (!fromLoadout) {
			if (gameScreen != null) {
				gameScreen.dispose();
			}
			gameScreen = new GameController(this, resourceManager);
		}

		gameScreen.createNewGame(weaponData, special, passive);
		this.gameScreen = gameScreen;

		setScreen(gameScreen);
	}

	public GameController getGameScreen() {
		return gameScreen;
	}

	public LoadoutController getLoadoutScreen() {
		if (loadoutScreen == null) {
			loadoutScreen = new LoadoutController(this, resourceManager);
		}
		return loadoutScreen;
	}

	
	@Override
	public void resume() {
		if (getScreen() != loadingScreen && !resourceManager.loadAsync()) {
			loadingScreen.setClickToSwitch(false);
			loadingScreen.addFinishedLoadingEventListener(
					new FinishedLoadingEventListenerImpl(getScreen()));
			setScreen(loadingScreen);
		}
	}
	
	private class FinishedLoadingEventListenerImpl implements FinishedLoadingEventListener {
		private Screen screen;
		public FinishedLoadingEventListenerImpl(Screen screen) {
			this.screen = screen;
		}
		@Override
		public void onLoaded() {
			setScreen(screen);
		}
	}
	
	// Required since the menu view tires to load assets on construct and therefore the above class
	// can't be used to handle FinishedLoadingEvent for the loading screen, to load the menu
	private final FinishedLoadingEventListener switchToMenu = new FinishedLoadingEventListener() {
		@Override
		public void onLoaded() {
			setScreen(new MainMenuController(MasterController.this, resourceManager));
		}
	};
	
	public static Preferences getUserDefaults() {
		return Gdx.app.getPreferences("bulletinferno_default");
	}
}
