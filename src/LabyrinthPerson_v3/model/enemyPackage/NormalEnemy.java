package model.enemyPackage;

import model.Direction;
import model.GameState;
import model.rules.GameRuleLogic;

import java.util.ArrayList;
import java.util.Random;

public class NormalEnemy extends Enemy {

    static Random random = new Random();

    public NormalEnemy(int positionX,int positionY){
        super(positionX, positionY);
    }

    public NormalEnemy(NormalEnemy other) {
        super(other.getPositionX(), other.getPositionY());
    }

    public void performMove(GameState gameState) {

        ArrayList<Direction> possibleDirections = GameRuleLogic.getPossibleMoves(gameState, getPositionX(), getPositionY());

        int playerX = gameState.getPlayer().getPositionX();
        int playerY = gameState.getPlayer().getPositionY();

        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        int i = 0;

        for (Direction d : possibleDirections) {
            int newX = getPositionX() + d.deltaX;
            int newY = getPositionY() + d.deltaY;

            double distance = Math.sqrt(Math.pow(playerX - newX, 2) + Math.pow(playerY - newY, 2));

            if (distance < minDistance) {
                minDistance = distance;
                minIndex = i;
            }

            i++;
        }

        setX(getPositionX() + possibleDirections.get(minIndex).deltaX);
        setY(getPositionY() + possibleDirections.get(minIndex).deltaY);
    }
}
