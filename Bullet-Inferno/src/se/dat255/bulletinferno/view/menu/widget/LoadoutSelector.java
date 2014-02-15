package se.dat255.bulletinferno.view.menu.widget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import se.dat255.bulletinferno.util.Descriptable;
import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.util.TextureDefinitionImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;

/**
 * A selector which contains a given set of options (values), represented with images, names and 
 * descriptions, for a user to select from. 
 * The options is required to be {@link Descriptable}s for their images will be identified via 
 * ({@link ResourceIdentifier#getIdentifier()).
 * 
 * @author Sebastian Blomberg
 *
 * @param <T>
 */
public class LoadoutSelector<T extends Descriptable> extends Table {
	private final static int TEXT_INTO_PADDING_LEFT = 20;
	private final Button upButton, downButton;
	private final List<Option> values;
	private final Image displayedItem = new Image();
	private final Label nameLabel, descriptionLabel;
	private int selectedIndex = 0;
	
	private final List<EventListener> listeners = new LinkedList<EventListener>();
	
	/**
	 * Constructs a new LoadoutSelecor with the first option selected.
	 * @param resources The ResourseManager
	 * @param options The list containing the options for the selector
	 */
	public LoadoutSelector(ResourceManager resources, List<T> options) {
		this(resources, options, 0);
	}
	
	/**
	 * Constructs a new LoadoutSelector with the option at the specified index in the options list
	 * to be selected. An option's identifier are expected to be found in the specified 
	 * ResourceManager
	 * @pre Specified ResourceManager must contain textures with the options identifier. 
	 * @param resources The ResourseManager
	 * @param options The list containing the options for the selector
	 * @param selectedOptionIndex The index for the option to be selected as default.
	 */
	public LoadoutSelector(ResourceManager resources, List<T> options, 
			int selectedOptionIndex) {
		upButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_TRIANGLE_BUTTON_UP));
		downButton = new Button(
				resources.getDrawableTexture(TextureDefinitionImpl.LOADOUTMENU_TRIANGLE_BUTTON_DOWN));
		displayedItem.setScaling(Scaling.fit);
		
		values = new ArrayList<Option>(options.size());
		float maxHeight = 0;
		Drawable d;
		for(T item : options) {
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
				switchSelectedOption(--selectedIndex);
			}
		});
		
		downButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)  {
				switchSelectedOption(++selectedIndex);
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

	@Override
	public boolean fire(Event e) {
		// Add all local listeners to superclass
		// so we can let it handle the firing of the event
		// this because we don't want the buttons in this class to notify this class listeners
		// when they get clicked, we would then get an anomaly if the user tries to get selected
		// item in a listener callback, because we haven't changed the selected item before 
		// parent's listeners (i.e. this class' listeners) get's called.
		// (Utterly stupid, but libdx implementation)
		for(EventListener l : listeners) {
			super.addListener(l);
		}
		boolean result = super.fire(e);
		// And remove them again
		for(EventListener l : listeners) {
			super.removeListener(l);
		}
		return result;
	}
	
	/**
	 * Returns the currently selected option
	 * @return selected option
	 */
	public T getSelected() {
		return values.get(selectedIndex).key;
	}
	
	/**
	 * Returns the index of the selected option. (Staring at 0)
	 * @return selected option's index
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	/**
	 * Sets the specified option as selected, if it exists in the selector, based on evaluation
	 * with {@link Object#equals(Object)}
	 * @param option
	 * @return true or false based on the success (if the element exist)
	 */
	public boolean setSelected(T option) {
		int i = 0;
		for(Option current : values) {
			if(current.key.equals(option)) {
				return setSelected(i);
			}
			i++;
		}
		return false;
	}
	
	/**
	 * Sets the option at the specified index as selected. 
	 * @param index (0 =< index < size)
	 * @return true or false based on the success (the index condition above)
	 */
	public boolean setSelected(int index) {
		if(index >= 0 && index < values.size()) {
			// Optimization, also no change event is fired
			if(selectedIndex != index) {
				selectedIndex = index;
				switchSelectedOption(selectedIndex);
			}
			return true;
			
		}
		return false;
	}
	
	/**
	 * Returns the amount of options this selector holds. 
	 * @return size of selector (amount of options)
	 */
	public int getSize() {
		return values.size();
	}
	
	/**
	 * Adds a listener to be notified when any changes is made in the selector.
	 */
	@Override
	public boolean addListener(EventListener listener) {
		listeners.add(listener);
		return true;
	}
	
	@Override
	public boolean removeListener(EventListener listener) {
		listeners.remove(listener);
		return true;
	}
	
	@Override
	public void clear() {
		super.clear();
		upButton.clear();
		downButton.clear();
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
		ChangeEvent e = Pools.obtain(ChangeEvent.class);
		e.setTarget(this);
		fire(e);
		Pools.free(e);
	}
	
	private class Option {
		public Drawable value;
		public T key;
		public Option(T key, Drawable value) {
			this.key = key;
			this.value = value;
		}
	}
}
