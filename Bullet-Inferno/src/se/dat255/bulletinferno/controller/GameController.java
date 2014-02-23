package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.ModelEnvironment;
import se.dat255.bulletinferno.model.ModelEnvironmentImpl;
import se.dat255.bulletinferno.model.entity.Enemy;
import se.dat255.bulletinferno.model.entity.PlayerShip;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.SpecialAbilityDefinition;
import se.dat255.bulletinferno.model.weapon.WeaponDefinition;
import se.dat255.bulletinferno.util.GameActionEvent;
import se.dat255.bulletinferno.util.Listener;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.BackgroundView;
import se.dat255.bulletinferno.view.EnemyView;
import se.dat255.bulletinferno.view.PlayerShipView;
import se.dat255.bulletinferno.view.ProjectileView;
import se.dat255.bulletinferno.view.audio.AudioPlayer;
import se.dat255.bulletinferno.view.audio.AudioPlayerImpl;
import se.dat255.bulletinferno.view.gui.HudView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * The main controller of the game, handles main initiation and update of time
 */
public class GameController extends SimpleController {

	/**
	 * Handles all the graphics with the game.<br>
	 * Also handles converting between <b>world</b> and <b>local</b> positions.
	 */
	private Graphics graphics;

	/** The current session instance of the game model. */
	private ModelEnvironment models;

	/** If the player died; Should not update the game */
	private boolean gameOver;

	/** The (center of the) current viewport position, in world coordinates */
	private Vector2 viewportPosition;

	/** The current viewport dimensions, in world coordinates. */
	private Vector2 viewportDimensions;

	/** Stores the weapons type for restarting the game */
	private WeaponDefinition[] weaponData;

	/** Reference to the master controller */
	private final MasterController myGame;

	/** Reference to the background view */
	private BackgroundView bgView;

	private final AudioPlayer audioPlayer;

	/** Reference to the main resource manager of the game */
	private final ResourceManager resourceManager;

	/** Reference to the shared special ability */
	private SpecialAbilityDefinition special;
	/** Reference to the shared passive ability definition */
	private PassiveAbilityDefinition passive;

	private PauseMenuController pauseController;
	private HudView hudView;
	private Stage hudStage;
	private PlayerShip ship;
	private final InputMultiplexer inputMultiplexer;
	private final GameTouchController touchController = new GameTouchController();
	
	/**
	 * Default controller to set required references
	 * 
	 * @param myGame
	 *        The master controller that creates this controller
	 * @param resourceManager
	 *        the resource manager instance.
	 */
	public GameController(final MasterController myGame, ResourceManager resourceManager) {
		this.myGame = myGame;
		this.resourceManager = resourceManager;
		
		pauseController = new PauseMenuController(myGame, this, resourceManager);
		audioPlayer = new AudioPlayerImpl(resourceManager);
		
		hudStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		hudView = new HudView(hudStage, resourceManager);
		hudView.addPauseButtonListener(pauseButtonListener);
		hudView.addSpecialAbilityButtonListener(specialAbilityButtonListener);
		
		inputMultiplexer = new InputMultiplexer(hudStage, touchController);
	}

	/**
	 * Creates or recreates a game "state". This method should be called before switching to the
	 * GameScreen.
	 */
	public void createNewGame(WeaponDefinition[] weaponData, SpecialAbilityDefinition special,
			PassiveAbilityDefinition passive) {
		// Initiate instead of declaring statically above
		viewportPosition = new Vector2();
		viewportDimensions = new Vector2();
		this.weaponData = weaponData;
		this.special = special;
		this.passive = passive;

		// Clear previous state
		if (graphics != null) {
			graphics.dispose();
			graphics = null;
		}

		if (models != null) {
			models.dispose();
			models = null;
		}

		// Set up the model environment with the provided weaponData, includes creating the player
		// ship.
		models = new ModelEnvironmentImpl(weaponData, enemyActionListener);
		ship = models.getPlayerShip();
		
		// Initialize the graphics controller
		graphics = new Graphics();
		graphics.initialize();
		
		// Apply the passive ability to the ship
		passive.getPassiveAbility().getEffect().applyEffect(ship);

		// Set up the bg view, rendering the segments
		bgView = new BackgroundView(models, resourceManager, ship);
		graphics.addRenderable(bgView);
		
		PlayerShipView shipView = new PlayerShipView(ship, resourceManager);
		graphics.addRenderable(shipView);

		EnemyView enemyView = new EnemyView(models, resourceManager);
		graphics.addRenderable(enemyView);

		ProjectileView projectileView = new ProjectileView(models, resourceManager);
		graphics.addRenderable(projectileView);
		
		// Set the new graphics and ship in the touch controller
		touchController.setGraphics(graphics);
		touchController.setPlayerShip(ship);
	}

