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
		// TODO -> check for values??? Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;

		// add 2 for walls around maze
		this.board = new Field[height][width];

		generate_empty();
	}

	public void generate_empty() {

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
					board[y][x] = Field.WALL;
				} else {
					board[y][x] = Field.PATH;
				}
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
	 *
	 */
	public Field[][] getFieldList() {
		return board;
	}

	public Field getField(int x, int y) {
		return board[y][x];
	}

	/**
	 * Returns the player's x position.
	 * 
	 * @return the player's x position.
	 */


}
