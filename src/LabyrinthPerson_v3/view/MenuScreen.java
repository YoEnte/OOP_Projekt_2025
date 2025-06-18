package view;

import model.Difficulty;
import javax.swing.*;

public class MenuScreen {
    public static Difficulty showMenu() {
        Object[] options = {
                "Easy",
                "Mittel (mehr Gegner)",
                "Schwer (viele Gegner)",
                "EasterEgg"
        };



        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose the difficulty: ",
                "Labyrinth - Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
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