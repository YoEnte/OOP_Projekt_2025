package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import model.Board;
import model.Field;
import model.GameState;

/**
 * A graphical view of the world.
 */
public class GraphicView extends JPanel implements View {

	/** The view's width. */
	private final int SCREEN_WIDTH;
	/** The view's height. */
	private final int SCREEN_HEIGHT;

	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;



	private Dimension fieldDimension;

	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.SCREEN_WIDTH = width;
		this.SCREEN_HEIGHT = height;

		this.BOARD_WIDTH = SCREEN_WIDTH / fieldDimension.width;
		this.BOARD_HEIGHT = SCREEN_HEIGHT / fieldDimension.height;


		this.fieldDimension = fieldDimension;
        this.bg = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT);

		this.board = new Rectangle[BOARD_HEIGHT][BOARD_WIDTH];
		this.boardColor = new Color[BOARD_HEIGHT][BOARD_WIDTH];
		for (int y = 0; y < SCREEN_WIDTH / fieldDimension.width; y++) {
			for (int x = 0; x < SCREEN_WIDTH / fieldDimension.width; x++) {

				board[y][x] = new Rectangle(0, 0);
				boardColor[y][x] = new Color(0, 0, 0);
			}
		}
	}

	/** The background rectangle. */
	private final Rectangle bg;
	/** The rectangle we're moving. */
	private final Rectangle player = new Rectangle(0, 0);

	private final Rectangle[][] board;

	private final Color[][] boardColor;

	/**
	 * Creates a new instance.
	 */
	@Override
	public void paint(Graphics g) {
		// Paint background
		g.setColor(Color.RED);
		g.fillRect(bg.x, bg.y, bg.width, bg.height);

		// Paint Board
		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				g.setColor(boardColor[y][x]);
				g.fillRect(board[y][x].x, board[y][x].y, board[y][x].width, board[y][x].height);
			}
		}

		// Paint player
		g.setColor(Color.BLACK);
		g.fillRect(player.x, player.y, player.width, player.height);
	}

	@Override
	public void update(GameState gameState) {

		// Update players size and location
		player.setSize(fieldDimension);
		player.setLocation(
			(int) (gameState.getPlayer().getPlayerX() * fieldDimension.width),
			(int) (gameState.getPlayer().getPlayerY() * fieldDimension.height)
		);

		// Update Field sizes and locations
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

		repaint();
	}

}
