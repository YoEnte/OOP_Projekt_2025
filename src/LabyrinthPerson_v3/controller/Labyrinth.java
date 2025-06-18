package controller;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.*;
import view.ConsoleView;
import view.GraphicView;

import model.Difficulty;
import view.MenuScreen;

/**
 * This is our main program. It is responsible for creating all of the objects
 * that are part of the MVC pattern and connecting them with each other.
 */
public class Labyrinth {


    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {



                Difficulty difficulty = MenuScreen.showMenu();
                System.out.println(difficulty);

                int width = 18 + 2;
                int height = 18 + 2;
                if (difficulty == Difficulty.SECRET) {
                    width = 29;
                    height = 29;
                }

                // Create a new game world.
                Board board = new Board(width, height, difficulty);

                // Size of a field in the graphical view.
                Dimension fieldDimensions = new Dimension(35, 35);

                // Create and register graphical view.
                ArrayList<Coordinates> starts = board.getIndexForFieldType(Field.START);
                Player player = new Player(starts.get(0).getXCoordinate(),starts.get(0).getYCoordinate());
                GameState gameState = new GameState(0, board, player, difficulty);

                GraphicView gview = new GraphicView(
                        width * fieldDimensions.width,
                        height * fieldDimensions.height,
                        fieldDimensions);
                gameState.registerView(gview);
                gview.setVisible(true);






                // Create and register console view.
                ConsoleView cview = new ConsoleView();
                gameState.registerView(cview);

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(new Color(240, 240, 240));

                JButton backButton = new JButton("← Zurück");
                JButton forwardButton = new JButton("Vor →");
                JButton restartButton = new JButton("↺ Menü");

                buttonPanel.add(backButton);
                buttonPanel.add(forwardButton);
                buttonPanel.add(restartButton);

                buttonPanel.setPreferredSize(new Dimension(width * fieldDimensions.width, 50));

                // Create controller and initialize JFrame.
                Controller controller = new Controller(gameState, new JButton[]{backButton, forwardButton, restartButton});
                controller.setTitle("Labyrinth - Game");
                controller.setResizable(false);
                controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                controller.getContentPane().add(gview, BorderLayout.CENTER);

                // Button-Panel unten hinzufügen
                controller.getContentPane().add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

                // pack() is needed before JFrame size can be calculated.
                controller.pack();

                // Calculate size of window by size of insets (titlebar + border) and size of
                // graphical view.
                Insets insets = controller.getInsets();

                int windowX = width * fieldDimensions.width + insets.left + insets.right;
                int windowY = height * fieldDimensions.height + insets.bottom + insets.top + 50;
                Dimension size = new Dimension(windowX, windowY);
                controller.setSize(size);
                controller.setMinimumSize(size);
                controller.setVisible(true);

                controller.requestFocusInWindow();


            }
        });
    }
}
