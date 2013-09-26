package se.dat255.bulletinferno;

import se.dat255.bulletinferno.controller.Touch;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.GameImpl;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.PlayerShipImpl;
import se.dat255.bulletinferno.model.RenderableGUI;
import se.dat255.bulletinferno.model.enemy.EnemyTypes;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.view.EnemyView;
import se.dat255.bulletinferno.view.ProjectileView;
import se.dat255.bulletinferno.view.ShipView;
import se.dat255.bulletinferno.view.gui.PauseIconView;
import se.dat255.bulletinferno.view.gui.PauseScreenView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {

	/**
	 * Handles all the graphics with the game.<br>
	 * Also handles converting between <b>world</b> and <b>local</b> positions.
	 */
	private Graphics graphics;

	/**
	 * The touch input handler
	 */
	private InputProcessor processor;

	/** The current session instance of the game model. */
	private Game game = null;

	/** If the game is paused; Should not update the game */
	private boolean gamePaused;

	/** The views to use when going in or out pause */
	private RenderableGUI pauseScreenView, pauseIconView;
	
	/** The (center of the) current viewport position, in world coordinates */
	private Vector2 viewportPosition = new Vector2();
	
	/** The current viewport dimensions, in world coordinates. */
	private Vector2 viewportDimensions = new Vector2();

	private MyGame myGame;

	public GameScreen(MyGame myGame) {
		this.myGame = myGame;
	}

	/**
	 * Creates or recreates a game "state". This method should be called before switching to the
	 * GameScreen.
	 * 
	 */
	public void createNewGame(WeaponData weaponType) {
		Gdx.app.log("GameScreen", "createNewGame, weaponType = " + weaponType);
		
		
		if(graphics != null ) {
			graphics.dispose();
			graphics = null;
		}
		
		// TODO: Initialize the game
		graphics = new Graphics();
		graphics.create();

		// TODO: should probably not be created here
		// Set up the player ship, view and add it to gfx.

		game = new GameImpl();

		PlayerShip ship = new PlayerShipImpl(game, new Vector2(0, 0), 100,
				weaponType.getPlayerWeaponForGame(game));
		ShipView shipView = new ShipView(ship);
		graphics.addRenderable(shipView);

		// Set up input handler
		processor = new Touch(game, graphics, ship);

		// TODO: Move the gui setup to when the player enters a level
		setupGUI();

		// TODO: Debug test spawn enemy to draw in world coord
		setupHardcodedEnemies();

		// TODO: Debug test add bullet
		// ProjectileImpl projectile = new ProjectileImpl(null);
		// projectile.setPosition(new Vector2(5, 7));
		ProjectileView projectileView = new ProjectileView(game);
		graphics.addRenderable(projectileView);
	}

	/** Initiates the pause components when the player starts a level */
	private void setupGUI() {
		pauseIconView = new PauseIconView(this);
		pauseScreenView = new PauseScreenView(this);
		graphics.addRenderableGUI(pauseIconView);
	}

	/** Pauses the game */
	public void pauseGame() {
		gamePaused = true;
		graphics.removeRenderableGUI(pauseIconView);
		graphics.addRenderableGUI(pauseScreenView);
	}

	/** Unpauses the game */
	public void unpauseGame() {
		gamePaused = false;
		graphics.removeRenderableGUI(pauseScreenView);
		graphics.addRenderableGUI(pauseIconView);
	}

	private void setupHardcodedEnemies() {
		Vector2 position = new Vector2(16 - 1, 9 / 3f * 1 - 2);
		Vector2 position2 = new Vector2(16 - 1, 9 / 3f * 2 - 2);
		Vector2 position3 = new Vector2(16 - 1, 9 / 3f * 3 - 2);

		game.addEnemy(EnemyTypes.DEFAULT_SHIP.getEnemyShip(game, position));
		game.addEnemy(EnemyTypes.SLOW_SHIP.getEnemyShip(game, position2));
		game.addEnemy(EnemyTypes.FAST_SHIP.getEnemyShip(game, position3));

		EnemyView enemyView = new EnemyView(game);

		graphics.addRenderable(enemyView);
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(processor);
	}

	@Override
	public void dispose() {
		graphics.dispose();
		
		if(game != null) {
			game.dispose();
		}
	}

	/**
	 * Main game entry loop. Called every frame and should update logic etc.
	 */
	@Override
	public void render(float delta) {
		super.render(delta);

		// Render the game
		graphics.render();

		// Only pause logics, rendering of GUI could still be needed
		if (!gamePaused) {
			// Update models. This should be done after graphics rendering, so that
			// graphics commands
			// can be buffered up for being sent to the graphics pipeline.
			// Meanwhile, we run the models.
			
			// Calculate the new world coordinate position (in the middle) of the viewport.
			viewportPosition = new Vector2(0, 0);
			graphics.screenToWorld(viewportPosition);
			viewportPosition.add(0.5f*viewportDimensions.x, 0);
			viewportPosition.sub(0, 0.5f*viewportDimensions.y);
			
			game.getPhysicsWorld().setViewport(viewportPosition, viewportDimensions);
			
			game.update(delta);
		}
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

		game.getPhysicsWorld().setViewport(viewportPosition, viewportDimensions);
	}

}