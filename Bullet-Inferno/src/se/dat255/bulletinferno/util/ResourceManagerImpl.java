package se.dat255.bulletinferno.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Definition of all the assets that should be handled by the resource manager
 */
public class ResourceManagerImpl implements ResourceManager {
	public enum TextureType {
		DEFAULT_SHIP("data/defaultEnemy.png"),
		FAST_SHIP("data/defaultEnemy.png"),
		SLOW_SHIP("data/defaultEnemy.png"),
		MAP_MOUNTAIN("images/game/mountain.png"),

		DEFAULT_ENEMY_SHIP("data/defaultEnemy.png"),
		SPECIAL_ENEMY_SHIP("data/specialEnemy.png"),
		HARD_BOSS_SHIP("data/boss.png"),
		EASY_BOSS_SHIP("data/bossEnemy.png"),

		// Player ship
		PLAYER_DEFAULT("data/playerShip.png"),
		PLAYER_EXPLOSION("data/explosion.gif"),

		// Weapons
		MISSILE_LAUNCHER("data/missileLauncher.png"),
		DISORDERER("data/disorderer.png"),
		MISSILE_LAUNCHER_LARGE("data/missileLauncherLarge.png"),
		DISORDERER_LARGE("data/disordererLarge.png"),
		SNIPER_RIFLE("data/sniperRifle.png"),

		// Projectiles
		RED_PROJECTILE("data/redDotProjectile.png"),
		GREEN_PROJECTILE("data/greenDotProjectile.png"),
		MISSILE("data/missile.png"),
		PLASMA("data/plasma.png"),
		HIGH_VELOCITY_PROJECTILE("data/missile.png"),

		// Buttons
		PAUSE_SCREEN("images/gui/screen_pause.png"),
		BLUE_BACKGROUND("images/game/background.png"),
		GAMEOVER_SCREEN("images/gui/screen_gameover.png"),
		PROJECTILE_RAIN("data/projectileRain.png"),
		TAKE_DAMAGE_MODIFIER("data/shieldMenu.png"),
		LOADOUT_START_BUTTON("data/startBtn.png"),
		HUD_TEXTURE("images/game/hud.png"),

		// Particles
		SMOKE_PARTICLE("images/particles/smoke.png"),

		;

		private final String path;

		TextureType(String path) {
			this.path = path;
		}

		private Texture getTexture(AssetManager manager) {
			return manager.get(this.path, Texture.class);
		}

		public String getPath() {
			return this.path;
		}
	}
	
	public enum SoundEffectType {
		DEFAULT_ENEMY_SHIP();
		
		static {
			DEFAULT_ENEMY_SHIP.mapping.put("DIED", "data/explosion.mp3");
		}
		
		private final Map<String, String> mapping = new HashMap<String, String>();
		
		private SoundEffectType() {}
		
		public String getSource(String key) {
			return mapping.get(key);
		}
	}
	
	private AssetManager manager;

	public ResourceManagerImpl() {
		manager = new AssetManager();
		Texture.setAssetManager(manager);
	}

	/**
	 * {@inheritDoc}
	 */
	public void startLoad(boolean blocking) {
		loadTextures();
		loadSoundEffects();
		
		if(blocking) {
			manager.finishLoading();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sound getSound(ResourceIdentifier identifier, GameAction action) {
		for (SoundEffectType soundEffectType : SoundEffectType.values()) {
			if (identifier.getIdentifier().equals(soundEffectType.name())) {
				return manager.get(soundEffectType.getSource(action.getAction()), Sound.class);
			}
		}
		
		throw new RuntimeException("Sound not found for that identifier.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Music getMusic(String identifier) {
		return null;
		//return manager.get(music.get(identifier), Music.class);
	}

	/** Adds all managed textures to the AssetManager's load queue. */
	private void loadTextures() {
		for (TextureType type : TextureType.values()) {
			manager.load(type.path, Texture.class);
		}
	}
	
	/** Adds all managed sound effects to the AssetManager's load queue. */
	private void loadSoundEffects() {
		for (SoundEffectType type : SoundEffectType.values()) {
			for(String src : type.mapping.values()) {
				manager.load(src, Sound.class);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unload(String path) {
		if (manager.isLoaded(path, Texture.class)) {
			manager.unload(path);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture getTexture(TextureType textureType) {
		if (manager.isLoaded(textureType.getPath(), Texture.class)) {
			return textureType.getTexture(manager);
		} else {
			throw new RuntimeException("Texture " + textureType.name() + " is not loaded.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture getTexture(ResourceIdentifier resourceIndentifier) {
		String identifier = resourceIndentifier.getIdentifier();

		TextureType type;
		try {
			type = TextureType.valueOf(identifier);
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException("resource identifier not found", exception);
		}

		return getTexture(type);
	}

	// TODO: Implement loading methods for sound and music

	@Override
	public boolean loadAsync() {
		return manager.update();
	}

	@Override
	public float getLoadProgress() {
		return manager.getProgress();
	}

	@Override
	public void dispose() {
		manager.dispose();
		manager = null;
		Texture.setAssetManager(null);
	}

}
