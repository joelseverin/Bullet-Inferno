package se.dat255.bulletinferno.model.physics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.PhysicsShapeFactory;
import se.dat255.bulletinferno.model.PhysicsViewportIntersectionListener;
import se.dat255.bulletinferno.model.PhysicsWorld;
import se.dat255.bulletinferno.model.PhysicsWorldCollisionQueue;
import se.dat255.bulletinferno.util.Disposable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Simulates the world dynamics and handles collision detection.
 */
public class PhysicsWorldImpl implements PhysicsWorld {

	/** The discrete time step to take each update. */
	private static float TIME_STEP = 1 / 60f;

	/**
	 * The number of velocity iterations per update (performance vs. accuracy).
	 * See Box2D docs.
	 */
	private static int VELOCITY_ITERATIONS = 8;

	/**
	 * The number of position iterations per update (performance vs. accuracy). See Box2D docs.
	 */
	private static int POSITION_ITERATIONS = 3;

	/**
	 * Accumulates time that was not simulated the last update, since it didn't fit into TIME_STEP.
	 */
	private float timeStepAccumulator = 0f;

	/** Holds the Box2D world. */
	private final World world;

	/**
	 * Holds a shape factory instance. Creates Box2D shape objects in various ways.
	 */
	private final PhysicsShapeFactory shapeFactory = new PhysicsShapeFactoryImpl();

