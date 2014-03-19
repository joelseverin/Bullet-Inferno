package se.dat255.bulletinferno.view.audio;

import com.badlogic.gdx.utils.Disposable;

import se.dat255.bulletinferno.util.GameActionEvent;
import se.dat255.bulletinferno.util.MusicDefinition;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;

/**
 * An implementation of an audio player to wrap the audio functionalities of Libgdx. <br/>
 * The AudioPlayer works with a {@link ResourceManager} to retrieve pre-loaded sounds and music, but
 * may, if not specified, load files live; this may, however, cause delay.
 * 
 * <strong>Make sure</strong> to call the {@link AudioPlayer#dispose()} when the audio player is no
 * longer in use. This will stop and free all resources in use.
 * 
 * @author Sebastian Blomberg
 *
 */
public interface AudioPlayer extends Disposable {
	/**
	 * Plays a sound effect from the specified event
	 * 
	 * @param event
	 *        the event to play a sound for.
	 */
	public void playSoundEffect(GameActionEvent<? extends ResourceIdentifier> event);

	/**
	 * Plays the music held by the definition. The music will be, if loaded, retrieved from the 
	 * active ResourceManager (see {@link AudioPlayer#setResourceManager(ResourceManager)}); if not
	 * it will be loaded and stream live which may cause delay. 
	 * @param musicDefinition
	 */
	public void playMusic(MusicDefinition musicDefinition);
	
	/**
	 * Plays the music held by the definition. The music will be, if loaded, retrieved from the 
	 * active ResourceManager (see {@link AudioPlayer#setResourceManager(ResourceManager)}); if not
	 * it will be loaded and stream live which may cause delay
	 * @param musicDefinition
	 * @param loop Whether or not to loop
	 */
	public void playMusic(MusicDefinition musicDefinition, boolean loop);
	
	/**
	 * Stops the playback of the specified definition
	 * @param musicDefinition
	 */
	public void stopMusic(MusicDefinition musicDefinition);
	
	/**
	 * Sets the volume of the audio player, in the range of 0 to 1
	 * 
	 * @param volume
	 *        the playback volume, between 0 and 1.
	 */
	public void setVolume(float volume);
	
	/**
	 * Mutes all the audio played by this Audio Player
	 */
	public void mute();
	
	/**
	 * Unmutes all the audio played by this Audio Player
	 */
	public void unMute();
	
	/**
	 * Pauses all music and sounds played by this player
	 */
	public void pause();
	
	/**
	 * Resumes all music and sounds played by this player 
	 */
	public void resume();
	
	/**
	 * Sets the resources manager in which to search and retrieve the resources
	 * required to play the requested music/sounds
	 * @param resources
	 */
	public void setResourceManager(ResourceManager resources);
}
