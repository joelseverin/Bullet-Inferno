package se.dat255.bulletinferno.view.menu.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;

import se.dat255.bulletinferno.util.Descriptable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class LoadoutSelector extends Table {
	private final Button upButton, downButton;
	private final List<Option> values;
	private final Image displayedItem = new Image();
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
		
		this.selectedIndex = selectedOptionIndex;
		if(values.size() != 0) {
			switchSelectedOption(selectedIndex);
		}
		
		add(upButton);
		row();
		add(displayedItem).height(maxHeight);
		row();
		add(downButton);

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
