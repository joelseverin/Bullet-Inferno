package se.dat255.bulletinferno.view.menu;

import java.util.List;

import javax.xml.ws.handler.MessageContext.Scope;

import se.dat255.bulletinferno.model.leaderboard.LeaderboardEntry;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinition;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class LeaderboardsView extends SimpleToggleSubMenuView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	public static enum LeaderboardType {
		HIGHSCORE, COLLECTED_COINS, LONGEST_RUN;
	}
	
	private final static int GLASS_WIDTH = 1182, GLASS_POSITION_X = 285;
	private final static int CELL_PADDING = 20;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private final Image header;
	
	private final Drawable entryBackground, entryAvatar;
	
	private final Table mainTable;
	private final Table highScoreEntriesTable = new Table();
	private final Table collectedCoinsEntriesTable = new Table();
	private final Table longestRunEntriesTable = new Table();
	private final Group leaderBoardWrapper = new Group();
	private final BitmapFont subheaderFont;
	private final BitmapFont textFont;
	private final Stage stage;
	private final ScrollPane scrollPane;
	private final Button highScoreListButton, collectedCoinsListButton, longestRunListButton;
	
	public LeaderboardsView(Stage stage, ResourceManager resources, 
			List<LeaderboardEntry> highscoreEntries, List<LeaderboardEntry> collectedCoinEntries,
			List<LeaderboardEntry> longestRunEntries) {
		super(new Group(), GLASS_WIDTH, VIRTUAL_HEIGHT, GLASS_POSITION_X, VIRTUAL_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;
		
		// TODO : Merge with resource handler
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myraidpro.ttf"));
		subheaderFont = generator.generateFont(28);
		textFont = generator.generateFont(24);
		generator.dispose();
				
		header = new Image(
				resources.getDrawableTexture(TextureDefinitionImpl.MENU_LEADERBOARDS_HEADER));
		entryBackground = resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_LEADERBOARDS_ENTRY_BG);
		entryAvatar = resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_LEADERBOARDS_AVATAR);
		
		highScoreListButton = new Button(
			resources.getDrawableTexture(TextureDefinitionImpl.MENU_LEADERBOARDS_HIGHSCOREBUTTON), 
			resources.getDrawableTexture(
					TextureDefinitionImpl.MENU_LEADERBOARDS_HIGHSCOREBUTTON_OVER),
			resources.getDrawableTexture(
					TextureDefinitionImpl.MENU_LEADERBOARDS_HIGHSCOREBUTTON_OVER));
		collectedCoinsListButton = new Button(
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_LEADERBOARDS_COLLECTEDCOINBUTTON), 
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_LEADERBOARDS_COLLECTEDCOINBUTTON_OVER),
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_LEADERBOARDS_COLLECTEDCOINBUTTON_OVER));
		longestRunListButton = new Button(
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_LEADERBOARDS_LONGESTRUNBUTTON), 
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_LEADERBOARDS_LONGESTRUNBUTTON_OVER),
				resources.getDrawableTexture(
						TextureDefinitionImpl.MENU_LEADERBOARDS_LONGESTRUNBUTTON_OVER));
		
		mainTable = new Table();
		mainTable.setFillParent(true);
		mainTable.setBackground(resources.getDrawableTexture(
				TextureDefinitionImpl.MENU_EXTENSION_GLASS));
		mainTable.top();
		mainTable.padTop(100);
		mainTable.add(header);
		mainTable.row();

		Table tabBar = new Table();
		tabBar.add(highScoreListButton).right();
		tabBar.add(collectedCoinsListButton);
		tabBar.add(longestRunListButton).left();
		mainTable.add(tabBar).padTop(30);
		mainTable.row();
		
		highScoreEntriesTable.top();
		collectedCoinsEntriesTable.top();
		longestRunEntriesTable.top();
		updateLeaderboard(LeaderboardType.HIGHSCORE, highscoreEntries);
		updateLeaderboard(LeaderboardType.COLLECTED_COINS, collectedCoinEntries);
		updateLeaderboard(LeaderboardType.LONGEST_RUN, longestRunEntries);
		
		scrollPane = new ScrollPane(leaderBoardWrapper);
		mainTable.add(scrollPane).top().height(770).width(760).padTop(30);

		showLeaderboard(LeaderboardType.HIGHSCORE);
		
		getToggleActor().addActor(mainTable);
		slideToggle();
		this.stage.addActor(getToggleActor());
	}
	
	public void updateLeaderboard(LeaderboardType type, List<LeaderboardEntry> entries) {
		Table temp = getLeaderboardFromType(type);
		temp.clearChildren();

		int i = 1;
		for(LeaderboardEntry entry : entries) {
			temp.add(new LeaderboardEntryView(new Image(entryAvatar), entryBackground, 
					entry.getName(), entry.getScore(), i++)).padTop(CELL_PADDING).height(LeaderboardEntryView.HEIGHT);
			temp.row();
		}
		
		
	}
	
	public void showLeaderboard(LeaderboardType type) {
		scrollPane.setWidget(getLeaderboardFromType(type));
		highScoreListButton.setChecked(false);
		collectedCoinsListButton.setChecked(false);
		longestRunListButton.setChecked(false);
		
		switch (type) {
			case HIGHSCORE:
				highScoreListButton.setChecked(true);
				break;
			case COLLECTED_COINS:
				collectedCoinsListButton.setChecked(true);
				break;
			default:
				longestRunListButton.setChecked(true);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		stage.getRoot().removeActor(getToggleActor());
		scrollPane.clear();
	}

	private Table getLeaderboardFromType(LeaderboardType type) {
		switch (type) {
			case HIGHSCORE:
				return highScoreEntriesTable;
			case COLLECTED_COINS:
				return collectedCoinsEntriesTable;
			default:
				return longestRunEntriesTable;
			
		}
	}
	
}
