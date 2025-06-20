package view;

import model.Difficulty;

import javax.swing.*;

/**
 * Die Klasse {@code MenuScreen} stellt ein Menü zur Auswahl des Schwierigkeitsgrades
 * für das Spiel bereit. Sie zeigt ein Dialogfenster mit verschiedenen Optionen an.
 */
public class MenuScreen {

    /**
     * Zeigt ein modales Dialogfenster zur Auswahl des Schwierigkeitsgrades an.
     * Der Benutzer kann zwischen "Easy", "Normal", "Hard" und "Fun" wählen.
     * Bei Schließen des Dialogs wird das Programm beendet.
     *
     * @return die gewählte {@link Difficulty}-Konstante basierend auf der Benutzerwahl
     */
    public static Difficulty showMenu() {
        // Array mit den zur Auswahl stehenden Schwierigkeitsgraden
        Object[] options = {
                "Easy",
                "Normal",
                "Hard",
                "Fun"
        };

        // Anzeige des Dialogs zur Auswahl des Schwierigkeitsgrads
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose the difficulty: ", // Nachrichtentext
                "Labyrinth - Difficulty", // Fenstertitel
                JOptionPane.DEFAULT_OPTION, // keine Standardauswahl
                JOptionPane.QUESTION_MESSAGE, // Symbol für eine Frage
                new ImageIcon("./src/LabyrinthPerson_v3/resources/player3.png"), // benutzerdefiniertes Icon -> Jerry Lol xD
                options, // Auswahlmöglichkeiten
                options[0]
        );

        // Falls der Benutzer das Fenster schließt, wird das Programm beendet
        if (choice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        // Rückgabe der zum gewählten Index passenden Schwierigkeit
        return switch (choice) {
            case 0 -> Difficulty.EASY;
            case 1 -> Difficulty.MEDIUM;
            case 2 -> Difficulty.HARD;
            default -> Difficulty.SECRET; // für "Fun" oder andere/unbekannte Werte
        };
    }
}
