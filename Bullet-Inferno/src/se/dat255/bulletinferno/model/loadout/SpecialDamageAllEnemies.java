package se.dat255.bulletinferno.model.loadout;

import se.dat255.bulletinferno.model.Destructible;
import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.SpecialEffect;

public class SpecialDamageAllEnemies implements SpecialEffect {

	private final Game game;
	private static final float damage = 5;

	public SpecialDamageAllEnemies(Game game) {
		this.game = game;
	}

	@Override
	public void activate() {
		for(Enemy enemy : game.getEnemies()) {
			if(enemy instanceof Destructible) {
				((Destructible) enemy).takeDamage(damage);
			}
		}
	}
}
