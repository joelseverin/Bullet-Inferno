package se.dat255.bulletinferno.view.menu;

import java.util.List;

import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;
import se.dat255.bulletinferno.util.userconnectivity.Leaderboard;
import se.dat255.bulletinferno.util.userconnectivity.LeaderboardEntry;
import se.dat255.bulletinferno.view.menu.widget.LoadingIcon;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * A view to display leaderboards. The view contains three different leaderboards (see 
 * {@link LeaderboardsView.LeaderboardType}) which can be toggled by the user.
 * 
 * @author Sebastian Blomberg
 *
 */
public class LeaderboardsView extends SimpleToggleSubMenuView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;

	private final static int GLASS_WIDTH = 1182, GLASS_POSITION_X = 285;
	private final static int CELL_PADDING = 20;
	private final static float GLASS_ANIMATION_DURATION = 0.4f;
	
	private final Image header;
	
	private final LoadingIcon loadingIcon;
	private final Table loadingIconWrapper = new Table();
	
	private final Drawable entryBackground, entryAvatar;
	private final ResourceManager resources;
	private final Table mainTable;
	private final Table highScoreEntriesTable = new Table();
	private final Table collectedCoinsEntriesTable = new Table();
	private final Table longestRunEntriesTable = new Table();
	private final Group leaderBoardWrapper = new Group();
	private final Stage stage;
	private final ScrollPane scrollPane;
	private final Button highScoreListButton, collectedCoinsListButton, longestRunListButton;
	private final ButtonGroup buttonGroup;
	
	public LeaderboardsView(Stage stage, ResourceManager resources) {
		super(new Group(), GLASS_WIDTH, VIRTUAL_HEIGHT, GLASS_POSITION_X, VIRTUAL_HEIGHT, 
				GLASS_ANIMATION_DURATION);
		this.stage = stage;
		this.resources = resources;
				
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
		buttonGroup = new ButtonGroup(highScoreListButton, collectedCoinsListButton,
				longestRunListButton);
		buttonGroup.setMaxCheckCount(1);
		buttonGroup.setMinCheckCount(1);
		
		loadingIcon = new LoadingIcon(
						resources.getDrawableTexture(TextureDefinitionImpl.LOADIN_ICON_1),
						resources.getDrawableTexture(TextureDefinitionImpl.LOADIN_ICON_2),
						resources.getDrawableTexture(TextureDefinitionImpl.LOADIN_ICON_3),
						resources.getDrawableTexture(TextureDefinitionImpl.LOADIN_ICON_4),
						resources.getDrawableTexture(TextureDefinitionImpl.LOADIN_ICON_5),
						resources.getDrawableTexture(TextureDefinitionImpl.LOADIN_ICON_6),
						resources.getDrawableTexture(TextureDefinitionImpl.LOADIN_ICON_7),
						resources.getDrawableTexture(TextureDefinitionImpl.LOADIN_ICON_8));
		
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
		
		scrollPane = new ScrollPane(leaderBoardWrapper);
		mainTable.add(scrollPane).top().height(770).width(760).padTop(30);
		
		loadingIconWrapper.add(loadingIcon);
		
		//highScoreListButton.setChecked(true);
		
		getToggleActor().addActor(mainTable);
		slideToggle();
		this.stage.addActor(getToggleActor());
	}
	
	/**
	 * Updates the data of the specified leaderboard
	 * @param leaderboard
	 * @param entries to be put in the leaderboard
	 */
	public void updateLeaderboard(Leaderboard leaderboard, List<LeaderboardEntry> entries) {
		Table temp = getLeaderboardFromType(leaderboard);
		temp.clearChildren();
		Skin skin = resources.getSkin();
		
		int i = 1;
		for(LeaderboardEntry entry : entries) {
			temp.add(new LeaderboardEntryView(skin, new Image(entryAvatar), entryBackground, 
						entry.getName(), entry.getScore(), i++))
					.padTop(CELL_PADDING).height(LeaderboardEntryView.DEFAULT_HEIGHT);
			temp.row();
		}
		
		
	}
	
	/**
	 * Displays the specified leaderboard and sets associated button as checked.
	 * <strong>OBSERVE</strong> as this method changes the state of the previously checked
	 * button and the associated button, a call will be sent to any ChangeListeners registered on 
	 * the previously checked button.
	 * @param leaderboard
	 */
	public void showLeaderboard(Leaderboard leaderboard) {
		scrollPane.setWidget(getLeaderboardFromType(leaderboard));
	}
	
	public void showLoadingIcon() {
		scrollPane.setWidget(loadingIconWrapper);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		stage.getRoot().removeActor(getToggleActor());
		scrollPane.clear();
		highScoreListButton.clear();
		collectedCoinsListButton.clear();
		longestRunListButton.clear();
	}

	public void addHighScoreBoardButtonListener(EventListener listener) {
		highScoreListButton.addListener(listener);
	}
	
	public void removeHighScoreBoardButtonListener(EventListener listener) {
		highScoreListButton.removeListener(listener);
	}
	
	public void addCollectedCoinsBoardButtonListener(EventListener listener) {
		collectedCoinsListButton.addListener(listener);
	}
	
	public void removeCollectedCoinsBoardButtonListener(EventListener listener) {
		collectedCoinsListButton.removeListener(listener);
	}
	
	public void addLongestRunBoardButtonListener(EventListener listener) {
		longestRunListButton.addListener(listener);
	}
	
	public void removeLongestRunBoardButtonListener(EventListener listener) {
		longestRunListButton.removeListener(listener);
	}
	
	private Table getLeaderboardFromType(Leaderboard type) {
		String name = type.getName(); 
		if("Highscore" == name) {
				return highScoreEntriesTable;
		} else if ("Collected coins" == name) {
				return collectedCoinsEntriesTable;
		} else if("Longest run" == name) {
				return longestRunEntriesTable;
		} else {
			return null;
		}
		
	}
	
}
