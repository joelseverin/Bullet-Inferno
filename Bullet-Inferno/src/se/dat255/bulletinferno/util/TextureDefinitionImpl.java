package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum TextureDefinitionImpl implements TextureDefinition {
	DEFAULT_SHIP(texture("data/katze.png")),
	FAST_SHIP(texture("data/katze.png")),
	SLOW_SHIP(texture("data/katze.png")),

	KATZE(texture("data/katze.png")),
	SQUIB(texture("data/squib.png")),
	EHMO(texture("data/EHMO.png")),
	DRIPPER(texture("data/dripper.png")),

	// Player ship
	PLAYER_DEFAULT(texture("data/playerShip.png")),
	PLAYER_EXPLOSION(texture("data/explosion.png")),

	// Weapons
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

	// Projectiles
	VELOCITY_BULLET(textureAtlas("data/packedProjectiles.atlas", "bullet")),
	ROUND_BULLET(textureAtlas("data/packedProjectiles.atlas", "bullet")),
	PLASMA(textureAtlas("data/packedProjectiles.atlas", "plasma")),
	EGG(textureAtlas("data/packedProjectiles.atlas", "egg")),
	RED_PROJECTILE(textureAtlas("data/packedProjectiles.atlas", "redDotProjectile")),
	GREEN_PROJECTILE(textureAtlas("data/packedProjectiles.atlas", "greenDotProjectile")),
	MISSILE(textureAtlas("data/packedProjectiles.atlas", "missile")),
	SPECIAL_ABILITY_MISSILE(textureAtlas("data/packedProjectiles.atlas", "missile")),
	HIGH_VELOCITY_PROJECTILE(textureAtlas("data/packedProjectiles.atlas", "missile")),

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

	// Buttons
	PAUSE_SCREEN(texture("images/gui/screen_pause.png")),
	BLUE_BACKGROUND(texture("images/game/background.png")),
	GAMEOVER_SCREEN(texture("images/gui/screen_gameover.png")),
	PROJECTILE_RAIN(texture("data/projectileRain.png")),
	LOADOUT_START_BUTTON(texture("data/startBtn.png")),
	HUD_TEXTURE(texture("images/game/hud.png")),

	// Slices
	MOUNTAIN_1(textureAtlas("data/packedMountain.atlas", "mountain1")),
	MOUNTAIN_2(textureAtlas("data/packedMountain.atlas", "mountain2")),
	MOUNTAIN_3(textureAtlas("data/packedMountain.atlas", "mountain3")),
	MOUNTAIN_4(textureAtlas("data/packedMountain.atlas", "mountain4")),
	MOUNTAIN_5(textureAtlas("data/packedMountain.atlas", "mountain5")),
	MOUNTAIN_6(textureAtlas("data/packedMountain.atlas", "mountain6")),
	MOUNTAIN_7(textureAtlas("data/packedMountain.atlas", "mountain7")),
	MOUNTAIN_8(textureAtlas("data/packedMountain.atlas", "mountain8")),

	// Particles
	SMOKE_PARTICLE(texture("images/particles/smoke.png")),

	// Menu
	MENU_BACKGROUND(texture("images/gui/menubackground.png")),
	MENU_PLANE(textureAtlas("images/gui/menubuttons.atlas", "menuplane")),
	
	MENU_SOUND_ON(textureAtlas("images/gui/menubuttons.atlas", "sound_on")),
	MENU_SOUND_OFF(textureAtlas("images/gui/menubuttons.atlas", "sound_off")),
	MENU_RESETBUTTON(textureAtlas("images/gui/menubuttons.atlas", "reset")),
	
	MENU_MAIN_PLAYBUTTON(textureAtlas("images/gui/menubuttons.atlas", "play")),
	MENU_MAIN_PLAYBUTTON_OVER(textureAtlas("images/gui/menubuttons.atlas", "play_down")),
	MENU_MAIN_ACHIEVEMENTSBUTTON(textureAtlas("images/gui/menubuttons.atlas", "achievements")),
	MENU_MAIN_ACHIEVEMENTSBUTTON_OVER(
			textureAtlas("images/gui/menubuttons.atlas", "achievements_down")),
	MENU_MAIN_SETTINGSBUTTON(textureAtlas("images/gui/menubuttons.atlas", "settings")),
	MENU_MAIN_SETTINGSBUTTON_OVER(textureAtlas("images/gui/menubuttons.atlas", "settings_down")),
	MENU_MAIN_LEADERBOARDSBUTTON(textureAtlas("images/gui/menubuttons.atlas", "leaderboards")),
	MENU_MAIN_LEADERBOARDSBUTTON_OVER(
			textureAtlas("images/gui/menubuttons.atlas", "leaderboards_down")),
	MENU_MAIN_STOREBUTTON(textureAtlas("images/gui/menubuttons.atlas", "store")),
	MENU_MAIN_STOREBUTTON_OVER(textureAtlas("images/gui/menubuttons.atlas", "store_down")),
	MENU_MAIN_GLAS(texture("images/gui/mainmenuglas.png")),
	
	MENU_EXTENSION_GLASS(texture("images/gui/extensionmenuglass.png")),
	MENU_SLIDER_BG(textureAtlas("images/gui/menubuttons.atlas", "sliderbackground")),
	MENU_SLIDER_KNOB(textureAtlas("images/gui/menubuttons.atlas", "sliderknob")),
	MENU_SLIDER_0_TO_100_VALUES(textureAtlas("images/gui/menulabels.atlas", "slider0to100values")),
	MENU_SLIDER_LOW_TO_HIGH_VALUES(textureAtlas("images/gui/menulabels.atlas", "sliderlowtohighvalues")),
	
	MENU_SETTINGS_LABEL(textureAtlas("images/gui/menuheadings.atlas", "settings")),
	MENU_SETTINGS_AUDIO_LABEL(textureAtlas("images/gui/menulabels.atlas", "settingsaudiolabel")),
	MENU_SETTINGS_SOUNDEF_LABEL(textureAtlas("images/gui/menulabels.atlas", "settingssoundeffectlabel")),
	MENU_SETTINGS_BGMUSIC_LABEL(textureAtlas("images/gui/menulabels.atlas", "settingsbackgroundsoundlabel")),
	MENU_SETTINGS_SENSE_LABEL(textureAtlas("images/gui/menulabels.atlas", "settingssenslabel")),
	MENU_SETTINGS_TOUCH_LABEL(textureAtlas("images/gui/menulabels.atlas", "settingstouchlabel")),
	
	MENU_ACHIEVEMENT_ENTRY_BG(textureAtlas("images/gui/menulabels.atlas", "achievementEntryBackground")),
	MENU_ACHIEVEMENT_UNACHIEVED(textureAtlas("images/gui/menulabels.atlas", "achievementIconGrey")),
	MENU_ACHIEVEMENT_ACHIEVED(textureAtlas("images/gui/menulabels.atlas", "achievementIconGold")),
	MENU_ACHIEVEMENT_HEADER(textureAtlas("images/gui/menuheadings.atlas", "achievements")),
	
	MENU_LEADERBOARDS_HEADER(textureAtlas("images/gui/menuheadings.atlas", "leaderboards")),
	MENU_LEADERBOARDS_AVATAR(textureAtlas("images/gui/menulabels.atlas", "leaderboardavatar")),
	MENU_LEADERBOARDS_ENTRY_BG(textureAtlas("images/gui/menulabels.atlas", "leaderboardentrybackground")),
	MENU_LEADERBOARDS_HIGHSCOREBUTTON(textureAtlas("images/gui/menubuttons.atlas", "leaderboardhighscorebutton")),
	MENU_LEADERBOARDS_HIGHSCOREBUTTON_OVER(textureAtlas("images/gui/menubuttons.atlas", "leaderboardhighscorebutton_down")),
	MENU_LEADERBOARDS_COLLECTEDCOINBUTTON(textureAtlas("images/gui/menubuttons.atlas", "leaderboardcoins")),
	MENU_LEADERBOARDS_COLLECTEDCOINBUTTON_OVER(textureAtlas("images/gui/menubuttons.atlas", "leaderboardcoins_down")),
	MENU_LEADERBOARDS_LONGESTRUNBUTTON(textureAtlas("images/gui/menubuttons.atlas", "leaderboardlongest")),
	MENU_LEADERBOARDS_LONGESTRUNBUTTON_OVER(textureAtlas("images/gui/menubuttons.atlas", "leaderboardlongest_down")),
	
	MENU_STORE_HEADER(textureAtlas("images/gui/menuheadings.atlas", "store"))
	;

	private TextureHolder holder;

	private TextureDefinitionImpl(TextureHolder holder) {
		this.holder = holder;
	}

	private static TextureHolder texture(String source) {
		return new TextureHolderImpl(source);
	}

	private static TextureHolder textureAtlas(String source, String region) {
		return new TextureHolderAtlasImpl(source, region);
	}

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
