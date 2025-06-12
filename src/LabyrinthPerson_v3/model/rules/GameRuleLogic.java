package model.rules;

import model.Direction;
import model.Field;
import model.GameState;

import java.util.ArrayList;

public class GameRuleLogic {
    public GameRuleLogic(){}

    public static boolean isValidToMove(GameState gameState, Direction direction, int positionX, int positionY) throws InvalidMoveException {
        int playerX = gameState.getPlayer().getPositionX() + direction.deltaX;
        int playerY = gameState.getPlayer().getPositionY() + direction.deltaY;

        if((gameState.getBoard().getFieldList())[playerX][playerY] ==  Field.WALL){
            throw new InvalidMoveException("There is a Wall");
        }
        
        // TODO Hier muss alles rein, was den Spieler betrifft. Sonst ist alles andere allgemein gehalten
        if(gameState.getPlayer().getPositionX() == positionX && gameState.getPlayer().getPositionY() == positionY){
            
        }
        return true;
    }


    public void  getPossibleMoves(GameState gameState, int x, int y){
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
    }
}
