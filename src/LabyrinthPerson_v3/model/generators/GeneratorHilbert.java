package model.generators;

import model.Field;

/**
 * Diese Klasse sollte eigentlich zufällig ein Labyrinth generieren, haben wir bloß nicht mehr geschafft :/
 * Nun gibt sie einfach das Standard Maze zurück
 */
public class GeneratorHilbert {

    public static Field[][] generate() {

        return MainMaze.generate();
    }
}
