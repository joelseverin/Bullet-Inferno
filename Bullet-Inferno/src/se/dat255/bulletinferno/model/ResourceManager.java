package se.dat255.bulletinferno.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A class that holds mappings between types of objects in the game and static resources
 * such as {@link Texture}, {@link Sprite}, {@link Sound} and {@link Music}. Resources
 * are retrieved using a String identifier. To use the
 * textures, they must first be loaded with {@link #load()}, which uses the libGDX built-in
 * {@link AssetManager} to do asynchronous loading.
 * 
 */
public interface ResourceManager {

	/**
	 * Returns the loaded {@link Texture} that is mapped to this identifier.
	 * 
	 * @param identifier
	 *        The identifier for this {@link Texture}.
	 * @return The {@link Texture}.
	 */
	Texture getTexture(String identifier);

	/**
	 * Returns the loaded {@link Sprite} that is mapped to this identifier.
	 * 
	 * @param identifier
	 *        The identifier for this {@link Sprite}.
	 * @return The {@link Sprite}.
	 */
	Sprite getSprite(String identifier);

	/**
	 * Returns the loaded {@link Sound} that is mapped to this identifier.
	 * 
	 * @param identifier
	 *        The identifier for this {@link Sound}.
	 * @return The {@link Sound}.
	 */
	Sound getSound(String identifier);

	/**
	 * Returns the loaded {@link Music} that is mapped to this identifier.
	 * 
	 * @param identifier
	 *        The identifier for this {@link Music}.
	 * @return The {@link Music}.
	 */
	Music getMusic(String identifier);

	/**
	 * Loads all the resources using an {@link AssetManager} to do asynchronous loading.
	 */
	void load();

}