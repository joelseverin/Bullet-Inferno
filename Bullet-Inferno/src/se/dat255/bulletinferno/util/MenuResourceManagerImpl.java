package se.dat255.bulletinferno.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuResourceManagerImpl implements ResourceManager {

	protected static final Resolution[] SUPPORTED_RESOLUTIONS = {
				new Resolution(270, 480, ""), // Default resolution
				new Resolution(450, 800, "800450"),
				new Resolution(720, 1280, "1280720"),
				new Resolution(1080, 1920, "19201080")
		};

	protected AssetManager manager;

	public MenuResourceManagerImpl() {
		manager = new AssetManager();

		ResolutionFileResolver resolver = new ResolutionFileResolver(
				new InternalFileHandleResolver(), SUPPORTED_RESOLUTIONS);

		manager.setLoader(Texture.class, new TextureLoader(resolver));
		manager.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
		Texture.setAssetManager(manager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startLoad(boolean blocking) {
		loadTextures();
		loadSoundEffects();
	
		if (blocking) {
			manager.finishLoading();
		}
	}

	@Override
	public Sound getSound(ResourceIdentifier identifier, GameAction action) {
		// not used
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Music getMusic(String identifier) {
		return null;
		// return manager.get(music.get(identifier), Music.class);
	}

	/** Adds all managed textures to the AssetManager's load queue. */
	private void loadTextures() {
		for (TextureDefinition definition : MenuTextureDefinitionImpl.values()) {
			definition.loadSource(manager);
		}
	}

	/** Adds all managed sound effects to the AssetManager's load queue. */
	private void loadSoundEffects() {
		// not used
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
	public TextureRegion getTexture(TextureDefinition textureDefinition) {
		return textureDefinition.getTexture(manager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextureRegion getTexture(ResourceIdentifier resourceIdentifier) {
		String identifier = resourceIdentifier.getIdentifier();
	
		TextureDefinition definition;
		try {
			definition = MenuTextureDefinitionImpl.valueOf(identifier);
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException(
					String.format("Resource with identifier '%s' could not be found", identifier),
					exception);
		}
	
		return getTexture(definition);
	}

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
		for (TextureDefinition definition : MenuTextureDefinitionImpl.values()) {
			definition.dispose();
		}
	
		manager.dispose();
		manager = null;
		Texture.setAssetManager(null);
	}

}