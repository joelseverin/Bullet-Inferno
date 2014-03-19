package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.model.achievement.Achievement;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class AchievementEntry extends Table {
	// Cache variables
	private static LabelStyle headerStyle = null, textstyle = null;;
	
	public AchievementEntry(Skin skin, Drawable achievedIcon, Drawable unAchievedIcon, Drawable background, 
			Achievement achievement) {
		left();
		bottom();
		setBackground(background);
		if(headerStyle == null) {
			headerStyle = new Label.LabelStyle(skin.getFont("myraid34"), skin.getColor("darkgreen"));
		}
		if(textstyle == null) {
			textstyle = new Label.LabelStyle(skin.getFont("myraid28"), skin.getColor("grey"));
		}
		
		add(new Image(achievement.isAchieved()? achievedIcon : unAchievedIcon))
				.padLeft(10).bottom();
		
		Table textTable = new Table();
		textTable.top();
		textTable.padLeft(20);
		textTable.add(new Label(achievement.getName(), headerStyle)).left().padTop(30);
		textTable.row();
		
		Label l = new Label(achievement.getDescription(), textstyle);
		l.setWrap(true);
		textTable.add(l).left().width(280);
		
		add(textTable).height(150);
		
	}
}
