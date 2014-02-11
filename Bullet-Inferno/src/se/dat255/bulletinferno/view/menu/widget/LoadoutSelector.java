package se.dat255.bulletinferno.view.menu.widget;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.util.Descriptable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class LoadoutSelector extends Table {
	private final static int TEXT_INTO_PADDING_LEFT = 40;
	private final Button upButton, downButton;
	private final List<Option> values;
	private final Image displayedItem = new Image();
	private final Label nameLabel, descriptionLabel;
	private int selectedIndex = 0;
	
	public LoadoutSelector(ResourceManager resources, List<Descriptable> options) {
		this(resources, options, 0);
	}
	
	public LoadoutSelector(ResourceManager resources, List<Descriptable> options, 
			int selectedOptionIndex) {
		upButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_TRIANGLE_BUTTON_UP));
		downButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_TRIANGLE_BUTTON_DOWN));
		displayedItem.setScaling(Scaling.fit);
		
		values = new ArrayList<Option>(options.size());
		float maxHeight = 0;
		Drawable d;
		for(Descriptable item : options) {
			d = resources.getDrawableTexture(item);
			values.add(new Option(item, d));
			if(d.getMinHeight() > maxHeight) {
				maxHeight = d.getMinHeight();
			}
		}		
		
		add(upButton).padBottom(10);
		row();
		add(displayedItem).height(maxHeight);
		row();
		add(downButton).padTop(10);
		row();
		
		upButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)  {
				if(selectedIndex > 0) {
					switchSelectedOption(--selectedIndex);
				}
			}
		});
		
		downButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)  {
				if(selectedIndex-1 < values.size()) {
					switchSelectedOption(++selectedIndex);
				}
			}
		});
		
		// TODO : Merge with resource handler
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myraidpro.ttf"));
		BitmapFont nameFont = generator.generateFont(38);
		BitmapFont descriptionFont = generator.generateFont(32);
		generator.dispose();
		nameLabel = new Label("", new Label.LabelStyle(nameFont, Color.valueOf("4c7d7d")));
		descriptionLabel = new Label("", new Label.LabelStyle(descriptionFont, 
				Color.valueOf("3f3f3c")));
		
		add(nameLabel).padTop(50).padLeft(TEXT_INTO_PADDING_LEFT).left();
		row();
		add(descriptionLabel).padTop(5).padLeft(TEXT_INTO_PADDING_LEFT + 20).left();
		
		this.selectedIndex = selectedOptionIndex;
		if(values.size() != 0) {
			switchSelectedOption(selectedIndex);
		}
	}

	public Descriptable getSelected() {
		return values.get(selectedIndex).key;
	}
	
	private void switchSelectedOption(int index) {
		if(index <= 0) {
			index = 0;
			downButton.setVisible(true);
			upButton.setVisible(false);
		} else if(index >= values.size()-1) {
			index = values.size()-1;
			upButton.setVisible(true);
			downButton.setVisible(false);
		} else {
			upButton.setVisible(true);
			downButton.setVisible(true);
		}
		
		displayedItem.setDrawable(values.get(selectedIndex).value);
		nameLabel.setText(values.get(selectedIndex).key.getName());
		descriptionLabel.setText(values.get(selectedIndex).key.getDescription());
		fire(new ChangeListener.ChangeEvent());
	}
	
	private class Option {
		public Drawable value;
		public Descriptable key;
		public Option(Descriptable key, Drawable value) {
			this.key = key;
			this.value = value;
		}
	}
}
