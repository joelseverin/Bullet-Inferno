package se.dat255.bulletinferno.model.map;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import se.dat255.bulletinferno.model.Game;

import com.badlogic.gdx.math.Vector2;

public enum SliceType {
	WATER(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_1(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_2(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_3(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_4(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_5(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_6(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_7(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	MOUNTAIN_8(0, 0, 20f, Collections.<ObstaclePlacement>emptyList()),
	SIMPLE_GROUND(2f, 2f, 20f, Arrays.asList(place(ObstacleDefinitionImpl.FLAT_GROUND, 0, 0)))
	;
	
	private final float entryHeight;
	private final float exitHeight;
	private final float width;
	private final List<? extends ObstaclePlacement> obstaclePlacements;
	
	SliceType(float entryHeight, float exitHeight, float width, 
			List<? extends ObstaclePlacement> obstaclePlacements){
		this.entryHeight = entryHeight;
		this.exitHeight = exitHeight;
		this.width = width;
		this.obstaclePlacements = obstaclePlacements;
	}

	public Slice getSlice(Game game, Vector2 position) {
		return new SliceImpl(game, this, entryHeight, exitHeight, position, width,
				obstaclePlacements);
	}
	
	/**
	 * Internal helper to create an {@link ObstaclePlacement} quickly for enum definitions.
	 */
	private static ObstaclePlacement place(ObstacleDefinition definition, float x, float y) {
		return new ObstaclePlacementImpl(definition, new Vector2(x, y));
	}
	
}