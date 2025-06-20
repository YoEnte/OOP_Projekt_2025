package view;

import model.Difficulty;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuScreen {
    public static Difficulty showMenu() {
        Object[] options = {
                "Easy",
                "Normal",
                "Hard",
                "Fun"
        };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose the difficulty: ",
                "Labyrinth - Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("./src/LabyrinthPerson_v3/resources/player3.png"),
                options,
                options[0]
        );

        if (choice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        return switch (choice) {
            case 0 -> Difficulty.EASY;
            case 1 -> Difficulty.MEDIUM;
            case 2 -> Difficulty.HARD;
            default -> Difficulty.SECRET;
        };
    }
}