package se.dat255.bulletinferno.controller.menu;

import com.badlogic.gdx.scenes.scene2d.Stage;

import se.dat255.bulletinferno.util.ResourceManager;
import se.dat255.bulletinferno.view.menu.SettingsView;

public class SettingsController implements SubMenuController {
	private SettingsView settingsView;
	
	public SettingsController(Stage stage, ResourceManager resources) {
		settingsView = new SettingsView(stage, resources);
	}
	
	@Override
	public void dispose() {
		settingsView.dispose();
	}

}
