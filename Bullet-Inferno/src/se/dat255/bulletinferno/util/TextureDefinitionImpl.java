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

	// Buttons
	BLUE_BACKGROUND(texture("images/game/background.png")),
	GAMEOVER_SCREEN(texture("images/gui/screen_gameover.png")),
	PROJECTILE_RAIN(texture("data/projectileRain.png")),
	
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
	MENU_MR_BEAR(texture("images/gui/mrbear.png")),
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
	
	MENU_STORE_HEADER(textureAtlas("images/gui/menuheadings.atlas", "store")),
	MENU_STORE_BUYBUTTON(textureAtlas("images/gui/menubuttons.atlas", "storebuybutton")),
	MENU_STORE_BUYBUTTON_OVER(textureAtlas("images/gui/menubuttons.atlas", "storebuybutton_down")),
	MENU_STORE_COIN_MEDIUM(textureAtlas("images/gui/menulabels.atlas", "storecoin_medium")),
	MENU_STORE_COIN_LARGE(textureAtlas("images/gui/menulabels.atlas", "storecoin_large")),
	MENU_STORE_BAR_1_DARK(textureAtlas("images/gui/menulabels.atlas", "storebar1_dark")),
	MENU_STORE_BAR_2_DARK(textureAtlas("images/gui/menulabels.atlas", "storebar2_dark")),
	MENU_STORE_BAR_3_DARK(textureAtlas("images/gui/menulabels.atlas", "storebar3_dark")),
	MENU_STORE_BAR_4_DARK(textureAtlas("images/gui/menulabels.atlas", "storebar4_dark")),
	MENU_STORE_BAR_5_DARK(textureAtlas("images/gui/menulabels.atlas", "storebar5_dark")),
	MENU_STORE_BAR_1_LIGHT(textureAtlas("images/gui/menulabels.atlas", "storebar1_light")),
	MENU_STORE_BAR_2_LIGHT(textureAtlas("images/gui/menulabels.atlas", "storebar2_light")),
	MENU_STORE_BAR_3_LIGHT(textureAtlas("images/gui/menulabels.atlas", "storebar3_light")),
	MENU_STORE_BAR_4_LIGHT(textureAtlas("images/gui/menulabels.atlas", "storebar4_light")),
	MENU_STORE_BAR_5_LIGHT(textureAtlas("images/gui/menulabels.atlas", "storebar5_light")), 
	SHIELD_LVL_1(textureAtlas("images/gui/menulabels.atlas", "storeshield")), 
	
	LOADOUTMENU_CLICK_INSTRUCTIONS(textureAtlas("images/gui/menulabels.atlas", "loadoutclikinstruction")), 
	LOADOUTMENU_HEADER(textureAtlas("images/gui/menuheadings.atlas", "loadoutheader")), 
	LOADOUTMENU_GLASS(texture("images/gui/loadoutglass.png")),
	LOADOUTMENU_GLASS_SELECT(texture("images/gui/loadoutselectionglass.png")),
	LOADOUTMENU_DONE_BUTTON(textureAtlas("images/gui/menubuttons.atlas", "loadoutdone")),
	LOADOUTMENU_DONE_BUTTON_DOWN(textureAtlas("images/gui/menubuttons.atlas", "loadoutdone_down")),
	LOADOUTMENU_SPECIAL_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutspeciallabel")),
	LOADOUTMENU_PASSIVE_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutpassivelabel")),
	LOADOUTMENU_STANDARD_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutstandardlabel")),
	LOADOUTMENU_HEAVY_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutheavylabel")),
	LOADOUTMENU_SELECTOR_BUTTON_SMALL(textureAtlas("images/gui/menubuttons.atlas", "loadoutsmallselector")),
	LOADOUTMENU_SELECTOR_BUTTON_SMALL_ACTIVE(textureAtlas("images/gui/menubuttons.atlas", "loadoutsmallselector_active")),
	LOADOUTMENU_SELECTOR_BUTTON_BIG(textureAtlas("images/gui/menubuttons.atlas", "loadoutbigselector")),
	LOADOUTMENU_SELECTOR_BUTTON_BIG_ACTIVE(textureAtlas("images/gui/menubuttons.atlas", "loadoutbigselector_active")),
	LOADOUTMENU_TRIANGLE_BUTTON_UP(textureAtlas("images/gui/menubuttons.atlas", "trianglebuttonup")),
	LOADOUTMENU_TRIANGLE_BUTTON_DOWN(textureAtlas("images/gui/menubuttons.atlas", "trianglebuttondown")),
	LOADOUTMENU_SELECTOR_CHOOSA_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutselectorchoosea")),
	LOADOUTMENU_SELECTOR_STANDARD_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutselectorstandard")),
	LOADOUTMENU_SELECTOR_HEAVY_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutselectorheavy")),
	LOADOUTMENU_SELECTOR_SPECIAL_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutselectorspecial")),
	LOADOUTMENU_SELECTOR_PASSIVE_LABEL(textureAtlas("images/gui/menulabels.atlas", "loadoutselectorpassive")),
	LOADOUTMENU_PLANE(texture("images/gui/loadoutplane.png")),
	
	PAUSEMENU_HEADER(textureAtlas("images/gui/menuheadings.atlas", "pauseheader")),
	PAUSEMENU_RESUME_BUTTON(textureAtlas("images/gui/menubuttons.atlas", "pauseresume")),
	PAUSEMENU_RESUME_BUTTON_DOWN(textureAtlas("images/gui/menubuttons.atlas", "pauseresume_down")),
	PAUSEMENU_MAINMENU_BUTTON(textureAtlas("images/gui/menubuttons.atlas", "pausemainmenu")),
	PAUSEMENU_MAINMENU_BUTTON_DOWN(textureAtlas("images/gui/menubuttons.atlas", "pausemainmenu_down")),
	PAUSEMENU_RESTART_BUTTON(textureAtlas("images/gui/menubuttons.atlas", "pauserestart")),
	PAUSEMENU_RESTART_BUTTON_DOWN(textureAtlas("images/gui/menubuttons.atlas", "pauserestart_down")),
	
	HUD_BAR(texture("images/game/hudbar.png")),
	HUD_SPECIALABILITY_BACKGROUND(textureAtlas("images/game/gameui.atlas", "specialabilitybuttonbackground")),
	HUD_SPECIALABILITY_BUTTON(textureAtlas("images/game/gameui.atlas", "specialabilitybutton")),
	HUD_SPECIALABILITY_BUTTON_DOWN(textureAtlas("images/game/gameui.atlas", "specialabilitybutton_down")),
	HUD_BARMETER_GRADIENT(texture("images/game/barmetergradient.png")),
	HUD_PAUSE_BUTTON(textureAtlas("images/game/gameui.atlas", "pausebutton")),
	HUD_PAUSE_BUTTON_DOWN(textureAtlas("images/game/gameui.atlas", "pausebutton_down")),
	
	LOADIN_ICON_1(textureAtlas("images/gui/loadingicon.atlas", "loadingicon1")),
	LOADIN_ICON_2(textureAtlas("images/gui/loadingicon.atlas", "loadingicon2")),
	LOADIN_ICON_3(textureAtlas("images/gui/loadingicon.atlas", "loadingicon3")),
	LOADIN_ICON_4(textureAtlas("images/gui/loadingicon.atlas", "loadingicon4")),
	LOADIN_ICON_5(textureAtlas("images/gui/loadingicon.atlas", "loadingicon5")),
	LOADIN_ICON_6(textureAtlas("images/gui/loadingicon.atlas", "loadingicon6")),
	LOADIN_ICON_7(textureAtlas("images/gui/loadingicon.atlas", "loadingicon7")),
	LOADIN_ICON_8(textureAtlas("images/gui/loadingicon.atlas", "loadingicon8"))
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
