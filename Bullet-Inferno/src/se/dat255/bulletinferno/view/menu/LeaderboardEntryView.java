package se.dat255.bulletinferno.view.menu;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * An entry view that displays a score record consisting of the record taker's name, score, rank
 * and avatar(profile picture).
 * 
 * @author Sebastian Blomberg
 *
 */
public class LeaderboardEntryView extends Table {
	public static final int DEFAULT_WIDTH = 760, DEFAULT_HEIGHT = 80;
	
	// Cache variables
	private static LabelStyle nameLabelStyle = null, scoreLabelStyle = null, rankLabelStyle = null;
	
	/**
	 * Constructs a new score entry. The avatar will be scaled to cover 100% of 
	 * the view's height ({@link LeaderboardEntryView#DEFAULT_WIDTH} and will be scaled to be 
	 * quadratically shaped.
	 * 
	 * @param skin
	 * @param avatar The record taker's avatar (profile picture). 
	 * @param background
	 * @param name
	 * @param score
	 * @param rank
	 */
	public LeaderboardEntryView(Skin skin, Image avatar, Drawable background, String name,
			long score, long rank) {
		// Initiate the cache variables
		if(nameLabelStyle == null) {
			nameLabelStyle = new Label.LabelStyle(skin.getFont("myraid34"), 
					skin.getColor("darkgreen"));
		}
		if(scoreLabelStyle == null) {
			scoreLabelStyle = new Label.LabelStyle(skin.getFont("myraid28"), skin.getColor("grey"));
		}
		if(rankLabelStyle == null) {
			rankLabelStyle = new Label.LabelStyle(skin.getFont("myraid40"), 
					skin.getColor("darkgreen"));
		}

		setBackground(background);
		
		avatar.setSize(DEFAULT_HEIGHT, DEFAULT_HEIGHT);
		add(avatar).width(DEFAULT_HEIGHT);
		
		Label nameLabel = new Label(name, nameLabelStyle);
		nameLabel.setY(35);
		Label scoreLabel = new Label(Long.toString(score), scoreLabelStyle);
		scoreLabel.setY(5);
		Label rankLabel = new Label("#" + rank, rankLabelStyle);
		
		Group nameScoreGroup = new Group();
		nameScoreGroup.addActor(nameLabel);
		nameScoreGroup.addActor(scoreLabel);
		add(nameScoreGroup).padLeft(20).left().width(410).bottom();
		add(rankLabel).padRight(15).width(DEFAULT_WIDTH - DEFAULT_HEIGHT - 410 - 20 - 15).right();
		
	}
}
