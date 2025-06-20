package model.enemyPackage;

import model.Direction;
import model.GameState;
import model.rules.GameRuleLogic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class EasyEnemy extends Enemy {
    static Random random = new Random();


    public EasyEnemy(int positionX, int positionY, String url){
        super(positionX, positionY, url);
    }

    public EasyEnemy(EasyEnemy other) {
        super(other.getPositionX(), other.getPositionY(), other.getUrl());
    }

    //TODO Fix invalid moves
    public void performMove(GameState gameState){
        ArrayList<Direction> possibleDirections = GameRuleLogic.getPossibleMoves(gameState, getPositionX(), getPositionY());
        int randomIndex = random.nextInt(possibleDirections.size());

        setX(getPositionX() + possibleDirections.get(randomIndex).deltaX);
        setY(getPositionY() + possibleDirections.get(randomIndex).deltaY);
    }
}
