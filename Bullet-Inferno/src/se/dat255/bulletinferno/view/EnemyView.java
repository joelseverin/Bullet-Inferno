package se.dat255.bulletinferno.view;

import java.util.ArrayList;
import java.util.List;

import se.dat255.bulletinferno.model.Enemy;
import se.dat255.bulletinferno.model.Game;
import se.dat255.bulletinferno.util.ManagedTexture;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyView implements Renderable {

	private final Game game;
	private Texture texture;
	private Sprite sprite;
	private ResourceManager resourceManager;
	private List<ManagedTexture> managedTextures;

	public EnemyView(Game game, ResourceManager resourceManager) {
		this.game = game;
		this.resourceManager = resourceManager;
		
		this.managedTextures = new ArrayList<ManagedTexture>();

		this.sprite = new Sprite();
		//sprite.setOrigin(0, 0);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for(Enemy enemy : game.getEnemies()) {
			ManagedTexture mTexture = resourceManager.getManagedTexture(enemy.getType());
			this.texture = mTexture.getTexture();
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			this.sprite.setTexture(texture);
			sprite.setRegion(texture);
			sprite.setSize(enemy.getDimensions().x, enemy.getDimensions().y);
			sprite.setPosition(enemy.getPosition().x,
					enemy.getPosition().y - sprite.getHeight() / 2);
			sprite.draw(batch);
		}
	}

	@Override
	public void dispose() {
		for(ManagedTexture mTexture : managedTextures) {
			mTexture.dispose(resourceManager);
		}
	}
}
