package se.dat255.bulletinferno.util;

public interface Descriptable extends ResourceIdentifier {
	/**
	 * Returns the name of the item
	 * @return name
	 */
	public String getName();
	
	/**
	 * Returns the description of the item
	 * @return description
	 */
	public String getDescription();
}
