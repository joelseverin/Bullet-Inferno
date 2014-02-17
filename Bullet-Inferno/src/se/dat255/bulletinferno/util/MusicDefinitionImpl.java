package se.dat255.bulletinferno.util;

public enum MusicDefinitionImpl implements MusicDefinition {
	MENU_MUSIC("music/menu.mp3");
	
	private String source;
	
	private MusicDefinitionImpl(String source) {
		this.source = source;
	}
	
	@Override
	public String getSourcePath() {
		return source;
	}

}
