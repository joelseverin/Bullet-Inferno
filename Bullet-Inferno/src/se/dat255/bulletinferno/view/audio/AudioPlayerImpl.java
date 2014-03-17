package se.dat255.bulletinferno.view.audio;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import se.dat255.bulletinferno.util.GameActionEvent;
import se.dat255.bulletinferno.util.MusicDefinition;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayerImpl implements AudioPlayer, Music.OnCompletionListener {
	private float volume = 1;
	private boolean isMuted = false;
	private boolean isPaused = false;
	private ResourceManager resources;

	private Map<MusicDefinition, Music> musicTracks = new HashMap<MusicDefinition, Music>();
	private Map<Long, Sound> sounds = new HashMap<Long, Sound>();
	
	public AudioPlayerImpl(ResourceManager resourceManager) {
		this.resources = resourceManager;
	}

	public AudioPlayerImpl(ResourceManager resourceManager, float volume) {
		this.resources = resourceManager;
		this.volume = volume;
	}

	@Override
	public void playSoundEffect(GameActionEvent<? extends ResourceIdentifier> e) {
		Sound sound = resources.getSound(e.getSource(), e.getAction());
		sounds.put(sound.play(isMuted? 0 : volume), sound);
	}

	@Override
	public void setVolume(float volume) {
		this.volume = volume;
		validateVolume();
	}

	@Override
	public void playMusic(MusicDefinition musicDefinition) {
		playMusic(musicDefinition, false);
	}

	@Override
	public void playMusic(MusicDefinition musicDefinition, boolean loop) {
		Music music = resources.getMusic(musicDefinition);
		music.play();
		music.setVolume(isMuted? 0 : volume);
		music.setLooping(loop);
		musicTracks.put(musicDefinition, music);
	}

	@Override
	public void stopMusic(MusicDefinition musicDefinition) {
		Music m = musicTracks.get(musicDefinition) ;
		if(m != null) {
			m.stop();
		}
	}

	@Override
	public void mute() {
		isMuted = true;
		validateVolume();
	}

	@Override
	public void unMute() {
		isMuted = false;
		validateVolume();
	}

	@Override
	public void pause() {
		if(isPaused) {
			// Already paused
			return;
		}
		
		for(Music m : musicTracks.values()) {
			m.pause();
		}
		
		for(Sound sound : sounds.values()) {
			sound.pause();
		}
		
	}

	@Override
	public void resume() {
		if(!isPaused) {
			// Already playing
			return;
		}
		
		for(Music m : musicTracks.values()) {
			m.play();
		}
		
		for(Sound sound : sounds.values()) {
			sound.resume();
		}
	}
	
	@Override
	public void setResourceManager(ResourceManager resources) {
		this.resources = resources;
	}

	private void validateVolume() {
		for(Music m : musicTracks.values()) {
			m.setVolume(isMuted? 0 : volume);
		}
		
		for(Entry<Long, Sound> soundEntry : sounds.entrySet()) {
			soundEntry.getValue().setVolume(soundEntry.getKey(), isMuted? 0 : volume);
		}
	}

	@Override
	public void onCompletion(Music music) {
		MusicDefinition definition = null;
		for(Entry<MusicDefinition, Music> entry : musicTracks.entrySet()) {
			if(entry.getValue() == music) {
				definition = entry.getKey();
			}
		}

		// If the music is found
		if(definition != null) {
			musicTracks.remove(definition);
			music.dispose();
		}
	}
	
	@Override
	public void dispose() {
		for(Music m : musicTracks.values()) {
			m.stop();
			m.dispose();
		}
		
		for(Sound sound : sounds.values()) {
			sound.stop();
			sound.dispose();
		}
	}
}
