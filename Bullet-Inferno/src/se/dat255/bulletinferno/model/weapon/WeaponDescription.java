package se.dat255.bulletinferno.model.weapon;

import se.dat255.bulletinferno.util.Descriptable;

/**
 * A class for describing the properties of a weapon.
 * (The main purpose of this class is to act as an adapter for a {@link WeaponDefinition} to a
 * {@link Descriptable})
 */
public class WeaponDescription implements Descriptable {
	private final WeaponDefinition definition;
	
	public WeaponDescription(WeaponDefinition definition) {
		this.definition = definition;
	}
	
	@Override
	public String getIdentifier() {
		return definition.getIdentifier();
	}

	@Override
	public String getName() {
		return definition.getName();
	}

	@Override
	public String getDescription() {
		return "Damage : " + definition.getProjectileType().getDamage()
		+ "\nReloading time : " + definition.getReloadTime();
	}
}
