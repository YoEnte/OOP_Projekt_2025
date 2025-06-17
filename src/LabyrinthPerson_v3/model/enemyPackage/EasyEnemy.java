package model.enemyPackage;

import model.Direction;
import model.GameState;
import model.rules.GameRuleLogic;

import java.util.ArrayList;
import java.util.Random;


public class EasyEnemy extends Enemy {
    static Random random = new Random();


    public EasyEnemy(int positionX,int positionY){
        super(positionX, positionY);
    }

    public EasyEnemy(EasyEnemy other) {
        super(other.getPositionX(), other.getPositionY());
    }

    //TODO Fix invalid moves
    public void performMove(GameState gameState){
        ArrayList<Direction> possibleDirections = GameRuleLogic.getPossibleMoves(gameState, this.getPositionX(), this.getPositionY());
        int randomIndex = random.nextInt(possibleDirections.size());

        this.setX(this.getPositionX() + possibleDirections.get(randomIndex).deltaX);
        this.setY(this.getPositionY() + possibleDirections.get(randomIndex).deltaY);
    }
}
