package model.rules;

import model.Direction;
import model.Field;
import model.GameState;
import model.enemyPackage.AbstractEnemy;

import java.util.ArrayList;

public class GameRuleLogic {
    public GameRuleLogic(){}

    public static boolean isValidToMove(GameState gameState, Direction direction, int positionX, int positionY) throws InvalidMoveException {
        int playerX = gameState.getPlayer().getPositionX() + direction.deltaX;
        int playerY = gameState.getPlayer().getPositionY() + direction.deltaY;


        
        // TODO Hier muss alles rein, was den Spieler betrifft. Sonst ist alles andere allgemein gehalten
        if(gameState.getPlayer().getPositionX() == positionX && gameState.getPlayer().getPositionY() == positionY){

            if(!gameState.getListOfEnemies().isEmpty()){
                for(AbstractEnemy e : gameState.getListOfEnemies()){
                    if(e.getPositionX() == playerX && e.getPositionY() == playerY){
                        throw new InvalidMoveException("There is an Enemy");
                    }
                }
            }
            if((gameState.getBoard().getFieldList())[playerX][playerY] ==  Field.WALL){
                throw new InvalidMoveException("There is a Wall");
            }


        } else{
            if((gameState.getBoard().getFieldList())[positionX + direction.deltaX][positionY + direction.deltaY] ==  Field.WALL){
                throw new InvalidMoveException("There is a Wall");
            }
        }
        return true;
    }

    public static boolean isValidToSpawn(GameState gameState, int positionX, int positionY) throws InvalidSpawnException {

        if((gameState.getBoard().getFieldList())[positionX][positionY] ==  Field.WALL){
            throw new InvalidSpawnException("There is a Wall");
        }
        if(gameState.getPlayer().getPositionX() == positionX && gameState.getPlayer().getPositionY() == positionY){
            throw new InvalidSpawnException("There is the Player");
        }
        if(!gameState.getListOfEnemies().isEmpty()){
            for(AbstractEnemy e : gameState.getListOfEnemies()){
                if(e.getPositionX() == positionX && e.getPositionY() == positionY){
                    throw new InvalidSpawnException("There is an Enemy");
                }
            }
        }

        return true;
    }


    public static ArrayList<Direction> getPossibleMoves(GameState gameState, int x, int y){
        ArrayList<Direction> validDirections = new ArrayList<>();
        ArrayList<Direction> allDirections = new ArrayList<>();
        allDirections.add(Direction.UP);
        allDirections.add(Direction.DOWN);
        allDirections.add(Direction.RIGHT);
        allDirections.add(Direction.LEFT);

        for(Direction direction: allDirections){
            try {
                GameRuleLogic.isValidToMove(gameState, direction, x, y);
                validDirections.add(direction);
                
            }catch (InvalidMoveException e){
                //pass
            }
        }
        return validDirections;
    }
}
