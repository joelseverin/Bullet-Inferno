package se.dat255.bulletinferno.view.menu;

import java.util.List;

import se.dat255.bulletinferno.model.achievement.Achievement;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AchievementsView extends SimpleToggleSubMenuView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	
	private final static int GLASS_WIDTH = 1182, GLASS_POSITION_X = 285;
	private final static int CELL_PADDING = 30;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private final Drawable unAchievedIcon;
	private final Drawable achievedIcon;
	private final Image header;
	private final TextureRegionDrawable cellBackground;
	private final Table mainTable;
	private final Table achievementsTable;
	private final Stage stage;
	private final Label achievedCountLabel;
	private final ScrollPane scrollPane;
	
	public AchievementsView(Stage stage, ResourceManager resources, 
			List<Achievement> achievements) {
		super(new Group(), GLASS_WIDTH, VIRTUAL_HEIGHT, GLASS_POSITION_X, VIRTUAL_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;
		
		Skin skin = resources.getSkin();
		
		unAchievedIcon = resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_ACHIEVEMENT_UNACHIEVED);
		achievedIcon = resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_ACHIEVEMENT_ACHIEVED);
		cellBackground = resources.getDrawableTexture(
							TextureDefinitionImpl.MENU_ACHIEVEMENT_ENTRY_BG);
		header = new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_ACHIEVEMENT_HEADER));
		achievedCountLabel = new Label("", new Label.LabelStyle(skin.getFont("myraid40"), 
				skin.getColor("darkgreen")));
		
		mainTable = new Table();
		mainTable.setFillParent(true);
		mainTable.setBackground(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_EXTENSION_GLASS));
		mainTable.top();
		mainTable.padTop(100);
		mainTable.add(header).colspan(2);
		mainTable.row();
		mainTable.add(achievedCountLabel).colspan(2).padTop(10).padBottom(40);
		mainTable.row();
		
		achievementsTable = new Table();
		achievementsTable.top();
		int i = 0, achievedCount = 0;
		for(Achievement achievement : achievements) {
			if(i % 2 == 0) {
				achievementsTable.row();
				achievementsTable.add(
						new AchievementEntry(skin, achievedIcon, unAchievedIcon, cellBackground, achievement)
						).padRight(CELL_PADDING).padBottom(CELL_PADDING);
			} else {
				achievementsTable.add(
						new AchievementEntry(skin, achievedIcon, unAchievedIcon, cellBackground, achievement)
						).padBottom(CELL_PADDING);
			}
			
			if(achievement.isAchieved()) {
				achievedCount++;
			}
			i++;
		}
		achievedCountLabel.setText(achievedCount + "/" + i);
		
		scrollPane = new ScrollPane(achievementsTable);
		mainTable.add(scrollPane).top().height(LeaderboardEntryView.DEFAULT_WIDTH);
		
		getToggleActor().addActor(mainTable);
		slideToggle();
		this.stage.addActor(getToggleActor());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		stage.getRoot().removeActor(getToggleActor());
		scrollPane.clear();
	}

}
