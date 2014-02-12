package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.ModelEnvironment;

/**
 * Defines the different special abilities
 */
public enum SpecialAbilityDefinitionImpl implements SpecialAbilityDefinition {

	LOADOUT_SPECIAL_NUKE("Nuclear bomb", "The ability to detonate a nuclear bomb, capable of " +
			"killing everything in your way", 
			new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
			return new SpecialAbilityImpl(new SpecialDamageAll(game.getEntityEnvironment(),
					game.getPhysicsEnvironment(), 20));
		}
	}),
	LOADOUT_SPECIAL_PROJECTILE_RAIN("Projectile Rain","Sends a rain of projectiles on your ememies",
			new SpecialInitializer() {
		@Override
		public SpecialAbility initialize(ModelEnvironment game) {
			return new SpecialAbilityImpl(new SpecialProjectileRain(game.getPhysicsEnvironment(),
					game.getWeaponEnvironment(), 25));
		}
	});

	private final SpecialInitializer specialInitializer;
	private final String name, description;
	
	SpecialAbilityDefinitionImpl(String name, String description, SpecialInitializer initializer) {
		specialInitializer = initializer;
		this.name = name;
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SpecialAbility getSpecialAbility(ModelEnvironment game) {
		return specialInitializer.initialize(game);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentifier() {
		return this.name();
	}

	/**
	 * Interface for specifying the callback listener for the enum instances above.
	 */
	public interface SpecialInitializer {
		public SpecialAbility initialize(ModelEnvironment game);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
