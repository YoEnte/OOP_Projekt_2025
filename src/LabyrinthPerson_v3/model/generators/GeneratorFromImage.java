package model.generators;

import model.Board;
import model.Coordinates;
import model.Field;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GeneratorFromImage {

    private static GeneratorFromImage generatorFromImage;

    public static Field[][] generate(int width, int height, String sourcePath) {

        Field[][] board = new Field[height][width];
        BufferedImage image = null;

        try {
            File input_file = new File(sourcePath);
            new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(input_file);

            //System.out.println("Reading complete");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    board[y][x] = getField(image.getRGB(x, y));;
                }
                // System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error " + e);
        }

        return board;
    }

    private static Field getField(int p) {
        int r = (p & 0x00ff0000) >> 16;
        int g = (p & 0x0000ff00) >> 8;
        int b = (p & 0x000000ff);
        //System.out.print("(" + r + " " + g + " " + b + ") ");
        Color clr = new Color(r, g ,b);

        Field thisField = null;

        if (clr.equals(Field.GOAL.color)) {
            thisField = Field.GOAL;
        } else if (clr.equals(Field.WALL.color)) {
            thisField = Field.WALL;
        } else if (clr.equals(Field.PATH.color)) {
            thisField = Field.PATH;
        } else if (clr.equals(Field.START.color)) {
            thisField = Field.START;
        } else {
            thisField = Field.PATH;
        }

        return thisField;
    }
}
