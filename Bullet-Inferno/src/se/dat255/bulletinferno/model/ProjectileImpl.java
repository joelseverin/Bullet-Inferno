package se.dat255.bulletinferno.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class ProjectileImpl implements Projectile {

	private static PhysicsBodyDefinition bodyDefinition = null;

	private PhysicsBody body = null;

	private int damage;
	private final Game game;

	/**
	 * Constructs a new projectile
	 * 
	 * @param world
	 */
	public ProjectileImpl(Game game) {
		this.game = game;

		if (bodyDefinition == null) {
			Shape shape = game.getPhysicsWorld().getShapeFactory().getRectangularShape(0.1f, 0.1f);
			bodyDefinition = new PhysicsBodyDefinitionImpl(shape, true);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Vector2 origin, Vector2 velocity, int damage) {
		this.damage = damage;

		body = game.getPhysicsWorld().createBody(bodyDefinition, this, origin);
		
		this.setVelocity(velocity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDamage() {
		return damage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preCollided(Collidable other) {
		// NOP
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postCollided(Collidable other) {
		// Decrease the damage after hits, since we must let the other object that collided with us
		// decide if they want to take our current damage (etc.) before we zero it.
		if(damage > 0) {
			damage -= 1;
			
			// We won't need this projectile anymore, since it is useless and can't hurt anyone.
			game.disposeProjectile(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		// TODO Reset projectile

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVelocity(Vector2 velocity) {
		body.setVelocity(velocity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
}
