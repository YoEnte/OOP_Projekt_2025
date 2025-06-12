package view;

import model.Board;

/**
 * Classes that want to display the {@link Board} must implement this interface
 * to be notified when the world updates.
 */
public interface View {

	/**
	 * Called whenever the world updates.
	 * 
	 * @param board the {@link Board} object which called this method.
	 */
	void update(Board board);

}
