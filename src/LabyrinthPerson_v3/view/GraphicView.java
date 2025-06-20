package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.Controller;
import model.Difficulty;
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
	public GraphicView(int width, int height, Dimension fieldDimension, GameState gameState) {
		this.SCREEN_WIDTH = width;
		this.SCREEN_HEIGHT = height;

		this.BOARD_WIDTH = SCREEN_WIDTH / fieldDimension.width;
		this.BOARD_HEIGHT = SCREEN_HEIGHT / fieldDimension.height;

		this.fieldDimension = fieldDimension;
		this.bg = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT); // Hintergrundfläche

		// Initialisierung des Spielfeldes, der Farben und der Bilder
		this.board = new Rectangle[BOARD_HEIGHT][BOARD_WIDTH];
		this.boardColor = new Color[BOARD_HEIGHT][BOARD_WIDTH];
		this.boardImage = new BufferedImage[BOARD_HEIGHT][BOARD_WIDTH];
		for (int y = 0; y < SCREEN_HEIGHT / fieldDimension.width; y++) {
			for (int x = 0; x < SCREEN_WIDTH / fieldDimension.width; x++) {
				board[y][x] = new Rectangle(0, 0);
				boardColor[y][x] = new Color(0, 0, 0); // Standardfarbe: Schwarz
				boardImage[y][x] = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);  // Standard: blankes Bild 10x10
			}
		}

		// Init Spieler Grafiken Blanko
		this.playerImages = new BufferedImage[] {
				new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB),
				new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB)
		};

		reloadGraphics(gameState);

		// Code für Buttons (auskommentiert) könnte hier eingefügt werden.
	}

	/** Rechteck für den Hintergrund. */
	private final Rectangle bg;

	/** Rechteck für den Spieler. */
	private final Rectangle player = new Rectangle(0, 0);

	/** Grafik für den Spieler */
	private final BufferedImage[] playerImages;

	/** Rechtecke für die Gegner. */
	private final ArrayList<Rectangle> enemies = new ArrayList<>();

	/** Grafiken für die Gegner. */
	private final ArrayList<BufferedImage> enemiesImage = new ArrayList<>();

	/** 2D-Array für das Spielfeld (Grafik). */
	private final Rectangle[][] board;

	/** 2D-Array für die Farben des Spielfeldes. */
	private final Color[][] boardColor;

	/** 2D-Array für die Grafiken des Spielfeldes. */
	private final BufferedImage[][] boardImage;

	/** Ob für den nächsten Frame Bilder oder nur Farben gemalt werden soll */
	private boolean useAssets;

	/**
	 * Läd alle Grafiken der Enemies, Spieler, Felder aus dem GameState neu in die eigenen Attribute.
	 *
	 * @param gameState Aktueller GameState
	 */
	private void reloadGraphics(GameState gameState) {
		playerImages[0] = gameState.getPlayer().getImage();
		playerImages[1] = gameState.getPlayer().getImageFlipped();

		for (Enemy e : gameState.getListOfEnemies()) {
			enemiesImage.add(e.getImage());
			enemiesImage.add(e.getImageFlipped());
		}

		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				boardImage[y][x] = gameState.getBoard().getField(x, y).image;
			}
		}
	}

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
				if (useAssets) {
					g.drawImage(boardImage[y][x], board[y][x].x, board[y][x].y, fieldDimension.width, fieldDimension.height, this);
				} else {
					g.setColor(boardColor[y][x]);
					g.fillRect(board[y][x].x, board[y][x].y, board[y][x].width, board[y][x].height);
				}
			}
		}

		// Spieler zeichnen
		if (useAssets) {
			g.drawImage(playerImages[0], player.x, player.y, fieldDimension.width, fieldDimension.height, this);
		} else {
			g.setColor(new Color(255, 0, 255)); // Magenta
			g.fillRect(player.x, player.y, player.width, player.height);
		}

		// Gegner zeichnen
		int i = 0;
		for (Rectangle r : enemies) {
			if (useAssets) {
				g.drawImage(enemiesImage.get(i * 2), r.x, r.y, fieldDimension.width, fieldDimension.height, this);
			} else {
				g.setColor(new Color(255, 100, 0)); // Orange
				g.fillRect(r.x, r.y, r.width, r.height);
			}

			i++;
		}
	}

	/**
	 * Aktualisiert alle grafischen Elemente entsprechend dem aktuellen Spielzustand.
	 *
	 * @param gameState Der aktuelle Spielzustand
	 */
	@Override
	public void update(GameState gameState) {

		// überneheme Asset Modus
		this.useAssets = true;
		if (gameState.getDifficulty() == Difficulty.SECRET) {
			this.useAssets = false;
		}

		// Grafiken neuladen bei jedem Frame, wenn das Game zu Ende ist (für Animation)
		if (gameState.getGameEnd() && useAssets) {
			reloadGraphics(gameState);
		}

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
