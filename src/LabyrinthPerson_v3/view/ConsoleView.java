package view;

import model.Board;

/**
 * A view that prints the current state of the world to the console upon every
 * update.
 */
public class ConsoleView implements View {

	@Override
	public void update(Board board) {
		// The player's position
		int playerX = board.getPlayerX();
		int playerY = board.getPlayerY();

		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				// If the player is here, print #, otherwise print .
				if (row == playerY && col == playerX) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}

			// A newline after every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
	}

}
