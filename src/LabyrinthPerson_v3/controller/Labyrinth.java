package controller;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.*;
import view.*;

/**
 * Die Hauptklasse des Labyrinth-Spiels.
 * Sie ist für die Initialisierung der MVC-Komponenten (Model, View, Controller)
 * und für den Start des Spiels verantwortlich.
 */
public class Labyrinth {

    /**
     * Die Main-Methode startet das Labyrinth-Spiel.
     * Sie initialisiert das Spiel basierend auf dem vom Benutzer gewählten Schwierigkeitsgrad.
     * Die grafische und textbasierte Darstellung (View) sowie der Controller werden eingerichtet.
     *
     * @param args Nicht verwendet.
     */
    public static void main(String[] args) {

        // Startet die GUI-Initialisierung im Event-Dispatch-Thread (Swing-konform).
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                // Zeigt das Menü und liest die gewählte Schwierigkeit vom Benutzer.
                Difficulty difficulty = MenuScreen.showMenu();

                // Definiert Standardgröße des Spielfelds (inkl. Rand).
                int width = 20;
                int height = 19;

                // Definiert die Pixelgröße jedes Spielfelds für die grafische Darstellung.
                Dimension fieldDimensions = new Dimension(32, 32);

                // Anpassung der Größe (+ Pixelgröße) für den "SECRET"-Schwierigkeitsgrad.
                if (difficulty == Difficulty.SECRET) {
                    width = 33;
                    height = 33;
                    fieldDimensions.setSize(25, 25);
                }

                // Erstellt das Spielfeld-Model mit angegebener Größe und Schwierigkeitsgrad.
                Board board = new Board(width, height, difficulty);

                // Ermittelt Startposition(en) des Spielers und erstelle Spieler.
                ArrayList<Coordinates> starts = board.getIndexForFieldType(Field.START);
                Player player = new Player(starts.getFirst().getXCoordinate(), starts.getFirst().getYCoordinate(), "./src/LabyrinthPerson_v3/resources/player");

                // Erstellt den Spielzustand mit initialem Punktestand 0.
                GameState gameState = new GameState(0, board, player, difficulty);

                // Erstellt die grafische Ansicht und registriert sie beim Spielzustand.
                GraphicView gview = new GraphicView(
                        width * fieldDimensions.width,
                        height * fieldDimensions.height,
                        fieldDimensions,
                        gameState);
                gameState.registerView(gview);
                gview.setVisible(true);

                // Erstellt und registriert die Konsolenansicht beim Spielzustand.
                ConsoleView cview = new ConsoleView();
                gameState.registerView(cview);

                // Erstellt ein Panel für die Steuerungsbuttons am unteren Rand.
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(new Color(240, 240, 240));

                // Erstellt Steuerungsbuttons.
                JButton backButton = new JButton("← Back");
                JButton forwardButton = new JButton("Forward →");
                JButton restartButton = new JButton("↺ Menu");

                // Fügt Buttons dem Panel hinzu.
                buttonPanel.add(backButton);
                buttonPanel.add(forwardButton);
                buttonPanel.add(restartButton);

                // Setzt die bevorzugte Höhe des Button-Panels.
                buttonPanel.setPreferredSize(new Dimension(width * fieldDimensions.width, 50));

                // Erstellt den Controller und übergibt ihm den Spielzustand und die Buttons.
                Controller controller = new Controller(gameState, new JButton[]{backButton, forwardButton, restartButton});
                controller.setTitle("Labyrinth - Game");
                controller.setResizable(false);
                controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Fügt die grafische Ansicht zum Hauptfenster hinzu.
                controller.getContentPane().add(gview, BorderLayout.CENTER);

                // Fügt das Button-Panel unter der grafischen Ansicht hinzu.
                controller.getContentPane().add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

                // Berechnet Fenstergröße, bevor das JFrame korrekt angezeigt werden kann.
                controller.pack();

                // Berechnet endgültige Fenstergröße basierend auf Insets und Feldgröße.
                Insets insets = controller.getInsets();
                int windowX = width * fieldDimensions.width + insets.left + insets.right;
                int windowY = height * fieldDimensions.height + insets.bottom + insets.top + 50;

                Dimension size = new Dimension(windowX, windowY);
                controller.setSize(size);
                controller.setMinimumSize(size);
                controller.setVisible(true);

                // Fordert den Fokus auf das Fenster an, um Tastatureingaben zu ermöglichen.
                controller.requestFocusInWindow();
            }
        });
    }
}
