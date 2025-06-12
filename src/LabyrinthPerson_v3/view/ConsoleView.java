package view;

import model.Board;
import model.Field;
import model.GameState;

/**
 * A view that prints the current state of the world to the console upon every
 * update.
 */
public class ConsoleView implements View {

	@Override
	public void update(GameState gameState, Board board) {
		// The player's position
		int playerX = gameState.getPlayer().getPlayerX();
		int playerY = gameState.getPlayer().getPlayerY();
		Field[][] _board = board.getBoard();

		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				// If the player is here, print #, otherwise print .
				if (row == playerY && col == playerX) {
					System.out.print(" #");
				} else {
					System.out.print(" " + _board[row][col].symbole);
				}
			}

			// A newline after every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
	}

}
