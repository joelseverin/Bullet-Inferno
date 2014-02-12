package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.util.Descriptable;
import se.dat255.bulletinferno.util.ResourceIdentifier;

public interface PassiveAbilityDefinition extends ResourceIdentifier, Descriptable {

	/**
	 * Returns the PassiveAbility that was chosen.
	 * 
	 * @return The PassiveAbility.
	 */
	PassiveAbility getPassiveAbility();

}
