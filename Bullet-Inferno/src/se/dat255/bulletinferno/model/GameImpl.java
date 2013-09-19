package se.dat255.bulletinferno.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of Game, the central type in Bullet Inferno.
 * 
 * <p>Game acts as a single point of entry for the outside environment, as well as central point of
 * lookup for the inside. It handles instance-based object creation and initialization (injection).
 */
public class GameImpl implements Game {

	/** A list of Collidable objects in the world (cache). */
	private final List<Collidable> collidables = new ArrayList<Collidable>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Collidable> getCollidables() {
		return collidables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Projectile> getProjectiles() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerShip getPlayerShip() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Obstacle> getObstacles() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Enemy> getEnemies() {
		// TODO Auto-generated method stub
		return null;
	}

}