package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.entity.PlayerShip;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * The main touch controller<br>
 * Information on development of LibGDX input handling:<br>
 * https://code.google.com/p/libgdx/wiki/InputEvent
 */
public class GameTouchController implements InputProcessor, Disposable {
	/** The keyboard key to shot the heavy weapon */
	private static final int SHOT_KEY = Input.Keys.SPACE;
	/** The keyboard key to use the special ability */
	private static final int SPECIAL_ABILITY_KEY = Input.Keys.G;

	/** Describes the sense of the point device */
	private float SENSE_SCALE = 1f;
	/** The game camera. This is needed to un-project x/y values to the virtual screen size. */
	private Graphics graphics = new Graphics();
	/** Hard reference to the ship model. */
	private PlayerShip ship;

	/** The finger index controlling the position of the ship. */
	private int steeringFinger = -1;
	/** The origin of touch down finger controlling the ship */
	private final Vector2 touchOrigin = new Vector2();
	/** Flag indicating that keyboard presses should be ignored */
	private boolean suppressKeyboard;

	
	public GameTouchController() {
		
	}
	
	public GameTouchController(Graphics graphics, PlayerShip ship) {
		this.graphics = graphics;
		this.ship = ship;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (suppressKeyboard) {
			return true;
		}
		if (keycode == SHOT_KEY) {
			ship.fireWeapon();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return suppressKeyboard;
	}

	@Override
	public boolean keyTyped(char character) {
		return suppressKeyboard;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// Otherwise it's world input
		// Unproject the touch location to the virtual screen.
		Vector2 touchVector = new Vector2(screenX, screenY);
		graphics.screenToWorld(touchVector);

		if (touchVector.x <= ship.getPosition().x + 8f) {
			// Left half of the screen
			// Set the touchOrigin vector to know where the touch originated from
			touchOrigin.set(touchVector);
			steeringFinger = pointer;
		} else {
			// Right half of the screen
			ship.fireWeapon();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer == steeringFinger) {
			touchOrigin.set(new Vector2());
			steeringFinger = -1;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer == steeringFinger) {
			// Un-project the touch location to the virtual screen.
			Vector2 touchVector = new Vector2(screenX, screenY);
			graphics.screenToWorld(touchVector);
			if (touchVector.x <= ship.getPosition().x + 8f) {
				ship.moveY(touchVector.y - touchOrigin.y, SENSE_SCALE);
				touchOrigin.set(touchVector);
			}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void setSuppressKeyboard(boolean suppressKeyboard) {
		this.suppressKeyboard = suppressKeyboard;
	}
	
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	public void setPlayerShip(PlayerShip ship) {
		this.ship = ship;
	}

	@Override
	public void dispose() {
		ship = null;
		graphics = null;
	}

}