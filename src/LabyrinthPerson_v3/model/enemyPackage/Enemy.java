package model.enemyPackage;

import model.GameState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Abstrakte Klasse, die den Gegner repäsentiert.
 * Speichert Koordinaten, Url zur Bild Datei, gebufferte Bilder
 */
public abstract class Enemy {

    private int x;
    private int y;
    private String url;
    private BufferedImage image;
    private BufferedImage imageFlipped;

    /**
     * Einfach constructor
     * @param x x Koordinate
     * @param y y Koordinate
     * @param url Url zur Datei (ohne .png)
     */
    protected Enemy(int x, int y, String url){
        this.x = x;
        this.y = y;
        this.url = url;

        BufferedImage imageTemp;
        BufferedImage imageFlippedTemp;
        try {
            imageTemp = ImageIO.read(new File(url + ".png"));
            imageFlippedTemp = ImageIO.read(new File(url + "_flipped.png"));
        } catch (IOException e) {
            System.out.println("image error");
            imageTemp = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            imageFlippedTemp = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        }
        this.image = imageTemp;
        this.imageFlipped = imageFlippedTemp;
    }

    /**
     * Konstruktor, der neben der Url auch direkt geladene Bilder nimmt
     * @param x x Koordinate
     * @param y y Koordinate
     * @param url Url (ohne .png)
     * @param image Normale Sprite
     * @param imageFlipped gespiegelte Sprite
     */
    protected Enemy(int x, int y, String url, BufferedImage image, BufferedImage imageFlipped) {
        this.x = x;
        this.y = y;
        this.url = url;
        this.image = image;
        this.imageFlipped = imageFlipped;
    }

    /**
     * generiert einen Zug, der aus der aktuellen Position gespielt werden soll
     * @param gamestate aktueller Spielstand
     */
    protected abstract void performMove(GameState gamestate);

    public int getPositionX(){
        return x;
    }
    public int getPositionY(){
        return y;
    }
    public String getUrl() {return url;}
    public BufferedImage getImage(){
        return image;
    }
    public BufferedImage getImageFlipped(){
        return imageFlipped;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
