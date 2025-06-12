package model.enemyPackage;

import model.Direction;
import model.GameState;
import model.rules.GameRuleLogic;

import java.util.ArrayList;
import java.util.Random;


public class EasyEnemy extends AbstractEnemy{
    static Random random = new Random();


    public EasyEnemy(int positionX,int positionY){
        super(positionX, positionY);
    }

    //TODO Fix invalid moves
    public void performMove(GameState gameState, EasyEnemy enemy){
        ArrayList<Direction> possibleDirections = GameRuleLogic.getPossibleMoves(gameState, enemy.getPositionX(), enemy.getPositionY());
        int randomIndex = random.nextInt(possibleDirections.size());

        enemy.setX(enemy.getPositionX() + possibleDirections.get(randomIndex).deltaX);
        enemy.setY(enemy.getPositionY() + possibleDirections.get(randomIndex).deltaY);
    }
}
