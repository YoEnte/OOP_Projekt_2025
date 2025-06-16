package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;

import model.Direction;
import model.Board;
import model.GameState;
import view.View;

/**
 * Our controller listens for key events on the main window.
 */
public class Controller extends JFrame implements KeyListener, ActionListener, MouseListener {

	/** The world that is updated upon every key press. */
	private GameState gameState;
	private List<View> views;

	/**
	 * Creates a new instance.
	 * 
	 * @param board the world to be updated whenever the player should move.
	 * @param caged the {@link GraphicsProgram} we want to listen for key presses
	 *              on.
	 */
	public Controller(GameState gameState) {
		// Remember the world
		this.gameState = gameState;
		
		// Listen for key events
		addKeyListener(this);
        // Listen for mouse events.
		// Not used in the current implementation.
		addMouseListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/////////////////// Key Events ////////////////////////////////

	@Override
	public void keyPressed(KeyEvent e) {
		// Check if we need to do something. Tells the world to move the player.
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP, 87:
			gameState.movePlayer(Direction.UP);

			if(gameState.isGameEnded()){
				this.dispose();
			}
            break;

		case KeyEvent.VK_DOWN, 83:
			gameState.movePlayer(Direction.DOWN);
			if(gameState.isGameEnded()){
				this.dispose();
			}
			break;

		case KeyEvent.VK_LEFT, 65:
			gameState.movePlayer(Direction.LEFT);
			if(gameState.isGameEnded()){
				this.dispose();
			}
			break;

		case KeyEvent.VK_RIGHT, 68:
			gameState.movePlayer(Direction.RIGHT);
			if(gameState.isGameEnded()){
				this.dispose();
			}
			break;

		case 79:
			gameState.stepGameState(-1);
			break;

		case 80:
			gameState.stepGameState(1);
			break;
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/////////////////// Action Events ////////////////////////////////

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/////////////////// Mouse Events ////////////////////////////////

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
