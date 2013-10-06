package se.dat255.bulletinferno.model.mock;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Loadout;
import se.dat255.bulletinferno.model.PassiveAbility;
import se.dat255.bulletinferno.model.PlayerShip;
import se.dat255.bulletinferno.model.Teamable;
import se.dat255.bulletinferno.model.Weapon;
import se.dat255.bulletinferno.model.physics.Collidable;

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
	public Weapon getWeapon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Loadout getLoadout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attachPassive(PassiveAbility passiveAbility) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTakeDamageModifier(float takeDamageModifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void moveY(float dy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveY(float dy, float scale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2 getDimensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restoreSpeed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void halt() {
		// TODO Auto-generated method stub
		
	}

}
