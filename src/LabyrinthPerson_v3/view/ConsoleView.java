package view;

import model.Board;
import model.Field;
import model.GameState;
import model.enemyPackage.Enemy;

/**
 * Eine View-Implementierung, die den aktuellen Zustand der Spielwelt bei jedem
 * Update auf der Konsole ausgibt.
 */
public class ConsoleView implements View {

	/**
	 * Aktualisiert die Konsolenanzeige des Spiels basierend auf dem übergebenen GameState.
	 * Zeigt dabei das Spielfeld, den Spieler (#), Feinde (E), Ziel (G) und den Start (S).
	 *
	 * @param gameState Der aktuelle Spielzustand, der angezeigt werden soll.
	 */
	@Override
	public void update(GameState gameState) {

		// Konsole leeren
		System.out.flush();

		// Position des Spielers ermitteln
		int playerX = gameState.getPlayer().getPositionX();
		int playerY = gameState.getPlayer().getPositionY();

		// Spielfeld aus dem GameState abrufen
		Field[][] board = gameState.getBoard().getFieldList();

		// Durch alle Zeilen des Spielfeldes iterieren
		for (int row = 0; row < gameState.getBoard().getHeight(); row++) {
			// Durch alle Spalten des Spielfeldes iterieren
			for (int col = 0; col < gameState.getBoard().getWidth(); col++) {
				boolean enemyOnField = false;

				// Prüfen, ob sich ein Gegner auf diesem Feld befindet
				for (Enemy e : gameState.getListOfEnemies()) {
					if (row == e.getPositionY() && col == e.getPositionX()) {
						System.out.print(" E"); // Gegner anzeigen
						enemyOnField = true;
					}
				}

				// Wenn der Spieler auf dem aktuellen Feld steht (und kein Gegner dort ist)
				if ((row == playerY && col == playerX) && !enemyOnField) {
					System.out.print(" #"); // Spieler anzeigen
				}
				// Wenn kein Gegner auf dem Feld ist, zeige das Symbol des Feldes
				else if (!enemyOnField) {
					System.out.print(" " + board[row][col].symbole);
				}

				// Rücksetzen der Enemy-Flagge für das nächste Feld
				enemyOnField = false;
			}

			// Neue Zeile nach jeder Spielfeldreihe
			System.out.println();
		}

		// Zusätzliche Leerzeile zur Trennung der Updates
		System.out.println();
	}
}
