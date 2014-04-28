package se.dat255.bulletinferno.util.userconnectivity;

interface UserCOnnectableListener<T> {
	/**
	 * Returns the result of an asynchronously loading
	 * @param result
	 */
	public void onDoneLoading(T result);
}
