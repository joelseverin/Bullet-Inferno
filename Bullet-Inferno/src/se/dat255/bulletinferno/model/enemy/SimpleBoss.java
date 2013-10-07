package se.dat255.bulletinferno.model.enemy;

import com.badlogic.gdx.math.Vector2;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.PhysicsBodyDefinition;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;
import se.dat255.bulletinferno.util.Timer;
import se.dat255.bulletinferno.util.Timerable;

public abstract class SimpleBoss extends SimpleEnemy implements Timerable {

	
	private final PlayerShip player;
	private final Game game;
	private Timer[] timers;

	public SimpleBoss(Game game, EnemyType type, Vector2 position, Vector2 velocity,
			int initialHealth,
			Weapon[] weapons, int score, int credits,
			PhysicsBodyDefinition bodyDefinition, PhysicsMovementPattern pattern) {
		super(game, type, position, velocity, initialHealth, weapons, score, credits,
				bodyDefinition, pattern);
		
		this.timers = new Timer[weapons.length];
		for (int i = 0; i < weapons.length; i++) {
			timers[i] = weapons[i].getTimer();
			timers[i].registerListener(this);
			timers[i].stop();
		}
		this.game = game;
		this.player = game.getPlayerShip();
		
		
	}

	public abstract void onTimeout(Timer source, float timeSinceLast);

	

	@Override
	public void viewportIntersectionBegin() {
		for (int i = 0; i < weapons.length; i++) {
			timers[i].start();
		}
		super.viewportIntersectionBegin();
		player.halt();
	}


	@Override
	public void takeDamage(float damage) {
		
		super.takeDamage(damage);

		if (isDead()) {
			game.restorePlayerShipSpeed();
		}
	}
	
	// Different firing methods determine how many weapon to fire and in what direction
	public void fireSpread(Timer source) {
		for (int i = 0; i < weapons.length / 2; i++) {

			if (source == timers[i]) {
				weapons[i].fire(this.getPosition(), new Vector2(-1, 0), this);
			}
		}

	}

	public void fireAim(Timer source) {
		for (int i = weapons.length / 2; i < weapons.length; i++) {

			if (source == timers[i]) {
				weapons[i].fire(this.getPosition(), new Vector2(player.getPosition().x
						- getPosition().x, player.getPosition().y - getPosition().y).nor(),
						this);
			}
		}

	}
	
	
	public void fireAimSpread(Timer source) {
		
		fireSpread(source);

		fireAim(source);
	}
	
	
	public Timer[] getWeaponTimers(){
		return timers;
	}

}