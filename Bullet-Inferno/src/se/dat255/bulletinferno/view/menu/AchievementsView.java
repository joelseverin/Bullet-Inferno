package se.dat255.bulletinferno.view.menu;

import java.util.List;

import javax.xml.ws.handler.MessageContext.Scope;

import se.dat255.bulletinferno.model.achievement.Achievement;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
	private final BitmapFont subheaderFont;
	private final BitmapFont textFont;
	private final Stage stage;
	private final Label achievedCountLabel;
	private final ScrollPane scrollPane;
	
	public AchievementsView(Stage stage, ResourceManager resources, 
			List<Achievement> achievements) {
		super(new Group(), GLASS_WIDTH, VIRTUAL_HEIGHT, GLASS_POSITION_X, VIRTUAL_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;
		
		// TODO : Merge with resource handler
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myraidpro.ttf"));
		subheaderFont = generator.generateFont(28);
		textFont = generator.generateFont(24);
		BitmapFont font34 = generator.generateFont(34);
		generator.dispose();
		
		unAchievedIcon = resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_ACHIEVEMENT_UNACHIEVED);
		achievedIcon = resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_ACHIEVEMENT_ACHIEVED);
		cellBackground = resources.getDrawableTexture(
							TextureDefinitionImpl.MENU_ACHIEVEMENT_ENTRY_BG);
		header = new Image(resources.getDrawableTexture(TextureDefinitionImpl.MENU_ACHIEVEMENT_HEADER));
		achievedCountLabel = new Label("", new Label.LabelStyle(font34, Color.valueOf("4c7d7d")));
		
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
				achievementsTable.add(createAchievementCell(achievement)).padRight(CELL_PADDING)
							.padBottom(CELL_PADDING);
			} else {
				achievementsTable.add(createAchievementCell(achievement)).padBottom(CELL_PADDING);
			}
			
			if(achievement.isAchieved()) {
				achievedCount++;
			}
			i++;
		}
		achievedCountLabel.setText(achievedCount + "/" + i);
		
		scrollPane = new ScrollPane(achievementsTable);
		mainTable.add(scrollPane).top().height(770);
		
		getToggleActor().addActor(mainTable);
		slideToggle();
		this.stage.addActor(getToggleActor());
	}
	
	private Table createAchievementCell(Achievement achievement) {
		Table table = new Table();
		table.left();
		table.bottom();
		
		table.setBackground(cellBackground);
		table.add(new Image(achievement.isAchieved()? achievedIcon : unAchievedIcon))
				.padLeft(10).bottom();
		
		Table textTable = new Table();
		textTable.top();
		textTable.padLeft(20);
		textTable.add(new Label(achievement.getName(), 
				new Label.LabelStyle(subheaderFont, Color.valueOf("4c7d7d")))).left().padTop(30);
		textTable.row();
		
		Label l = new Label(achievement.getDescription(), new Label.LabelStyle(textFont, 
				Color.valueOf("4b4b4b")));
		l.setWrap(true);
		textTable.add(l).left().width(280);
		
		table.add(textTable).height(150);
		
		return table;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		stage.getRoot().removeActor(getToggleActor());
		scrollPane.clear();
	}

}
