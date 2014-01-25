package se.dat255.bulletinferno.controller.menu;

public interface SubMenuControllHandler {
	/**
	 * Gets called when a dispose request (See 
	 * {@link SubMenuController#disposeRequest(SubMenuControllHandler)} in a 
	 * {@link SubMenuController} has finished and the controller is disposed.
	 * @param controller
	 */
	public void onDisposeFinished(SubMenuController controller);
}
