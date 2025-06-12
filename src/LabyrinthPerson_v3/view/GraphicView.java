package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import model.Board;
import model.Field;

/**
 * A graphical view of the world.
 */
public class GraphicView extends JPanel implements View {

	/** The view's width. */
	private final int WIDTH;
	/** The view's height. */
	private final int HEIGHT;

	private Dimension fieldDimension;

	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldDimension = fieldDimension;
        this.bg = new Rectangle(WIDTH, HEIGHT);

		this._board = new Rectangle[HEIGHT / fieldDimension.height][WIDTH / fieldDimension.width];
		this._boardColor = new Color[HEIGHT / fieldDimension.height][WIDTH / fieldDimension.width];
		for (int y = 0; y < WIDTH / fieldDimension.width; y++) {
			for (int x = 0; x < WIDTH / fieldDimension.width; x++) {

				_board[y][x] = new Rectangle(0, 0);
				_boardColor[y][x] = new Color(0, 0, 0);
			}
		}
	}

	/** The background rectangle. */
	private final Rectangle bg;
	/** The rectangle we're moving. */
	private final Rectangle player = new Rectangle(0, 0);

	private final Rectangle[][] _board;

	private final Color[][] _boardColor;

	/**
	 * Creates a new instance.
	 */
	@Override
	public void paint(Graphics g) {
		// Paint background
		g.setColor(Color.RED);
		g.fillRect(bg.x, bg.y, bg.width, bg.height);

		// Paint Board
		for (int y = 0; y < HEIGHT / fieldDimension.height; y++) {
			for (int x = 0; x < WIDTH / fieldDimension.width; x++) {
				g.setColor(_boardColor[y][x]);
				g.fillRect(_board[y][x].x, _board[y][x].y, _board[y][x].width, _board[y][x].height);
			}
		}

		// Paint player
		g.setColor(Color.BLACK);
		g.fillRect(player.x, player.y, player.width, player.height);
	}

	@Override
	public void update(Board board) {

		// Update players size and location
		player.setSize(fieldDimension);
		player.setLocation(
			(int) (board.getPlayerX() * fieldDimension.width),
			(int) (board.getPlayerY() * fieldDimension.height)
		);

		// Update Field sizes and locations
		for (int y = 0; y < HEIGHT / fieldDimension.height; y++) {
			for (int x = 0; x < WIDTH / fieldDimension.width; x++) {
				_board[y][x].setSize(fieldDimension);
				_board[y][x].setLocation(
						(int) (x * fieldDimension.width),
						(int) (y * fieldDimension.height)
				);
				_boardColor[y][x] = board.getField(x, y).color;
			}
		}

		repaint();
	}

}
