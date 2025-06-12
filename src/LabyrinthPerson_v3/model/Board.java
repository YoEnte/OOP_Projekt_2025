package model;

import java.util.ArrayList;

import view.View;

/**
 * The world is our model. It saves the bare minimum of information required to
 * accurately reflect the state of the game. Note how this does not know
 * anything about graphics.
 */
public class Board {

	/** The world's width. */
	private final int width;
	/** The world's height. */
	private final int height;

	private Field[][] board;


	/**
	 * Creates a new world with the given size.t
	 */
	public Board(int width, int height) {
		// Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;
		this.board = new Field[height][width];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				board[y][x] = Field.PATH;
			}
		}
	}

	public void generate() {

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {


			}
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// Getters and Setters

	/**
	 * Returns the width of the world.
	 * 
	 * @return the width of the world.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the world.
	 * 
	 * @return the height of the world.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the player's x position.
	 * 
	 * @return the player's x position.
	 */


}
