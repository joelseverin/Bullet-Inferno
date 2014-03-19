package se.dat255.bulletinferno.model.achievement;

public interface Achievement {
	/**
	 * Returns the <strong>unique</strong> identifier for the achievement
	 * @return id
	 */
	public String getId();
	
	/**
	 * Returns the name of the achievement
	 * @return name
	 */
	public String getName();
	
	/**
	 * Returns the description of the achievement
	 * @return description
	 */
	public String getDescription();
	
	/**
	 * Return whether this achievement is fully achieved
	 * @return
	 */
	public boolean isAchieved();
}
