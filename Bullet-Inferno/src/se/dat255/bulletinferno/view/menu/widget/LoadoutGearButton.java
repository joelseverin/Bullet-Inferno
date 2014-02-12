package se.dat255.bulletinferno.view.menu.widget;

import se.dat255.bulletinferno.util.ResourceIdentifier;
import se.dat255.bulletinferno.util.ResourceManager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class LoadoutGearButton extends Button {
	public final static int DEFAULT_IMAGE_PADDING_X = 25, DEFAULT_IMAGE_PADDING_Y = 16;
	
	private Image gearImage = new Image();
	private float imagePaddingX = 0, imagePaddingY = 0;
	
	public LoadoutGearButton(ButtonStyle style) {
		this(style, DEFAULT_IMAGE_PADDING_X, DEFAULT_IMAGE_PADDING_Y);
	}
	
	public LoadoutGearButton(ButtonStyle style, float gearImagePaddingX, float gearImagePaddingY) {
		super(style);
		this.imagePaddingX = gearImagePaddingX;
		this.imagePaddingY = gearImagePaddingY;
		gearImage.setScaling(Scaling.fit);
	}
	
	public LoadoutGearButton(ButtonStyle style, Drawable gear) {
		this(style, gear, DEFAULT_IMAGE_PADDING_X, DEFAULT_IMAGE_PADDING_Y);
	}
	
	public LoadoutGearButton(ButtonStyle style, Drawable gear, float gearImagePaddingX, 
			float gearImagePaddingY) {
		this(style, gearImagePaddingX, gearImagePaddingY);
		setGearImage(gear);
	}
	
	public void setGearImage(ResourceManager resources, ResourceIdentifier gearIdentifier) {
		setGearImage(resources.getDrawableTexture(gearIdentifier));
	}
	
	public void setGearImage(Drawable gear) {
		gearImage.setDrawable(gear);
		gearImage.pack();
		
		// See if the image is within the button's area minus the padding, and if so change it.
		float maxWidth = getWidth() - 2*imagePaddingX;
		float maxHeight = getHeight() - 2*imagePaddingY;
		if(gearImage.getWidth() > maxWidth) {
			gearImage.setWidth(maxWidth);
		} 
		
		if(gearImage.getHeight() > maxHeight) {
			gearImage.setHeight(maxHeight);
		}
	}
	
	@Override
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		updateImagePosition();
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		updateImagePosition();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		gearImage.draw(batch, parentAlpha);
	}
	
	private void updateImagePosition() {
		gearImage.setPosition(getX() + imagePaddingX, getY() + imagePaddingY);
	}
}
