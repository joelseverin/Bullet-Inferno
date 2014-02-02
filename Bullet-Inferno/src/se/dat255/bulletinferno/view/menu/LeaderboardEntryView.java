package se.dat255.bulletinferno.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class LeaderboardEntryView extends Table {
	public static final int WIDTH = 760, HEIGHT = 80;
	
	public LeaderboardEntryView(Image avatar, Drawable background, String name, int score, int rank) {
		setBackground(background);
		// TODO replace with skin
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myraidpro.ttf"));
		BitmapFont nameFont = generator.generateFont(28);
		BitmapFont scoreFont = generator.generateFont(24);
		BitmapFont rankFont = generator.generateFont(34);
		generator.dispose();

		avatar.setSize(HEIGHT, HEIGHT);
		add(avatar).width(HEIGHT);
		
		Label nameLabel = new Label(name, new Label.LabelStyle(nameFont, Color.valueOf("4c7d7d")));
		nameLabel.setY(35);
		Label scoreLabel = new Label(Integer.toString(score), 
				new Label.LabelStyle(scoreFont, Color.valueOf("4b4b4b")));
		scoreLabel.setY(5);
		Label rankLabel = new Label("#" + rank, 
				new Label.LabelStyle(rankFont, Color.valueOf("4c7d7d")));
		
		Group nameScoreGroup = new Group();
		nameScoreGroup.addActor(nameLabel);
		nameScoreGroup.addActor(scoreLabel);
		add(nameScoreGroup).padLeft(20).left().width(410).bottom();
		add(rankLabel).padRight(15).width(WIDTH - HEIGHT - 410 - 20 - 15).right();
		
	}
}
