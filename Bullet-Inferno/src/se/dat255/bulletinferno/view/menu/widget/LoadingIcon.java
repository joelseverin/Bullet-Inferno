package se.dat255.bulletinferno.view.menu.widget;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * A loading icon widget that animates image frames in a sequence
 * 
 * @author Sebastian Blomberg
 *
 */
public class LoadingIcon extends Image {
	public final static float DEFAULT_FRAME_DURATION = 0.1f;
	
	private RepeatAction repeatAction;
	private Action switchImageAction = new Action() {
		@Override
		public boolean act(float arg0) {
			setDrawable(frames[(++currentFrame)%frames.length]);
			return true;
		}
	};
	
	private Drawable[] frames;
	private int currentFrame = 0;
	
	public LoadingIcon(Drawable... frames) {
		this(frames, DEFAULT_FRAME_DURATION);
	}
	
	public LoadingIcon(Drawable[] frames, float frameSwitchSpeed) {
		super(frames[0]);
		this.frames = frames;
		repeatAction = Actions.forever(Actions.sequence(
							new DelayAction(frameSwitchSpeed), switchImageAction)
						);
		addAction(repeatAction);
	}
	
	public void pause() {
		repeatAction.finish();
	}
	
	public void resume() {
		repeatAction.restart();
	}
}
