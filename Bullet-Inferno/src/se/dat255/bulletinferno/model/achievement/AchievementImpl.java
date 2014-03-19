package se.dat255.bulletinferno.model.achievement;

public class AchievementImpl implements Achievement {
	private final String id, name, description;
	private boolean isAchieved;
	
	public AchievementImpl(String id, String name, String description, boolean isAchieved) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.isAchieved = isAchieved;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isAchieved() {
		return isAchieved;
	}

}
