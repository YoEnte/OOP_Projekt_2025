package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.Controller;
import model.GameState;
import model.enemyPackage.Enemy;

/**
 * Diese Klasse stellt die grafische Ansicht des Spiels dar.
 * Sie zeichnet das Spielfeld, den Spieler und die Gegner.
 */
public class GraphicView extends JPanel implements View {
	private JFrame frame;

	/** Die Breite des Fensters. */
	private final int SCREEN_WIDTH;
	/** Die Höhe des Fensters. */
	private final int SCREEN_HEIGHT;

	/** Die Breite des Spielfelds in Zellen. */
	private final int BOARD_WIDTH;
	/** Die Höhe des Spielfelds in Zellen. */
	private final int BOARD_HEIGHT;

	/** Die Größe jedes Feldes (z.B. 32x32 Pixel). */
	private Dimension fieldDimension;

	/**
	 * Konstruktor: Initialisiert die grafische Ansicht mit bestimmten Maßen.
	 *
	 * @param width Breite des Fensters in Pixeln
	 * @param height Höhe des Fensters in Pixeln
	 * @param fieldDimension Dimension jedes Spielfeldes
	 */
	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.SCREEN_WIDTH = width;
		this.SCREEN_HEIGHT = height;

		this.BOARD_WIDTH = SCREEN_WIDTH / fieldDimension.width;
		this.BOARD_HEIGHT = SCREEN_HEIGHT / fieldDimension.height;

		this.fieldDimension = fieldDimension;
		this.bg = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT); // Hintergrundfläche

		// Initialisierung des Spielfeldes und der Farben
		this.board = new Rectangle[BOARD_HEIGHT][BOARD_WIDTH];
		this.boardColor = new Color[BOARD_HEIGHT][BOARD_WIDTH];
		for (int y = 0; y < SCREEN_HEIGHT / fieldDimension.height; y++) {
			for (int x = 0; x < SCREEN_WIDTH / fieldDimension.width; x++) {
				board[y][x] = new Rectangle(0, 0);
				boardColor[y][x] = new Color(0, 0, 0); // Standardfarbe: Schwarz
			}
		}

		// Code für Buttons (auskommentiert) könnte hier eingefügt werden.
	}

	/** Rechteck für den Hintergrund. */
	private final Rectangle bg;

	/** Rechteck für den Spieler. */
	private final Rectangle player = new Rectangle(0, 0);

	/** Rechtecke für die Gegner. */
	private ArrayList<Rectangle> enemies = new ArrayList<>();

	/** 2D-Array für das Spielfeld (Grafik). */
	private final Rectangle[][] board;

	/** 2D-Array für die Farben des Spielfeldes. */
	private final Color[][] boardColor;

	/**
	 * Zeichnet das komplette Spielfeld neu.
	 *
	 * @param g Das Graphics-Objekt zum Zeichnen
	 */
	@Override
	public void paint(Graphics g) {
		// Hintergrund zeichnen
		g.setColor(Color.RED);
		g.fillRect(bg.x, bg.y, bg.width, bg.height);

		// Spielfeld zeichnen
		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				g.setColor(boardColor[y][x]);
				g.fillRect(board[y][x].x, board[y][x].y, board[y][x].width, board[y][x].height);
			}
		}

		// Spieler zeichnen
		g.setColor(new Color(255, 0, 255)); // Magenta
		g.fillRect(player.x, player.y, player.width, player.height);

		// Gegner zeichnen
		g.setColor(new Color(255, 100, 0)); // Orange
		for (Rectangle r : enemies) {
			g.fillRect(r.x, r.y, r.width, r.height);
		}
	}

	/**
	 * Aktualisiert alle grafischen Elemente entsprechend dem aktuellen Spielzustand.
	 *
	 * @param gameState Der aktuelle Spielzustand
	 */
	@Override
	public void update(GameState gameState) {
		// Position und Größe des Spielers setzen
		player.setSize(fieldDimension);
		player.setLocation(
				(int) (gameState.getPlayer().getPositionX() * fieldDimension.width),
				(int) (gameState.getPlayer().getPositionY() * fieldDimension.height)
		);

		// Gegner aktualisieren
		enemies.clear();
		for (Enemy e : gameState.getListOfEnemies()) {
			Rectangle r = new Rectangle();
			r.setSize(fieldDimension);
			r.setLocation(
					(int) (e.getPositionX() * fieldDimension.width),
					(int) (e.getPositionY() * fieldDimension.height)
			);
			enemies.add(r);
		}

		// Spielfeld-Felder aktualisieren
		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				board[y][x].setSize(fieldDimension);
				board[y][x].setLocation(
						(int) (x * fieldDimension.width),
						(int) (y * fieldDimension.height)
				);
				boardColor[y][x] = gameState.getBoard().getField(x, y).color;
			}
		}

		// Komplette Neuzeichnung (repaint xD kp wie man das übersetzen soll) auslösen
		repaint();
		paintImmediately(0, 0, getWidth(), getHeight());
	}
}
