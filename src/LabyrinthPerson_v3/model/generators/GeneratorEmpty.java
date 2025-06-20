package model.generators;

import model.Coordinates;
import model.Field;
import model.Board;

/**
 * Die Klasse {@code GeneratorEmpty} erzeugt ein leeres Spielfeld (Board),
 * bei dem der Rand aus Wänden besteht und das Innere begehbare Pfade enthält.
 * Start- und Zielpunkt werden ebenfalls festgelegt.
 */
public class GeneratorEmpty {

    /**
     * Generiert ein zweidimensionales Spielfeld-Array mit den gegebenen Abmessungen.
     * Der äußere Rand wird mit Wänden gefüllt, das Innere mit Pfaden.
     * Der Startpunkt wird auf Position (1,1) gesetzt, das Ziel auf Position (18,18).
     *
     * @param width  die Breite des Spielfeldes
     * @param height die Höhe des Spielfeldes
     * @return ein 2D-Array vom Typ {@code Field}, das das generierte Spielfeld darstellt
     */
    public static Field[][] generate(int width, int height) {

        // Initialisiere das Spielfeld-Array mit den gegebenen Abmessungen
        Field[][] board = new Field[height][width];

        // Schleife über alle Positionen im Array
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Setze Wände an den äußeren Randpositionen
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    board[y][x] = Field.WALL;
                } else {
                    // Alle anderen Felder sind begehbare Pfade
                    board[y][x] = Field.PATH;
                }
            }
        }

        // Setze das Zielfeld an eine feste Position
        board[18][18] = Field.GOAL;

        // Setze das Startfeld an eine feste Position
        board[1][1] = Field.START;

        // Gib das fertige Spielfeld zurück
        return board;
    }
}
