package se.dat255.bulletinferno.view.menu;

import se.dat255.bulletinferno.util.Disposable;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public abstract class SimpleToggleSubMenuView implements Disposable {
	
	private final Group toggleActor;
	private final float toggleSlideDuration;
	private EventListener toggleListener = null;
	private boolean isDown = false;
	
	public SimpleToggleSubMenuView(Group toggleActor, int toggleAcotorWidth, int toggleActorHeight, 
			int toggleActorX, int toggleActorY, float toggleSlideDuration) {
		this.toggleActor = toggleActor;
		this.toggleSlideDuration = toggleSlideDuration;
		
		// Create a group (<div>) for animation purposes
		toggleActor.setSize(toggleAcotorWidth, toggleActorHeight);
		toggleActor.setPosition(toggleActorX, toggleActorY);
	}
	
	/**
	 * Toggles the view so that it's animated up/down and is/isn't visible in the views given 
	 * VIRTUAL_WIDTH and VIRTUAL_HEIGHT
	 */
	public void slideToggle() {
		// Remove previous action
		if(oldSlideAction != null) {
			toggleActor.removeAction(oldSlideAction);
		}
		
		if(isDown) {
			toggleActor.addAction(getAnimateUpAction());
		} else {
			isDown = true;
			toggleActor.addAction(getAnimateDownAction());
		}
	}
	
	/**
	 * Returns whether the view has been toggled so that it's visible
	 * in it's VIRTUAL_WIDTH and VIRTUAL_HEIGHT. See {@link SettingsView#slideToggle()}
	 * @return is in place
	 */
	public boolean isToggledVisible() {
		return isDown;
	}
	
	/**
	 * Set listener that receives a callback on a complete toggle. 
	 * See {@link SettingsView#slideToggle()} 
	 * @param listener
	 */
	public void setSlideToggleListener(EventListener listener) {
		this.toggleListener = listener;
	}
	
	@Override
	public void dispose() {
		toggleListener = null;
		if(inActionComplete) {
			// If we're in action complete in action complete, let it worry about clearing the rest.
			toggleActor.clearChildren();
		} else {
			toggleActor.clear();
		}
	}
	
	public Group getToggleActor() {
		return toggleActor;
	}
	
	private Action oldSlideAction = null;
	// A helper in order to always get the same speed for the animation, no matter where
	// the group currently is positioned
	private Action getAnimateUpAction() {
		oldSlideAction = Actions.sequence(
							Actions.moveTo(toggleActor.getX(), toggleActor.getHeight(), 
									toggleSlideDuration * 
									(1 - toggleActor.getY()/toggleActor.getHeight())),
									onActionComplete);
		return oldSlideAction;
	}
	
	private Action getAnimateDownAction() {
		oldSlideAction = Actions.sequence(
							Actions.moveTo(toggleActor.getX(), 0, 
									toggleSlideDuration * 
									toggleActor.getY()/toggleActor.getHeight()),
									onActionComplete);
		return oldSlideAction;
	}
	
	private boolean inActionComplete = false;
	private Action onActionComplete = new Action() {
		@Override
		public boolean act(float delta) {
			inActionComplete = true;
			if(toggleActor.getY() != 0) {
				isDown = false;
			}
			
			if(toggleListener != null) {
				// Due to a design flaw in libgdx, we need to add and remove listener since it
				// otherwise would be notified by all of its children's event too.
				toggleActor.addListener(toggleListener);
				toggleActor.fire(new ChangeListener.ChangeEvent());
				if(toggleListener == null) {
					// If toggleListener is null now a dispose call has been placed to the view
					// but since that happened during the firing of an event, the view can't remove
					// all listeners, so we need to here.
					toggleActor.clearListeners();
				} else {
					toggleActor.removeListener(toggleListener);
				}
			}
			inActionComplete = false;
			return true;
		}
		
	};
	
}
