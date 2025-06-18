package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.Controller;
import model.GameState;
import model.enemyPackage.Enemy;

/**
 * A graphical view of the world.
 */
public class GraphicView extends JPanel implements View {
	private JFrame frame;

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

//		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//		buttonPanel.setBackground(new Color(240, 240, 240));
//
//		JButton backButton = new JButton("← Zurück");
//		JButton forwardButton = new JButton("Vor →");
//		JButton restartButton = new JButton("↺ Neustart");
//
//		buttonPanel.add(backButton);
//		buttonPanel.add(forwardButton);
//		buttonPanel.add(restartButton);



	}

	/** The background rectangle. */
	private final Rectangle bg;
	/** The rectangle we're moving. */
	private final Rectangle player = new Rectangle(0, 0);
	private ArrayList<Rectangle> enemies = new ArrayList<>();


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
		g.setColor(new Color(255, 0, 255));
		g.fillRect(player.x, player.y, player.width, player.height);

		// Paint enemies
		g.setColor(new Color(255, 100, 0));
		for (Rectangle r : enemies) {
			g.fillRect(r.x, r.y, r.width, r.height);
		}


//		ArrayList<AbstractEnemy> enemies = GameState.getListOfEnemies();
//		(enemies.get(0).getPositionX());
//		(enemies.get(0).getClass());
//
//		// g.fillRect(enemies.get(0).getPositionX(), enemies.get(0).getPositionY(), enemy.width, enemy.height);


	}
	public void gameStateRepaint(GameState gameState){
		update(gameState);
	}

	@Override
	public void update(GameState gameState) {


		// Update players size and location
		player.setSize(fieldDimension);
		player.setLocation(
			(int) (gameState.getPlayer().getPositionX() * fieldDimension.width),
			(int) (gameState.getPlayer().getPositionY() * fieldDimension.height)
		);

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
		paintImmediately(0, 0, getWidth(), getHeight());
	}

	public void addButtons(Controller c){

	}

}
