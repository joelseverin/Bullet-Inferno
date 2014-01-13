package se.dat255.bulletinferno.util;

public class GameActionEventImpl<T> implements GameActionEvent<T> {
	private final T source;
	private final GameAction action;

	public GameActionEventImpl(T source, GameAction action) {
		this.source = source;
		this.action = action;
	}

	@Override
	public T getSource() {
		return source;
	}

	@Override
	public GameAction getAction() {
		return action;
	}

}
