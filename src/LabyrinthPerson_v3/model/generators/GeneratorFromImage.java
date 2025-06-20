package model.generators;

import model.Board;
import model.Coordinates;
import model.Field;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Diese Klasse ermöglicht die Generierung eines Spielfeldes (Field[][]) aus einem Bild.
 * Dabei werden die Farben der einzelnen Pixel aus dem Bild in Spiel-Felder (WALL, PATH, START, GOAL) übersetzt.
 */
public class GeneratorFromImage {

    // Singleton-Instanz (nicht verwendet, aber vorbereitet)
    private static GeneratorFromImage generatorFromImage;

    /**
     * Liest ein Bild ein und konvertiert dessen Pixel in ein zweidimensionales Spielfeld-Array.
     *
     * @param width      Die Breite des Spielfelds.
     * @param height     Die Höhe des Spielfelds.
     * @param sourcePath Der Pfad zur Bilddatei, die als Vorlage dient.
     * @return Ein 2D-Array vom Typ Field, das das Spielfeld repräsentiert.
     */
    public static Field[][] generate(int width, int height, String sourcePath) {

        Field[][] board = new Field[height][width]; // Initialisiere leeres Spielfeld
        BufferedImage image = null;

        try {
            File input_file = new File(sourcePath); // Lade Bilddatei vom angegebenen Pfad

            // Erstelle neues BufferedImage-Objekt (wird nicht verwendet – überflüssige Zeile)
            new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            image = ImageIO.read(input_file); // Lese Bild in BufferedImage ein

            // Gehe jedes Pixel durch und ordne ihm ein Spielfeld zu
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Weise das Feld dem Board anhand der Pixel-Farbe zu
                    board[y][x] = getField(image.getRGB(x, y));
                }
            }
        } catch (IOException e) {
            // pass
        }

        return board;
    }

    /**
     * Bestimmt das Feld (Field) anhand eines Farbwertes (RGB) aus dem Bild.
     *
     * @param p Der Farbwert eines Pixels (als int).
     * @return Das zugehörige Field (z.B. WALL, PATH, START, GOAL).
     */
    private static Field getField(int p) {
        // Extrahiere Rot-, Grün- und Blauanteil des Pixels
        int r = (p & 0x00ff0000) >> 16;
        int g = (p & 0x0000ff00) >> 8;
        int b = (p & 0x000000ff);

        // Erstelle ein Color-Objekt aus den RGB-Werten
        Color clr = new Color(r, g ,b);

        Field thisField;

        // Vergleiche die Farbe mit den vordefinierten Feldfarben
        if (clr.equals(Field.GOAL.color)) {
            thisField = Field.GOAL;
        } else if (clr.equals(Field.WALL.color)) {
            thisField = Field.WALL;
        } else if (clr.equals(Field.INVW.color)) {
            thisField = Field.INVW;
        } else if (clr.equals(Field.PATH.color)) {
            thisField = Field.PATH;
        } else if (clr.equals(Field.START.color)) {
            thisField = Field.START;
        } else {
            // Falls die Farbe nicht erkannt wird, standardmäßig PATH setzen
            thisField = Field.PATH;
        }

        return thisField;
    }
}
