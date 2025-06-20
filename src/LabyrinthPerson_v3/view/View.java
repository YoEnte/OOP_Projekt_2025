package view;

import model.Board;
import model.GameState;

/**
 * Classes that want to display the {@link Board} must implement this interface
 * to be notified when the world updates.
 */
public interface View {

	/**
	 * Called whenever the world updates.
	 * 
	 * @param gameState the {@link Board} object which called this method.
	 */
	void update(GameState gameState);

}
