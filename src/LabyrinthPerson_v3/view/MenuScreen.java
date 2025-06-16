package view;

import model.Difficulty;
import javax.swing.*;

public class MenuScreen {
    public static Difficulty showMenu() {
        Object[] options = {
                "Einfach (wenige Gegner)",
                "Mittel (mehr Gegner)",
                "Schwer (viele Gegner)"
        };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Wähle die Schwierigkeitsstufe:",
                "Labyrinth - Schwierigkeit",
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
            default -> Difficulty.EASY;
        };
    }
}