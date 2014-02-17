package se.dat255.bulletinferno.util;

public enum MusicDefinitionImpl implements MusicDefinition {
	;	
	private String source;
	
	private MusicDefinitionImpl(String source) {
		this.source = source;
	}
	
	@Override
	public String getSourcePath() {
		return source;
	}

}
