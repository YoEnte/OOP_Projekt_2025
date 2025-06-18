package model;

import java.util.ArrayList;

import model.generators.*;
import view.View;

/**
 * The world is our model. It saves the bare minimum of information required to
 * accurately reflect the state of the game. Note how this does not know
 * anything about graphics.
 */
public class Board {

	/** The world's width. */
	private int width;
	/** The world's height. */
	private int height;

	private Field[][] board;
	private Coordinates start;
	private Coordinates[] goals;

	/**
	 * Creates a new world with the given size.t
	 */
	public Board(int width, int height) {
		// TODO -> check for values??? Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;

		Board newBoard = GeneratorFromImage.generate(29, 29, "./src/LabyrinthPerson_v3/resources/qr_code.bmp");
		this.board = newBoard.board;
		this.start = newBoard.start;
		this.goals = newBoard.goals;
	}

	public Board(int width, int height, Difficulty difficulty) {
		// TODO -> check for values??? Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;

		Board newBoard;
		if (difficulty == Difficulty.SECRET) {
			newBoard = GeneratorFromImage.generate(width, height, "./src/LabyrinthPerson_v3/resources/qr_code.bmp");
		} else {
			newBoard = TestMaze.generate();
		}

		this.board = newBoard.board;
		this.start = newBoard.start;
		this.goals = newBoard.goals;
	}

	public Board(Board other) {
		this.width = other.width;
		this.height = other.height;
		this.board = other.board.clone();
	}

	public Board(Field[][] fields, int width, int height) {
		this.board = fields.clone();
		this.width = width;
		this.height = height;
	}

	public Board(Field[][] fields, int width, int height, Coordinates start, Coordinates[] goals) {
		this.board = fields.clone();
		this.width = width;
		this.height = height;
		this.start = new Coordinates(start);
		this.goals = new Coordinates[goals.length];
		int i = 0;
		for (Coordinates g : goals) {
			this.goals[i] = new Coordinates(g);
			i++;
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// Getters and Setters

	//public Field getStart

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

	public Board setFieldOnBoard(Board board, Coordinates newFieldCoordinates, Field newFieldType) {
		Field[][] newField = board.getFieldList();
		newField[newFieldCoordinates.getYCoordinate()][newFieldCoordinates.getXCoordinate()] = newFieldType;
		return new Board(newField, board.getWidth(), board.getHeight());
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

	public ArrayList<Coordinates> getIndexForFieldType(Field field){
		ArrayList<Coordinates> indexes = new ArrayList<>();


		for(int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				if(board[j][i] == field){
					indexes.add(new Coordinates(i,j));
				}
			}
		}

		return indexes;
	}



	/**
	 * Returns the player's x position.
	 * 
	 * @return the player's x position.
	 */


}
