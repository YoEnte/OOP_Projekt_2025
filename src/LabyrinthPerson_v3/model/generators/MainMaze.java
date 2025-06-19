package model.generators;

import model.Coordinates;
import model.Field;
import model.Board;

public class MainMaze {
    public static Field[][] generate(){
        Field[][] board = new Field[20][20];

        String[] maze = {
                "■■■■■■■■■■■■■■■■■■■■",
                "■■■■■■■■■■■■■■■■■■■■",
                "■S    G■        ■  ■",
                "■■ ■■■■■ ■■■■■■■■ ■■",
                "■                  ■",
                "■ ■■■■■ ■■■■■■■■■■ ■",
                "■     ■     ■      ■",
                "■■■■■ ■■■■■ ■■■■■■ ■",
                "■     ■            ■",
                "■ ■■■ ■ ■■■■■■■■■■ ■",
                "■   ■ ■     ■ ■    ■",
                "■ ■ ■■■■■■■ ■ ■ ■■■■",
                "■ ■         ■      ■",
                "■ ■■■■■■■■■■■ ■■■ ■■",
                "■     ■     ■   ■  ■",
                "■■■■■ ■ ■■■■■■■ ■■■■",
                "■     ■         ■  ■",
                "■ ■■■■■ ■■■■■■■ ■ ■■",
                "■             ■   G■",
                "■■■■■■■■■■■■■■■■■■■■"
        };

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                char c = maze[y].charAt(x);
                switch (c) {
                    case '■' -> board[y][x] = Field.WALL;
                    case 'S' -> board[y][x] = Field.START;
                    case 'G' -> board[y][x] = Field.GOAL;
                    case ' ' -> board[y][x] = Field.PATH;
                }
            }
        }

        return board;
    }
}
