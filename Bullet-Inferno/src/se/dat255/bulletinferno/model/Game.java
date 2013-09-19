package se.dat255.bulletinferno.model;

import java.util.List;

public interface Game {
	/**
	 * Returns a list of all projectiles in the game
	 * @return
	 */
	public List<? extends Projectile> getProjectiles();

	/**
	 * Retrieve a projectile
	 */
	public Projectile retrieveProjectile(Class<? extends Projectile> type);
	
	/**
	 * Disposes of the specified projectile
	 * @param projectile
	 */
	public void disposeProjectile(Projectile projectile);
	
	/**
	 * Returns the player's ship
	 * @return
	 */
	public PlayerShip getPlayerShip();

	/**
	 * Returns a list of all the obstacles.
	 * 
	 * @return A list of all the obstacles.
	 */
	public List<? extends Obstacle> getObstacles();

	/**
	 * Returns a list of all enemies in the game
	 * @return enemies
	 */
	public List<? extends Enemy> getEnemies();
}
