package se.dat255.bulletinferno.model.weapon;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.Projectile;
import se.dat255.bulletinferno.model.ProjectileImpl;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.WeaponDescription;

/**
 * Enum class for holding different Weapon types. The method {@link #getPlayerWeaponForGame(Game)}
 * (for players) or {@link #getEnemyWeaponForGame(Game)} (for enemies) are
 * used to retrieve a Weapon for the game.
 * 
 * @author Jakob Csorgei Gustavsson
 * 
 */
public enum WeaponData implements WeaponDescription {

	/**
	 * Order:
	 * reloadTime, projectile, offset, projectileVelocity, damage
	 */
	FAST(0.1f, ProjectileType.SINE_PROJECTILE, new Vector2(), 4),
	SLOW(1f, ProjectileType.SINE_PROJECTILE, new Vector2(), 4);

	private float reloadingTime;
	private final ProjectileType projectileType;
	private final Vector2 offset;
	private final float velocity;

	WeaponData(float reloadTime, ProjectileType projectileType, Vector2 offset,
			float velocity) {
		reloadingTime = reloadTime;
		this.projectileType = projectileType;
		this.offset = offset;
		this.velocity = velocity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getReloadTime() {
		return reloadingTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getOffset() {
		return offset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getProjectileVelocity() {
		return velocity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getPlayerWeaponForGame(Game game) {
		return new WeaponImpl(game, reloadingTime, projectileType, offset, velocity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Weapon getEnemyWeaponForGame(Game game) {
		return new EnemyWeaponImpl(game, reloadingTime, projectileType, offset, velocity);
	}
	
	@Override
	public String getIdentifier() {
		return this.name();
	}

	@Override
	public ProjectileType getProjectileType() {
		return projectileType;
	}
}
