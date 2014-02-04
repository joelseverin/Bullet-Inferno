package se.dat255.bulletinferno.model.store;

import se.dat255.bulletinferno.util.ResourceIdentifier;

public interface StoreItem extends ResourceIdentifier {
	/**
	 * Returns the max upgrade level
	 * @return max upgrade level
	 */
	public int getMaxLevel();
	
	/**
	 * Returns the item's current level
	 * @return
	 */
	public int getCurrentLevel();
	
	/**
	 * Returns the cost for upgrading the item to the next level
	 * @return upgrade cost
	 */
	public int getUpgradeCost();
	
	/**
	 * Returns the description of the item in the next level
	 * @return upgrade description
	 */
	public String getUpdrageDescription();
	
	/**
	 * Returns the name of the item
	 * @return name
	 */
	public String getName();
}