package se.dat255.bulletinferno.util;

public enum MusicDefinitionImpl implements MusicDefinition {
	MENU_BACKGROUND("audio/intensity.mp3");	
	private String source;
	
	private MusicDefinitionImpl(String source) {
		this.source = source;
	}
	
	@Override
	public String getSourcePath() {
		return source;
	}

}
