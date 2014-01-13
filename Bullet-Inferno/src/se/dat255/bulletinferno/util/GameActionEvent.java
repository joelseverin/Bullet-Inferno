package se.dat255.bulletinferno.util;

public interface GameActionEvent<T> {
	/**
	 * Returns the source from which the the action
	 * originated
	 * 
	 * @return source
	 */
	public T getSource();

	/**
	 * Returns the GameActionEvent's GameAction.
	 * 
	 * @return The GameAction.
	 */
	public GameAction getAction();
}
