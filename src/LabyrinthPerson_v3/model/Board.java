package model;

import java.util.ArrayList;

import model.generators.*;

/**
 * Die Klasse {@code Board} repräsentiert das Spielfeld (Modell) im MVC-Muster.
 * Sie enthält alle Informationen über die Spielfeldstruktur und bietet Methoden zur Abfrage und Manipulation des Spielfelds.
 * Die Klasse weiß nichts über die Darstellung (View) und behandelt keine Benutzerinteraktionen (Controller).
 */
public class Board {

	/** Breite des Spielfelds. */
	private int width;

	/** Höhe des Spielfelds. */
	private int height;

	/** Zweidimensionales Array, das das Spielfeld mit seinen Feldern speichert. */
	private Field[][] board;

	/**
	 * Konstruktor mit zusätzlichem Schwierigkeitsgrad-Parameter.
	 * Wählt je nach Schwierigkeitsgrad zwischen einem Standard-Labyrinth und einem Spezialbild.
	 *
	 * @param width      Breite des Boards
	 * @param height     Höhe des Boards
	 * @param difficulty Schwierigkeitsgrad (z.B. SECRET → QR-Code)
	 */
	public Board(int width, int height, Difficulty difficulty) {
		this.width = width;
		this.height = height;

		Field[][] newBoard;
		if (difficulty == Difficulty.SECRET) {
			// Bei SECRET wird das Labyrinth aus einem Bild generiert
			newBoard = GeneratorFromImage.generate(width, height, "./src/LabyrinthPerson_v3/resources/qr_code2.bmp");
		} else {
			// Standard-Generator für reguläre Schwierigkeitsgrade
			newBoard = MainMaze.generate();
		}

		this.board = newBoard;
	}

	/**
	 * Lädt je nach größe ein "L" oder "W" für gewonnen oder verloren
	 *
	 * @param width      Breite des Boards
	 * @param height     Höhe des Boards
	 * @param winOrLose hat der Spieler gewonnen oder verloren
	 */
	public Board (int width, int height, boolean winOrLose) {
		this.width = width;
		this.height = height;

		Field[][] newBoard;
		if (winOrLose) {
			if (height == 33) {
				newBoard = GeneratorFromImage.generate(width, height, "./src/LabyrinthPerson_v3/resources/W33x33.bmp");
			} else {
				newBoard = GeneratorFromImage.generate(width, height, "./src/LabyrinthPerson_v3/resources/W20x19.bmp");
			}

		} else {
			if (height == 33) {
				newBoard = GeneratorFromImage.generate(width, height, "./src/LabyrinthPerson_v3/resources/L33x33.bmp");
			} else {
				newBoard = GeneratorFromImage.generate(width, height, "./src/LabyrinthPerson_v3/resources/L20x19.bmp");
			}

		}

		this.board = newBoard;
	}


	/**
	 * Lädt ein leeres Feld je nach Größe
	 *
	 * @param width      Breite des Boards
	 * @param height     Höhe des Boards
	 * @param paceHolderLol Platzhalter um ihn von den anderen Konstruktoren zu unterscheiden xD
	 */
	public Board(int width, int height, int paceHolderLol) {
		this.width = width;
		this.height = height;

		Field[][] newBoard;
		if(height == width && width == 33){
			newBoard = GeneratorFromImage.generate(width, height, "./src/LabyrinthPerson_v3/resources/Blank33x33.bmp");
		} else {
			newBoard = GeneratorFromImage.generate(width, height, "./src/LabyrinthPerson_v3/resources/Blank20x19.bmp");
		}

		this.board = newBoard;
	}

	/**
	 * Copy-Konstruktor: Erstellt ein neues Board-Objekt anhand eines bestehenden.
	 * Die Felder werden allerdings nur flach kopiert (nicht deep copy!).
	 *
	 * @param other Das zu kopierende Board.
	 */
	public Board(Board other) {
		this.width = other.width;
		this.height = other.height;
		this.board = other.board.clone(); // flache Kopie des Arrays
	}

	/**
	 * Konstruktor, der ein bereits bestehendes Feld-Array übernimmt.
	 *
	 * @param fields Zweidimensionales Array von Feldern
	 * @param width  Breite
	 * @param height Höhe
	 */
	public Board(Field[][] fields, int width, int height) {
		this.board = fields.clone(); // flache Kopie
		this.width = width;
		this.height = height;
	}

	///////////////////////////////////////////////////////////////////////////
	// Getter und Hilfsmethoden

	/**
	 * Gibt die Breite des Boards zurück.
	 *
	 * @return Breite
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gibt die Höhe des Boards zurück.
	 *
	 * @return Höhe
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gibt ein neues Board-Objekt zurück, bei dem ein bestimmtes Feld ersetzt wurde.
	 * Die Originalinstanz bleibt unverändert (Immutability-Prinzip).
	 *
	 * @param board             Ursprüngliches Board
	 * @param newFieldCoordinates Koordinaten des zu ersetzenden Feldes
	 * @param newFieldType      Neues Feld, das eingefügt werden soll
	 * @return Neues Board mit verändertem Feld
	 */
	public Board setFieldOnBoard(Board board, Coordinates newFieldCoordinates, Field newFieldType) {
		Field[][] newField = board.getFieldList();
		newField[newFieldCoordinates.getYCoordinate()][newFieldCoordinates.getXCoordinate()] = newFieldType;
		return new Board(newField, board.getWidth(), board.getHeight());
	}

	/**
	 * Gibt die interne Felddarstellung zurück (das gesamte Spielfeld).
	 *
	 * @return 2D-Array von Feldern
	 */
	public Field[][] getFieldList() {
		return board;
	}

	/**
	 * Gibt das Feld an einer bestimmten Position zurück.
	 *
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @return Das Feld an Position (x, y)
	 */
	public Field getField(int x, int y) {
		return board[y][x];
	}

	/**
	 * Sucht im gesamten Spielfeld nach allen Feldern eines bestimmten Typs.
	 *
	 * @param field Gesuchter Feldtyp (z.B. START, GOAL)
	 * @return Liste der Koordinaten, an denen sich dieser Feldtyp befindet
	 */
	public ArrayList<Coordinates> getIndexForFieldType(Field field) {
		ArrayList<Coordinates> indexes = new ArrayList<>();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (board[y][x] == field) {
					indexes.add(new Coordinates(x, y));
				}
			}
		}

		return indexes;
	}

}
