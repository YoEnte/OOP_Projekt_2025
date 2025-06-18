package view;

import model.Board;
import model.Field;
import model.GameState;
import model.enemyPackage.Enemy;

/**
 * A view that prints the current state of the world to the console upon every
 * update.
 */
public class ConsoleView implements View {

	@Override
	public void update(GameState gameState) {

		System.out.flush();

		// The player's position
		int playerX = gameState.getPlayer().getPositionX();
		int playerY = gameState.getPlayer().getPositionY();
		Field[][] board = gameState.getBoard().getFieldList();

		for (int row = 0; row < gameState.getBoard().getHeight(); row++) {
			for (int col = 0; col < gameState.getBoard().getWidth(); col++) {
				boolean enemyOnField = false;

				for(Enemy e : gameState.getListOfEnemies()){
					if(row == e.getPositionY() && col == e.getPositionX()){
						System.out.print(" E");
						enemyOnField = true;
					}
				}
				// If the player is here, print #, otherwise print .
				if ((row == playerY && col == playerX) && !enemyOnField) {
					System.out.print(" #");
				} else if(!enemyOnField) {
					System.out.print(" " + board[row][col].symbole);
				}
				enemyOnField = false;
			}

			// A newline after every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
	}
}
