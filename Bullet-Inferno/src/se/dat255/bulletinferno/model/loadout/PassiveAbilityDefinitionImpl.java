package se.dat255.bulletinferno.model.loadout;


public enum PassiveAbilityDefinitionImpl implements PassiveAbilityDefinition {

	LOADOUT_PASSIVE_RELOADING_TIME("Faster reload", "Makes your gun reload faster",
			new PassiveAbilityImpl(new PassiveReloadingTime(0.75f))),
	LOADOUT_PASSIVE_TAKE_DAMAGE_MODIFIER("Endurance", "Makes you able to withstand more damage",
			new PassiveAbilityImpl(new PassiveTakeDamageModifier(0.5f))),
	LOADOUT_PASSIVE_DAMAGE_MODIFIER("Stronger weapons","Increases the damage of your weapons",
			new PassiveAbilityImpl(new PassiveDamageModifier(1.10f)));

	private final PassiveAbility ability;
	private final String name, description;
	
	PassiveAbilityDefinitionImpl(String name, String description, PassiveAbility ability) {
		this.ability = ability;
		this.name = name;
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PassiveAbility getPassiveAbility() {
		return ability;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentifier() {
		return this.name();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return description;
	}
}
