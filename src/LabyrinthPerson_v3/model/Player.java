package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Repräsentiert den Spieler im Spiel mit einer x- und y-Position auf dem Spielfeld.
 */
public class Player {

    // X-Koordinate des Spielers
    private int playerX;

    // Y-Koordinate des Spielers
    private int playerY;

    // Geladene Bilddateien
    private final BufferedImage image;
    private final BufferedImage imageFlipped;

    /**
     * Konstruktor zum Erstellen eines Spielers mit spezifischer Position.
     *
     * @param x Die X-Position des Spielers.
     * @param y Die Y-Position des Spielers.
     * @param url Die "Basis" Adresse zur Bilddatei (ohne .png)
     */
    public Player(int x, int y, String url){
        this.playerX = x;
        this.playerY = y;

        // Bilddateien laden und speichern -> bei fehler blanke bilder speichern
        BufferedImage imageTemp;
        BufferedImage imageFlippedTemp;
        try {
            imageTemp = ImageIO.read(new File(url + ".png"));
            imageFlippedTemp = ImageIO.read(new File(url + "_flipped.png"));
        } catch (IOException e) {
            imageTemp = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            imageFlippedTemp = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        }
        this.image = imageTemp;
        this.imageFlipped = imageFlippedTemp;
    }

    /**
     * Kopierkonstruktor. Erstellt eine neue Spielerinstanz basierend auf einem bestehenden Spielerobjekt.
     *
     * @param other Das Spielerobjekt, das kopiert werden soll.
     */
    public Player(Player other) {
        this.playerX = other.playerX;
        this.playerY = other.playerY;
        this.image = other.image;
        this.imageFlipped = other.imageFlipped;
    }

    /**
     * Setzt die X-Position des Spielers neu.
     *
     * @param playerX Neue X-Position.
     */
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    /**
     * Setzt die Y-Position des Spielers neu.
     *
     * @param playerY Neue Y-Position.
     */
    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    /**
     * Gibt die aktuelle X-Position des Spielers zurück.
     *
     * @return X-Position des Spielers.
     */
    public int getPositionX() {
        return playerX;
    }

    /**
     * Gibt die aktuelle Y-Position des Spielers zurück.
     *
     * @return Y-Position des Spielers.
     */
    public int getPositionY() {
        return playerY;
    }

    /**
     * Gibt das gespeicherte Bildobjekt zurück
     *
     * @return Bildobjekt "normal"
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Gibt das gespeicherte gespiegelte Bildobjekt zurück
     *
     * @return Bildobjekt "gespiegelt"
     */
    public BufferedImage getImageFlipped() {
        return imageFlipped;
    }
}
