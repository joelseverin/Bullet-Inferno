package se.dat255.bulletinferno.model.mock;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Collidable;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.Weapon;

public class SimplePlayerShipMock implements PlayerShip {

	@Override
	public void preCollided(Collidable other) {
	}

	@Override
	public void postCollided(Collidable other) {
	}

	@Override
	public void takeDamage(float damage) {
	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	public int getInitialHealth() {
		return 0;
	}

	@Override
	public boolean isInMyTeam(Teamable teamMember) {
		return false;
	}

	@Override
	public void dispose() {

	}

	@Override
	public Vector2 getPosition() {
		return null;
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	public void fireWeapon() {
	}

	@Override
	public void update(float deltaTime) {
	}

	@Override
	public void setPosition(Vector2 position) {
	}

	@Override
	public void moveTo(float yPos) {
	}

	@Override
	public void stopMovement() {
	}

	@Override
	public float getMovePos() {
		return 0;
	}

	@Override
	public void setWeapon(Weapon weapon) {
	}

}
