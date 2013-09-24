package se.dat255.bulletinferno.view.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.MyGame;
import se.dat255.bulletinferno.model.RenderableGUI;

public class PauseScreenView implements RenderableGUI {
	
	private final Vector2 position, size;
	private final Sprite sprite;
	private final MyGame game;
	
	public PauseScreenView(MyGame game) {
		Texture texture = new Texture(Gdx.files.internal("images/gui/screen_pause.png"));
		sprite = new Sprite(texture);
		size = new Vector2(16.0f, 9.0f);
		sprite.setSize(size.x, size.y);
		position = new Vector2(0.0f, 0.0f);
		sprite.setPosition(position.x - 8.0f, position.y - 4.5f);
		this.game = game;
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}

	@Override
	public void pressed() {
		game.unpauseGame();
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public Vector2 getSize() {
		return size;
	}

}
