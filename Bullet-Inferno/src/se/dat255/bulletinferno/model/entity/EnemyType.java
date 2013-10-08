package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.DisorderedBossMovementPattern;
import se.dat255.bulletinferno.model.physics.DisorderedMovementPattern;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinitionImpl;
import se.dat255.bulletinferno.model.physics.PhysicsEnvironment;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.model.weapon.Weapon;
import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.model.weapon.WeaponEnvironment;
import se.dat255.bulletinferno.util.PhysicsShapeFactory;
import se.dat255.bulletinferno.util.ResourceIdentifier;

import com.badlogic.gdx.math.Vector2;

public enum EnemyType implements ResourceIdentifier {

	// (movement pattern = null) => no movement patter
	DEFAULT_ENEMY_SHIP(new Vector2(-3, 0), null, 5, new WeaponData[] { WeaponData.ENEMY_DISORDERER }, 10,
			10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1))),

	SPECIAL_ENEMY_SHIP(new Vector2(-2, 0), new DisorderedMovementPattern(1, 1), 5,
			new WeaponData[] { WeaponData.ENEMY_FORCE_GUN }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(1, 1))),
			
	EASY_BOSS_SHIP(new Vector2(0, 2), new DisorderedBossMovementPattern(2, 3), 15,
			new WeaponData[] {WeaponData.BOSS_SPR, WeaponData.BOSS_AIM}, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(2, 2))),

	HARD_BOSS_SHIP(new Vector2(0, 0), new DisorderedBossMovementPattern(2, 3), 25,
			new WeaponData[] { WeaponData.BOSS_SPR, WeaponData.BOSS_SPR2, WeaponData.BOSS_SPR3, WeaponData.BOSS_AIM, WeaponData.BOSS_AIM2, WeaponData.BOSS_AIM3  }, 10, 10,
			new PhysicsBodyDefinitionImpl(PhysicsShapeFactory.getRectangularShape(2, 2)));

	private final Vector2 velocity;
	private final PhysicsMovementPattern pattern;
	private final int initialHealth;
	private WeaponData weaponData;
	private WeaponData[] weaponsData;
	private final int score;
	private final int credits;
	private final PhysicsBodyDefinition bodyDefinition;

	EnemyType(Vector2 velocity, PhysicsMovementPattern pattern, int initialHealth,
			WeaponData[] weaponsData, int score, int credits, PhysicsBodyDefinition bodyDefinition) {
		this.velocity = velocity.cpy();
		this.pattern = pattern;
		this.initialHealth = initialHealth;
		this.weaponsData = weaponsData;
		this.score = score;
		this.credits = credits;
		this.bodyDefinition = bodyDefinition;
	}

	public SimpleEnemy getEnemyShip(PhysicsEnvironment physics,
			EntityEnvironment entities, WeaponEnvironment weaponEnvironment, Vector2 position) {
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].getEnemyWeaponForGame(physics, weaponEnvironment);
		}

		if (pattern == null) {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity,
					initialHealth, weapons, score, credits, bodyDefinition);
		} else {
			return new DefaultEnemyShipImpl(physics, entities, this, position, velocity,
					initialHealth, weapons, score, credits, bodyDefinition, pattern);
		}
	}

	public SimpleBoss getBoss(PhysicsEnvironment physics, EntityEnvironment entities,
			WeaponEnvironment weaponEnvironment, Vector2 position) {
		Weapon[] weapons = new Weapon[weaponsData.length];
		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = weaponsData[i].getEnemyWeaponForGame(physics, weaponEnvironment);
		}if (pattern == null){
			return new DefaultBossImpl(physics, entities, this, position, velocity, initialHealth,
					weapons, score, credits, bodyDefinition);
		}else {
			return new DefaultBossImpl(physics, entities, this, position, velocity, pattern, initialHealth,
					weapons, score, credits, bodyDefinition);
		}
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public int getInitialHealth() {
		return initialHealth;
	}

	public WeaponData getWeaponData() {
		return weaponData;
	}

	public int getScore() {
		return score;
	}

	public int getCredits() {
		return credits;
	}

	@Override
	public String getIdentifier() {
		return this.name();
	}
}