	/** The player has died, the game is over */
	public void gameOver() {
		gameOver = true;
		touchController.setSuppressKeyboard(true);
	}

	/**
	 * Do nothing when application resumes, let the user resume the game. Unless the current game is
	 * over, in which case keep track of the pause state of the app.
	 */
	@Override
	public void resume() {
		if (gameOver) {
			super.resume();
		}
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void dispose() {
		audioPlayer.dispose();
		pauseController.dispose();
		hudStage.dispose();
		hudView.dispose();
		touchController.dispose();
		
		if(graphics != null) {
			graphics.dispose();
		}
		if (models != null) {
			models.dispose();
		}
	}

	/**
	 * Main game entry loop. Called every frame and should update logic etc.
	 */
	@Override
	public void render(float delta) {
		graphics.setNewCameraPos(models.getPlayerShip().getPosition().x -
				models.getPlayerShip().getDimensions().x / 2 +
				Graphics.GAME_WIDTH / 2,
				Graphics.GAME_HEIGHT / 2);

		// Render the game
		graphics.render();
		
		// Debug render
		// graphics.renderWithDebug(models.getPhysicsEnvironment());
		
		hudView.setScore(ship.getScore());
		hudView.setHealth(ship.getHealth());
		hudStage.draw();
		
		if (!gameOver && models.getPlayerShip().isDead()) {
			gameOver();
		}

		// Only pause logic, rendering of GUI could still be needed
		if (!isPaused && !gameOver) {
			// Update models. This should be done after graphics rendering, so that
			// graphics commands
			// can be buffered up for being sent to the graphics pipeline.
			// Meanwhile, we run the models.

			// Calculate the new world coordinate position (in the middle) of the viewport.
			viewportPosition = new Vector2(0, 0);
			graphics.screenToWorld(viewportPosition);
			viewportPosition.add(0.5f * viewportDimensions.x, 0);
			viewportPosition.sub(0, 0.5f * viewportDimensions.y);

			models.setViewport(viewportPosition, viewportDimensions);

			models.update(delta);
		}
		
		hudStage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		graphics.resize(width, height);

		// Get bottom left (at zero, world origo) and top right corners for further calculation...
		viewportPosition = new Vector2(0, height);
		graphics.screenToWorld(viewportPosition);
		viewportDimensions = new Vector2(width, 0);
		graphics.screenToWorld(viewportDimensions);

		// ...adjust dimensions to bottom left corner...
		viewportDimensions.sub(viewportPosition);

		// ...adjust position to being in the middle of the viewport...
		viewportPosition.add(0.5f * viewportDimensions.x, 0.5f * viewportDimensions.y);

		models.setViewport(viewportPosition, viewportDimensions);
	}

	/** Gets the game background view */
	public BackgroundView getBgView() {
		return bgView;
	}

	/** Get method for weapon data set in create new game */
	public WeaponDefinition[] getWeaponData() {
		return weaponData;
	}

	/** Get method for data set in create new game */
	public SpecialAbilityDefinition getSpecial() {
		return special;
	}

	/** Get method for data set in create new game */
	public PassiveAbilityDefinition getPassive() {
		return passive;
	}
	
	private ChangeListener pauseButtonListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			myGame.setScreen(pauseController);
		}
	};
	
	private ChangeListener specialAbilityButtonListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			special.getSpecialAbility(models).getEffect().activate(ship);
		}
	};
	
	private Listener<GameActionEvent<Enemy>> enemyActionListener = 
			new Listener<GameActionEvent<Enemy>>() {
		@Override
		public void call(GameActionEvent<Enemy> e) {
			audioPlayer.playSoundEffect(e);
		}
	};
}
