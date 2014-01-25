package se.dat255.bulletinferno.controller.menu;

import se.dat255.bulletinferno.util.Disposable;

public interface SubMenuController extends Disposable {
	/**
	 * Requests the controller to dispose of itself, leaving time for the controller and it's 
	 * components to perform finishing tasks before dispose (Such as waiting for view animations 
	 * to finish ). The specified listener get's a callback when the dispose is done.
	 * @param listener the listener to receive a callback after dispose
	 */
	public void disposeRequest(SubMenuControllHandler listener);
	
	/**
	 * Requests the controller to dispose of itself, leaving time for the controller and it's 
	 * components to perform finishing tasks before dispose (Such as waiting for view animations 
	 * to finish ).
	 */
	public void disposeRequest();
	
	/**
	 * Requests the controller to immediately dispose of itself. All tasks will be aborted.
	 * <br/><br/> For leaving the controller time to run finishing
	 */
	public void dispose();
}
