package se.dat255.bulletinferno.view.menu;

import java.util.List;

import se.dat255.bulletinferno.model.achievement.Achievement;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
	private final BitmapFont font;
	private final Stage stage;
	
	public AchievementsView(Stage stage, ResourceManager resources, 
			List<Achievement> achievements) {
		super(new Group(), GLASS_WIDTH, VIRTUAL_HEIGHT, GLASS_POSITION_X, VIRTUAL_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;
		
		unAchievedIcon = resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_ACHIEVEMENT_UNACHIEVED);
		achievedIcon = resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_ACHIEVEMENT_ACHIEVED);
		cellBackground = resources.getDrawableTexture(
							TextureDefinitionImpl.MENU_ACHIEVEMENT_ENTRY_BG);
		font = new BitmapFont(Gdx.files.internal("fonts/berlinsans.fnt"), 
				Gdx.files.internal("fonts/berlinsans_0.png"), false);
		font.setScale(0.5f);
		header = new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_ACHIEVEMENT_HEADER));
		
		mainTable = new Table();
		mainTable.setFillParent(true);
		mainTable.setBackground(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_EXTENSION_GLASS));
		mainTable.top();
		mainTable.padTop(100);
		mainTable.add(header).colspan(2);
		mainTable.row();
		mainTable.debug();
		
		int i = 0;
		for(Achievement achievement : achievements) {
			if(i % 2 == 0) {
				mainTable.row();
				mainTable.add(createAchievementCell(achievement)).padRight(CELL_PADDING)
							.padBottom(CELL_PADDING);
			} else {
				mainTable.add(createAchievementCell(achievement)).padBottom(CELL_PADDING);
			}
			i++;
		}
		
		getToggleActor().addActor(mainTable);
		slideToggle();
		this.stage.addActor(getToggleActor());
	}
	
	private Table createAchievementCell(Achievement achievement) {
		Table table = new Table();
		table.left();
		table.bottom();
		
		Table textTable = new Table();
		textTable.top();
		textTable.padLeft(10);
		table.setBackground(cellBackground);
		table.add(new Image(achievement.isAchieved()? achievedIcon : unAchievedIcon)).padLeft(10);
		
		table.debug();
		
		textTable.add(new Label(achievement.getName(), 
				new Label.LabelStyle(font, Color.valueOf("4c7d7d")))).left();
		textTable.row();
		textTable.add(new Label(achievement.getDescription(), 
				new Label.LabelStyle(font, Color.BLUE))).left();
		table.add(textTable);
		
		return table;
	}
	
	@Override
	public void dispose() {
		
	}

}