	/**
	 * A collision queue but with filtering and internal usage of special bodies.
	 * 
	 * <p>
	 * Public for testing purposes only.
	 */
	public class FilteredCollisionQueue extends PhysicsWorldCollisionQueueImpl implements
			Disposable {

		/** A body positioned right in the viewport used for viewport intersection events. */
		private Body viewportIntersectionBody = null;

		/** BodyDef for viewportIntersectionBody. Used for viewport intersection events. */
		private final BodyDef viewportIntersectionBodyDef = new BodyDef();

		/**
		 * A fixture fitting the viewport dimensions (replaced on resize), attached to
		 * viewportIntersectionBody and used by viewport intersection events.
		 */
		private Fixture viewportIntersectionFixture = null;

		/** FixtureDef for viewportIntersectionFixture. Used for viewport intersection events. */
		private final FixtureDef viewportIntersectionFixtureDef = new FixtureDef();

		/** The last viewport dimension (null if no earlier dimension set). */
		private Vector2 lastViewportDimensions = null;

		/**
		 * (Private to prevent outside construction. Use PhysicsWorld directly instead.)
		 */
		private FilteredCollisionQueue() {
			viewportIntersectionBodyDef.type = BodyDef.BodyType.DynamicBody;
			viewportIntersectionBodyDef.gravityScale = 0; // No gravity.

			viewportIntersectionFixtureDef.density = 0f;
			viewportIntersectionFixtureDef.friction = 0f;
			viewportIntersectionFixtureDef.isSensor = true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void beginContact(Contact contact) {
			// Only called inside the time step.

			Body bodyA = contact.getFixtureA().getBody();
			Body bodyB = contact.getFixtureB().getBody();

			if (bodyA == viewportIntersectionBody) {
				Object userData = bodyB.getUserData();
				if (userData instanceof PhysicsViewportIntersectionListener) {
					((PhysicsViewportIntersectionListener) userData).viewportIntersectionBegin();
				}
			} else if (bodyB == viewportIntersectionBody) {
				Object userData = bodyA.getUserData();
				if (userData instanceof PhysicsViewportIntersectionListener) {
					((PhysicsViewportIntersectionListener) userData).viewportIntersectionBegin();
				}
			} else {
				super.beginContact(contact);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void endContact(Contact contact) {
			// Called inside the time step, or when bodies are destroyed. When they are destroyed,
			// one fixture will be null (possibly both?).

			Fixture fixtureA = contact.getFixtureA();
			Body bodyA = fixtureA != null ? fixtureA.getBody() : null;

			Fixture fixtureB = contact.getFixtureB();
			Body bodyB = fixtureB != null ? fixtureB.getBody() : null;

			if (bodyA == viewportIntersectionBody) {
				if (bodyB != null) {
					Object userData = bodyB.getUserData();
					if (userData instanceof PhysicsViewportIntersectionListener) {
						((PhysicsViewportIntersectionListener) userData).viewportIntersectionEnd();
					}
				}
			} else if (bodyB == viewportIntersectionBody) {
				if (bodyA != null) {
					Object userData = bodyA.getUserData();
					if (userData instanceof PhysicsViewportIntersectionListener) {
						((PhysicsViewportIntersectionListener) userData).viewportIntersectionEnd();
					}
				}
			} else {
				super.endContact(contact);
			}
		}

		/**
		 * Adjusts the viewport, for viewport intersection events.
		 * 
		 * @see PhysicsWorld#setViewport(Vector2, Vector2)
		 */
		public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions) {
			if (viewportIntersectionBody == null) {
				// Initialize body def position.
				viewportIntersectionBodyDef.position.set(viewportPosition);

				// Create a body with a fixture of the same dimensions as the viewport.
				viewportIntersectionBody = world.createBody(viewportIntersectionBodyDef);
				viewportIntersectionFixtureDef.shape = shapeFactory.getRectangularShape(
						viewportDimensions.x, viewportDimensions.y);
				viewportIntersectionFixture = viewportIntersectionBody.createFixture(
						viewportIntersectionFixtureDef);
			} else {
				// Resize the fixture def and body if its size changed.
				if (!viewportDimensions.equals(lastViewportDimensions)) {
					viewportIntersectionFixtureDef.shape.dispose();
					viewportIntersectionFixtureDef.shape = shapeFactory.getRectangularShape(
							viewportDimensions.x, viewportDimensions.y);

					// Replace the fixture with a resized one, moving the body in between.
					viewportIntersectionBody.destroyFixture(viewportIntersectionFixture);
					viewportIntersectionBody.setTransform(viewportPosition, 0);
					viewportIntersectionFixture = viewportIntersectionBody.createFixture(
							viewportIntersectionFixtureDef);
				} else {
					// Just move the body.
					viewportIntersectionBody.setTransform(viewportPosition, 0);
				}
			}

			lastViewportDimensions = viewportDimensions;
		}

		@Override
		public void dispose() {
			if (viewportIntersectionFixtureDef.shape != null) {
				viewportIntersectionFixtureDef.shape.dispose();
			}
		}
	}

	/** A collision detection queue (filtered by implementation). */
	private final FilteredCollisionQueue collisionQueue = new FilteredCollisionQueue();

	private final Map<PhysicsBody, PhysicsMovementPattern> movementPatterns = new HashMap<PhysicsBody, PhysicsMovementPattern>();

	/**
	 * Start the simulation.
	 */
	public PhysicsWorldImpl() {
		// Initialize world with 0 gravity and all objects non-sleeping. They cannot sleep as we
		// move them manually (otherwise Box2D will make them sleep, possibly with odd behavior).
		world = new World(new Vector2(0f, 0f), false);
		world.setContactListener(collisionQueue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhysicsBody createBody(PhysicsBodyDefinition definition, Collidable collidable,
			Vector2 position) {
		BodyDef bodyDef = definition.getBox2DBodyDefinition();

		bodyDef.position.set(position);
		Body body = world.createBody(bodyDef);
		body.createFixture(definition.getBox2DFixtureDefinition());
		body.setUserData(collidable);
		return new PhysicsBodyImpl(body);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void attachMovementPattern(PhysicsMovementPattern pattern, PhysicsBody body) {
		movementPatterns.put(body, pattern);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void detachMovementPattern(PhysicsBody body) {
		movementPatterns.remove(body);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBody(PhysicsBody body) {
		world.destroyBody(body.getBox2DBody());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float deltaTime) {
		timeStepAccumulator += deltaTime;

		// Take discrete steps of TIME_STEP, sometimes even multiple of them (to keep up).
		for (; timeStepAccumulator > TIME_STEP; timeStepAccumulator -= TIME_STEP) {
			for (Entry<PhysicsBody, PhysicsMovementPattern> set : movementPatterns.entrySet()) {
				set.getValue().update(TIME_STEP, set.getKey());
			}
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}

		// Resolve queued up collisions.
		// Collisions are queued to avoid the problem where the Box2D world cannot be modified
		// within the collision handler (not allowed by Box2D).
		for (PhysicsWorldCollisionQueue.Entry collision : collisionQueue) {
			Collidable collidableA = collision.getCollidableA();
			Collidable collidableB = collision.getCollidableB();

			collidableA.preCollided(collidableB);
			collidableB.preCollided(collidableA);
			collidableA.postCollided(collidableB);
			collidableB.postCollided(collidableA);
		}
		collisionQueue.removeAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhysicsShapeFactory getShapeFactory() {
		return shapeFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setViewport(Vector2 viewportPosition, Vector2 viewportDimensions) {
		collisionQueue.setViewport(viewportPosition, viewportDimensions);
	}

	@Override
	public void dispose() {
		collisionQueue.dispose();
	}

}