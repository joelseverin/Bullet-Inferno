package se.dat255.bulletinferno.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class for rendering of the loading screen.
 */
public class LoadingScreenView implements Disposable {
	public final static int VIRTUAL_HEIGHT = 1080, VIRTUAL_WIDTH = 1920;
	private final static int LOADING_BAR_HEIGHT = 80, LOADING_BAR_WIDTH = 14,
			LOADING_BAR_POSITION_X = 645, LOADING_BAR_POSITION_Y = 170,
			LOADING_BAR_PADDING_X = 18;
	private final static Color BACKGROUND_COLOR = new Color(0.1797f, 0.1797f, 0.1719f, 1f);
	private final static Color LOADINGBAR_FILLED_COLOR = new Color(0.2980f, 0.490196f, 
			0.490196f, 1f);
	private final static Color LOADINGBAR_UNFILLED_COLOR = new Color(0.117647f, 0.117647f, 
			0.117647f, 1f);

	/** Text format to be displayed while the loading is in progress */
	private final static String PROGRESS_LABEL_TEXT = "Loading... %d%%";
	/** Text to be displayed when the loading is finished */
	private final static String ON_FINISHED_LABEL_TEXT = "Touch to Start!";

	private final Stage stage;
	
	private float percent = 0;
	private int barsFilled = 0;
	
	// Should be disposed GUI elements
	private final Texture screenBgTexture, logoTexture; 
	private final TextureRegionDrawable loadinbarFilledTexture, loadinbarUnfilledTexture;
	private final BitmapFont progressLabelFont;

	// GUI elements
	private final Label progressLabel;
	private final Image background, logo;
	private final Image[] loadingBars = new Image[20];
	
	/**
	 * Creates a new LoadingScreenView.
	 * 
	 * <p>
	 * <b>Note:</b> This will load textures directly, and it is therefore important to remember to
	 * dispose this view!
	 * </p>
	 * @param Stage The stage to be used in the view.
	 */
	public LoadingScreenView(Stage stage) {
		this.stage = stage;
		if(stage.getHeight() != VIRTUAL_HEIGHT || stage.getWidth() != VIRTUAL_WIDTH) {
			stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		}
		
		// Create background
		Pixmap p = new Pixmap(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, Format.RGBA8888);
		p.setColor(BACKGROUND_COLOR);
		p.fillRectangle(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		screenBgTexture = new Texture(p);
		background = new Image(screenBgTexture);
		p.dispose();
		stage.addActor(background);
		
		// Create logo
		logoTexture = new Texture(Gdx.files.internal("images/loadingscreen/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		logo = new Image(logoTexture);
		logo.setPosition(352, 280);
		stage.addActor(logo);
		
		// Create filled loading bar texture
		p = new Pixmap(LOADING_BAR_WIDTH, LOADING_BAR_HEIGHT, Format.RGBA8888);
		p.setColor(LOADINGBAR_FILLED_COLOR);
		p.fillRectangle(0, 0, LOADING_BAR_WIDTH, LOADING_BAR_HEIGHT);
		loadinbarFilledTexture = new TextureRegionDrawable(new TextureRegion(new Texture(p)));
		
		// Create unfilled loading bar texture
		p.setColor(LOADINGBAR_UNFILLED_COLOR);
		p.fillRectangle(0, 0, LOADING_BAR_WIDTH, LOADING_BAR_HEIGHT);
		loadinbarUnfilledTexture = new TextureRegionDrawable(new TextureRegion(new Texture(p)));
		p.dispose();
		
		// Create the loading bar images
		int x = LOADING_BAR_POSITION_X;
		for(int i = 0; i < 20; i++) {
			loadingBars[i] = new Image(loadinbarUnfilledTexture);
			loadingBars[i].setPosition(x, LOADING_BAR_POSITION_Y);
			x += LOADING_BAR_WIDTH + LOADING_BAR_PADDING_X;
			stage.addActor(loadingBars[i]);
		}
		
		// Progress label
		progressLabelFont = new BitmapFont(Gdx.files.internal("fonts/myraid/myraid32.fnt"),
				false);
		progressLabelFont.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		LabelStyle ls = new LabelStyle(progressLabelFont, LOADINGBAR_FILLED_COLOR);
		progressLabel = new Label("Loading", ls);
		progressLabel.setPosition(875, 110);
		stage.addActor(progressLabel);
	}

	/**
	 * Sets the displayed loadProgress.
	 * 
	 * @param loadProgress
	 *        the current load progress, in percentage from 0 to 1.
	 */
	public void setLoadProgress(float loadProgress) {
		if(loadProgress > percent + 0.01) {
			progressLabel.setText("Loading " + (int) (loadProgress * 100) + "%");
			percent = loadProgress;
			
			int barsToBeFilled = Math.min((int) (loadProgress * 20), 19); 
			
			if(barsToBeFilled > barsFilled) {
				for(; barsFilled <= barsToBeFilled; barsFilled++) {
					loadingBars[barsFilled].setDrawable(loadinbarFilledTexture);
				}
			}
		} else if (loadProgress < percent - 0.01) {
			progressLabel.setText("Loading " + (int) (loadProgress * 100) + "%");
			percent = loadProgress;
			
			int barsToBeFilled = Math.max((int) (loadProgress * 20), 0); 
			
			if(barsToBeFilled < barsFilled) {
				for(; barsFilled > barsToBeFilled; barsFilled--) {
					loadingBars[barsFilled].setDrawable(loadinbarUnfilledTexture);
				}
			}
		}
	}

	/**
	 * Sets the loading to finished and displays the finished loading message.
	 */
	public void loadingFinished() {
		if (!progressLabel.getText().equals(ON_FINISHED_LABEL_TEXT)) {
			progressLabel.setText(ON_FINISHED_LABEL_TEXT);
			for(; barsFilled < 20; barsFilled++) {
				loadingBars[barsFilled].setDrawable(loadinbarFilledTexture);
			}
		}
	}

	@Override
	public void dispose() {
		screenBgTexture.dispose();
		logoTexture.dispose();
		loadinbarFilledTexture.getRegion().getTexture().dispose();
		loadinbarUnfilledTexture.getRegion().getTexture().dispose();
		progressLabelFont.dispose();
		
		for(Image i : loadingBars) {
			stage.getRoot().removeActor(i);
		}
		stage.getRoot().removeActor(background);
		stage.getRoot().removeActor(logo);
		stage.getRoot().removeActor(progressLabel);
		progressLabelFont.dispose();
	}

}
