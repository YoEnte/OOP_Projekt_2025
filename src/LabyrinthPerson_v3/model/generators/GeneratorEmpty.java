package model.generators;

import model.Coordinates;
import model.Field;
import model.Board;

public class GeneratorEmpty {

    public static Field[][] generate(int width, int height) {

        Field[][] board = new Field[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    board[y][x] = Field.WALL;
                } else {
                    board[y][x] = Field.PATH;
                }
            }
        }
        board[18][18] = Field.GOAL;
        board[1][1] = Field.START;

        return board;
    }
}
