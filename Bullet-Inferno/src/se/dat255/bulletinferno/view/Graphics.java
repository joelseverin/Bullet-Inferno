package se.dat255.bulletinferno.view;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * The main graphics handling of the game
 */
public class Graphics {

	/** 2D world camera */
	private OrthographicCamera worldCamera;
	/** Handles efficient drawing of several images */
	private SpriteBatch worldBatch;

	private Box2DDebugRenderer debugRenderer;

	/** The size, in meters, of the visible area. */
	public static final float GAME_WIDTH = 16f, GAME_HEIGHT = 9f;
	/** Inverted size, multiplication is faster than division */
	public static final float GAME_WIDTH_INVERTED = 1 / GAME_WIDTH,
			GAME_HEIGHT_INVERTED = 1 / GAME_HEIGHT;

	/** A vector that checks where the camera should be placed next update */
	private final Vector2 nextCameraPos = new Vector2();

	/** List of all objects that are to be rendered in the world */
	private final List<Renderable> renderables = new ArrayList<Renderable>();
	
	/**
	 * Initializes all the required assets
	 */
	public void initialize() {
		Texture.setEnforcePotImages(false);
		
		nextCameraPos.set(0, 0);
		worldCamera = new OrthographicCamera();
		worldBatch = new SpriteBatch();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void initializeWithDebug() {
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawBodies(true);
		initialize();
	}

	/**
	 * Sets the new size of the view field when the screen changes size
	 */
	public void resize(float w, float h) {
		float width = w / h * GAME_HEIGHT;
		worldCamera.setToOrtho(false, width, GAME_HEIGHT);
		worldCamera.update(true);
	}

	/**
	 * Releases the assets when called
	 */
	public void dispose() {
		if(worldBatch != null) {
			worldBatch.dispose();
		}
		if(debugRenderer != null) {
			debugRenderer.dispose();
		}
	}

	/**
	 * Positions the camera correctly and renders all the graphics of the game
	 */
	public void render() {
		// Update the camera position
		worldCamera.position.set(nextCameraPos.x, nextCameraPos.y, 0);
		worldCamera.update(true);
		worldBatch.setProjectionMatrix(worldCamera.combined);

		// Clear the screen every frame
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Render units that have alpha
		worldBatch.begin();
		for (Renderable renderable : renderables) {
			renderable.render(worldBatch, worldCamera);
		}
		worldBatch.end();
	}

	public void renderWithDebug(PhysicsEnvironment physics) {
		render();
		debugRenderer.render(physics.getWorld(), worldCamera.combined);
	}

	/** Adds an object to be rendered in the world. Uses hashcode to separate */
	public void addRenderable(Renderable renderable) {
		renderables.add(renderable);
	}

	/** Removes an object from being rendered in the world */
	public void removeRenderable(Renderable renderable) {
		renderables.remove(renderable);
	}

	/** Temporary local vector to prevent re-allocation every call */
	private static final Vector3 vector = new Vector3();

	/** Changes the given vector from screen to world position */
	public void screenToWorld(Vector2 position) {
		vector.set(position.x, position.y, 0);
		worldCamera.unproject(vector);
		position.set(vector.x, vector.y);
	}

	/** Changed the given vector from world to screen position */
	public void worldToScreen(Vector2 position) {
		vector.set(position.x, position.y, 0);
		worldCamera.project(vector);
		position.set(vector.x, vector.y);
	}

	/** Sets the next camera position */
	public void setNewCameraPos(float x, float y) {
		// Have to adjust the positions from relative to the virtual GAME_WIDTH/GAME_HEIGHT
		// to relative to the actual viewport width/height
		x = x - GAME_WIDTH / 2 + worldCamera.viewportWidth / 2;
		y = y - GAME_HEIGHT / 2 + worldCamera.viewportHeight / 2;
		nextCameraPos.set(x, y);
	}

}
