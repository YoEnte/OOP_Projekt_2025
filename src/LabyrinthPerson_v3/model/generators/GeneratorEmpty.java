package model.generators;

import model.Field;

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

        return board;
    }
}
