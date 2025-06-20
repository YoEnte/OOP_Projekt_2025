package model;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * represents a Field type
 */
public enum Field {

    /** Wall field */
    WALL("Wall", new Color(0, 0, 0), '■', "./src/LabyrinthPerson_v3/resources/wall3.png"),
    /** Invisible Wall field */
    INVW("Inv Wall", new Color(254, 254, 254), 'I', "./src/LabyrinthPerson_v3/resources/path.png"),
    /** Start field */
    START("Start", new Color(255, 0, 0), 'S', "./src/LabyrinthPerson_v3/resources/start.png"),
    /** Goal field */
    GOAL("Goal", new Color(0, 255, 0), 'G', "./src/LabyrinthPerson_v3/resources/goal.png"),
    /** Path field */
    PATH("Path", new Color(255, 255, 255), ' ', "./src/LabyrinthPerson_v3/resources/path3.png");

    public final String type;
    public final Color color;
    public final char symbole;
    public final BufferedImage image;

    /**
     * Creates a new Field with the given status values.
     *
     * @param type The type / name of the field.
     * @param color The color for GraphicsView.
     * @param symbole The representation for ConsoleView.
     */
    private Field(String type, Color color, char symbole, String url) {
        this.type = type;
        this.color = color;
        this.symbole = symbole;

        // load image -> if error create blank image
        BufferedImage imageTemp;
        try {
            imageTemp = ImageIO.read(new File(url));
        } catch (IOException e) {
            imageTemp = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        }
        this.image = imageTemp;
    }
}