package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum MenuTextureDefinitionImpl implements TextureDefinition {

	// Loadout menu specific
	LOADOUT_PASSIVE_RELOADING_TIME(texture("data/reloadSpeed.png")),
	LOADOUT_PASSIVE_TAKE_DAMAGE_MODIFIER(texture("data/shieldBoost.png")),
	LOADOUT_PASSIVE_DAMAGE_MODIFIER(texture("data/damageBoost.png")),
	LOADOUT_PASSIVE_RELOADING_TIME_BUTTON(texture("data/loadout/passive_ability/reloadButton.png")),
	LOADOUT_PASSIVE_TAKE_DAMAGE_MODIFIER_BUTTON(
			texture("data/loadout/passive_ability/shieldButton.png")),
	LOADOUT_PASSIVE_DAMAGE_MODIFIER_BUTTON(texture("data/loadout/passive_ability/damageButton.png")),
	LOADOUT_SPECIAL_PROJECTILE_RAIN(texture("data/projectileRain.png")),
	LOADOUT_SPECIAL_NUKE(texture("data/nuke.png")),
	LOADOUT_BACKGROUND(texture("data/menu.png")),
	LOADOUT_SHIP(texture("data/menuship.png")),
	LOADOUT_SPECIAL_ABILITIES(texture("data/menuspecialabilities.png")),
	LOADOUT_PASSIVE_ABILITIES(texture("data/menupassiveabilities.png")),
	LOADOUT_STANDARD_WEAPON(texture("data/menustandardweapon.png")),
	LOADOUT_HEAVYWEAPON(texture("data/menuheavyweapon.png")),
	
	STANDARD_MACHINE_GUN(texture("data/machineGun.png")),
	STANDARD_MINI_GUN(texture("data/miniGun.png")),
	STANDARD_PLASMA_GUN(texture("data/plasmaGun.png")),
	HEAVY_EGG_CANNON(texture("data/eggCannon.png")),
	HEAVY_LASER_CANNON(texture("data/laserCannon.png")),
	MISSILE_LAUNCHER(texture("data/missileLauncher.png")),
	DISORDERER(texture("data/disorderer.png")),
	MISSILE_LAUNCHER_LARGE(texture("data/missileLauncherLarge.png")),
	DISORDERER_LARGE(texture("data/disordererLarge.png")),
	SNIPER_RIFLE(texture("data/sniperRifle.png")),
	LASER(texture("data/laser.png")),

	// Buttons
	LOADOUT_START_BUTTON(texture("data/startBtn.png")),
	
	;
	
	private TextureHolder holder;

	private MenuTextureDefinitionImpl(TextureHolder holder) {
		this.holder = holder;
	}

	private static TextureHolder texture(String source) {
		return new TextureHolderImpl(source);
	}

//	private static TextureHolder textureAtlas(String source, String region) {
//		return new TextureHolderAtlasImpl(source, region);
//	}

	@Override
	public TextureRegion getTexture(AssetManager manager) {
		return holder.getTexture(manager);
	}

	@Override
	public String getSource() {
		return holder.getSource();
	}

	@Override
	public void dispose() {
		holder.dispose();
	}

	@Override
	public void loadSource(AssetManager manager) {
		holder.loadSource(manager);
	}

	@Override
	public void unloadSource(AssetManager manager) {
		holder.unloadSource(manager);
	}
	
	
}
