package se.dat255.bulletinferno.view.menu;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinition;
import se.dat255.bulletinferno.model.loadout.PassiveAbilityDefinitionImpl;
import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PassiveButtonsView {

	private final ResourceManager resourceManager;
	private final List<PassiveButton> passiveButtons = new ArrayList<PassiveButton>();
	private PassiveButton selectionButton;
	private final Skin skin;
	private final Table table;
	private final Label label;

	public PassiveButtonsView(Stage stage, Skin skin, Table table, Label label,
			ResourceManager resourceManager) {
		this.skin = skin;
		this.table = table;
		this.label = label;
		this.resourceManager = resourceManager;
	}

	public void populateTable() {
		PassiveAbilityDefinitionImpl[] arr = PassiveAbilityDefinitionImpl.values();
		if (passiveButtons.size() == 0) {
			table.clear();
			for (int i = 0; i < arr.length; i++) {
				// TODO: the line below needs changing to take into account all weapons
				PassiveAbilityDefinition ability = arr[i];
				PassiveButton passiveButton = new PassiveButton(getTableButton(ability), ability,
						resourceManager);
				passiveButtons.add(passiveButton);
				passiveButton.getButton().addListener(new TableElementClickedListener());
			}

		}
		if (selectionButton.getData() == null) {
			setSelectionToSelected(passiveButtons.get(0));
		}
		// Set up the table to add these buttons to
		showTable();
	}

	private void showTable() {
		table.clear();
		for (PassiveButton button : passiveButtons) {
			this.table.add(button.getButton()).padBottom(20).height(95).width(200).row();
		}
		label.setText("Passive abilities");
	}

	private Button getTableButton(ResourceIdentifier identifier) {
		TextureRegion texture = resourceManager.getTexture(identifier);
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(texture);

		return new Button(buttonStyle);
	}

	private void deselectOtherButtons(PassiveButton selected) {
		for (PassiveButton pButton : passiveButtons) {
			if (pButton != selected && pButton.isSelected()) {
				pButton.toggleSelected(skin);
			}
		}
	}

	private void setSelectionToSelected(PassiveButton pButton) {
		ButtonStyle style = pButton.getButton().getStyle();
		selectionButton.setData(pButton.getData());

		style.up = new TextureRegionDrawable(resourceManager.getTexture(
				selectionButton.getData()));
		style.over = style.up;

		selectionButton.getButton().setStyle(style);
	}

	public PassiveButton getSelectionButton() {
		return this.selectionButton;
	}

	public void setSelectionButton(PassiveButton selectionSpecialButton) {
		this.selectionButton = selectionSpecialButton;
	}

	private class TableElementClickedListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			PassiveButton selected = null;
			for (PassiveButton pButton : passiveButtons) {
				Button button = pButton.getButton();
				if (button == ((Button) actor)) {
					selected = pButton;
					if (!pButton.isSelected()) {
						pButton.toggleSelected(skin);
						setSelectionToSelected(pButton);
					} else {
						pButton.toggleSelected(skin);
					}
				}
				// TODO: add break here since we don't want to keep looping after we found the
				// matching weapon
			}

			deselectOtherButtons(selected);
		}
	}

	public class SelectionClickedListener extends ChangeListener {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			populateTable();
		}
	}
}
