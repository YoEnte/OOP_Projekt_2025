package model;

import java.awt.Color;

/**
 * represents a Field type
 */
public enum Field {

    /** Wall field */
    WALL("Wall", new Color(0, 0, 0), '■'),
    /** Start field */
    START("Start", new Color(255, 0, 0), 'S'),
    /** Goal field */
    GOAL("Goal", new Color(0, 255, 0), 'G'),
    /** Free field */
    FREE("Free", new Color(255, 255, 255), ' ');

    public final String type;
    public final Color color;
    public final char symbole;

    /**
     * Creates a new Field with the given status values.
     *
     * @param type The type / name of the field.
     * @param color The color for GraphicsView.
     * @param symbole The representation for ConsoleView.
     */
    private Field(String type, Color color, char symbole) {
        this.type = type;
        this.color = color;
        this.symbole = symbole;
    }
}