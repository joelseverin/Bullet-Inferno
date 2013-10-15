package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.gui.Listener;
import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsViewportIntersectionListener;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.model.weapon.Projectile;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.util.GameActionEvent;
import se.dat255.bulletinferno.util.GameActionEventImpl;
import se.dat255.bulletinferno.util.GameActionImpl;

import com.badlogic.gdx.math.Vector2;

public abstract class SimpleEnemy implements Enemy, Collidable, Destructible,
		PhysicsViewportIntersectionListener {

	private float health;
	private final float initialHealth;
	private final int score;
	private final int credits;
	private final EnemyDefinitionImpl type;
	private Listener<GameActionEvent> actionListener;

	private PhysicsBody body = null;
	private final PhysicsEnvironment physics;
	private final EntityEnvironment entities;
	private Vector2 velocity;
	private Weapon[] weapons;

	private final Listener<Integer> scoreListener;

	/** A flag to make sure we don't remove ourself twice */
	private boolean flaggedForRemoval = false;
	// TODO : Fix this in box2d instead?
	private boolean isAwake = false;

	/**
	 * A task that when added to the Game's runLater will remove this projectile. Used to no modify
	 * the physics world during a simulation.
	 */
	private Runnable removeSelf = new Runnable() {
		@Override
		public void run() {
			entities.removeEnemy(SimpleEnemy.this);
			dispose();
		}
	};

	public SimpleEnemy(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyDefinitionImpl type,
			Vector2 position, Vector2 velocity, float initialHealth, Weapon[] weapons,
			int score,
			int credits, PhysicsBodyDefinition bodyDefinition, Listener<Integer> scoreListener) {

		this.physics = physics;
		this.type = type;
		this.initialHealth = initialHealth;
		health = initialHealth;
		this.weapons = weapons;
		this.score = score;
		this.credits = credits;
		this.velocity = velocity;
		this.entities = entities;
		this.scoreListener = scoreListener;

		body = this.physics.createBody(bodyDefinition, this, position);
	}

	public SimpleEnemy(PhysicsEnvironment physics, EntityEnvironment entities,
			EnemyDefinitionImpl type,
			Vector2 position, Vector2 velocity, float initialHealth, Weapon[] weapons, int score,
			int credits, PhysicsBodyDefinition bodyDefinition, PhysicsMovementPattern pattern,
			Listener<Integer> scoreListener) {
		this(physics, entities, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, scoreListener);

		if (pattern != null) {

			physics.attachMovementPattern(pattern.copy(), body);
		}
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getCredits() {
		return credits;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		if (!isAwake) {
			return;
		}

		if (hitByOtherProjectile(other)) {
			takeDamage(((Projectile) other).getDamage());
		} else if (hitByPlayerShip(other)) {
			takeDamage(initialHealth);
		}
	}

	private boolean hitByPlayerShip(Collidable other) {
		return other instanceof PlayerShip;
	}

	private boolean hitByOtherProjectile(Collidable other) {
		return other instanceof Projectile && !isInMyTeam(((Projectile) other).getSource());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// NOP
	}

	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public void takeDamage(float damage) {
		// Take no damage if enemy isn't alive
		if (health > 0) {
			health -= damage;

			if (isDead()) {
				scoreListener.call(getScore());
				if (actionListener != null) {
					actionListener.call(new GameActionEventImpl(this, GameActionImpl.DIED));
				}
				scheduleRemoveSelf();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDead() {
		return health <= 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		physics.removeBody(body);
		if (weapons != null) {
			for (int i = 0; i < (weapons.length); i++) {
				if (weapons[i] != null) {
					weapons[i].getTimer().stop();
				}
			}

			body = null;
		}
	}

	@Override
	public float getInitialHealth() {
		return initialHealth;
	}

	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	public void setVelocity(Vector2 velocity) {
		body.setVelocity(velocity);
	}

	protected Vector2 getVelocity() {
		return body.getVelocity();
	}

	protected Weapon[] getWeapons() {
		return weapons;
	}

	@Override
	public boolean isInMyTeam(Teamable teamMember) {
		return teamMember instanceof Enemy;
	}

	@Override
	public void viewportIntersectionBegin() {
		isAwake = true;
		body.setVelocity(velocity);
	}

	@Override
	public void viewportIntersectionEnd() {
		scheduleRemoveSelf();
	}

	@Override
	public String getIdentifier() {
		return type.name();
	}

	/**
	 * Removes the ship from the world using game.runLater to not modify physics world while
	 * running.
	 */
	private void scheduleRemoveSelf() {
		if (!flaggedForRemoval) {
			physics.runLater(removeSelf);
			flaggedForRemoval = true;
		}
	}

	public void setActionListener(Listener<GameActionEvent> actionListener) {
		this.actionListener = actionListener;
	}

	@Override
	public Vector2 getDimensions() {
		return body.getDimensions();
	}

	protected PhysicsBody getBody() {
		return body;
	}
}
