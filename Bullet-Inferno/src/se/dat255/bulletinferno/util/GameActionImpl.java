package se.dat255.bulletinferno.util;

public enum GameActionImpl implements GameAction {
	BORN, TOOK_DAMAGE, CRASHED, DIED;

	@Override
	public String getAction() {
		return name();
	}

}
