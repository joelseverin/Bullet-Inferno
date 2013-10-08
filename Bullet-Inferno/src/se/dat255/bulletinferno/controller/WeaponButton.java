package se.dat255.bulletinferno.controller;

import se.dat255.bulletinferno.model.weapon.WeaponData;
import se.dat255.bulletinferno.util.ResourceManagerImpl.TextureType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class WeaponButton {

	private final Button button;
	private WeaponData weaponData;
	private boolean isSelected;

	public WeaponButton(Button button, WeaponData weaponData) {
		this.button = button;
		this.weaponData = weaponData;
		this.isSelected = false;
	}

	public Button getButton() {
		return button;
	}

	public WeaponData getWeaponData() {
		return weaponData;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void toggleSelected(Skin skin) {
		this.isSelected = !isSelected;
		if (isSelected) {
			button.getStyle().up = skin.newDrawable(button.getStyle().up,
					Color.LIGHT_GRAY);
		} else {
			button.getStyle().up = new TextureRegionDrawable(new TextureRegion(
					TextureType.DISORDERER.getTexture()));
		}
	}

	public void setWeaponData(WeaponData weaponData) {
		this.weaponData = weaponData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((button == null) ? 0 : button.hashCode());
		result = prime * result + (isSelected ? 1231 : 1237);
		result = prime * result + ((weaponData == null) ? 0 : weaponData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeaponButton other = (WeaponButton) obj;
		if (button == null) {
			if (other.button != null)
				return false;
		} else if (!button.equals(other.button))
			return false;
		if (isSelected != other.isSelected)
			return false;
		if (weaponData != other.weaponData)
			return false;
		return true;
	}
	
	
}
