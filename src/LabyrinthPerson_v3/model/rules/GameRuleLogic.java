package model.rules;

import model.Coordinates;
import model.Direction;
import model.Field;
import model.GameState;
import model.enemyPackage.Enemy;

import java.util.ArrayList;

/**
 * Diese Klasse enthält die Spiellogik bezüglich zulässiger Bewegungen, Spawnregeln
 * sowie allgemeiner Spiellogik wie z.B. Zielerreichung oder Kollision mit Gegnern.
 */
public class GameRuleLogic {

    /** Konstruktor der Klasse (leer, da keine Initialisierung notwendig ist). */
    public GameRuleLogic(){}

    /**
     * Prüft, ob eine Bewegung in eine bestimmte Richtung von einer gegebenen Position erlaubt ist.
     *
     * @param gameState Der aktuelle Zustand des Spiels.
     * @param direction Die Bewegungsrichtung.
     * @param positionX Die X-Position des zu bewegenden Objekts.
     * @param positionY Die Y-Position des zu bewegenden Objekts.
     * @return true, wenn die Bewegung erlaubt ist.
     * @throws InvalidMoveException wenn die Bewegung nicht zulässig ist (z.B. durch Wand oder Gegner blockiert).
     */
    public static boolean isValidToMove(GameState gameState, Direction direction, int positionX, int positionY) throws InvalidMoveException {
        int playerX = gameState.getPlayer().getPositionX() + direction.deltaX;
        int playerY = gameState.getPlayer().getPositionY() + direction.deltaY;

        // Wenn keine Bewegung erfolgt, ist sie automatisch gültig
        if(direction == Direction.NONE){
            return true;
        }

        // Prüfe, ob es sich um den Spieler handelt
        if(gameState.getPlayer().getPositionX() == positionX && gameState.getPlayer().getPositionY() == positionY){

            // Überprüfen, ob die Bewegung außerhalb des Spielfelds geht
            if((playerX >= gameState.getBoard().getWidth() || playerX < 0) || (playerY >= gameState.getBoard().getHeight() || playerY < 0)){
                throw new InvalidMoveException("The position is out of bounds");
            }

            // Überprüfen, ob sich eine (unsichtbare) Wand am Ziel befindet
            Field newField = (gameState.getBoard().getFieldList())[playerY][playerX];
            if(newField == Field.WALL || newField == Field.INVW){
                throw new InvalidMoveException("There is a Wall");
            }

        } else {
            // Für alle anderen Objekte (z. B. Gegner)
            Field newField = (gameState.getBoard().getFieldList())[positionY + direction.deltaY][positionX + direction.deltaX];
            if(newField == Field.WALL || newField == Field.INVW){
                throw new InvalidMoveException("There is a Wall");
            }

            // Überprüfen, ob ein Gegner am Zielpunkt ist
            ArrayList<Enemy> enemies = gameState.getListOfEnemies();
            for(Enemy e : enemies){
                if(positionX + direction.deltaX == e.getPositionX() && positionY + direction.deltaY == e.getPositionY()){
                    throw new InvalidMoveException("There is an Enemy");
                }
            }
        }
        return true;
    }

    /**
     * Überprüft, ob ein neues Objekt (z.B. Gegner) an einer bestimmten Position gespawnt werden darf.
     *
     * @param gameState Der aktuelle Zustand des Spiels.
     * @param positionX Die X-Koordinate.
     * @param positionY Die Y-Koordinate.
     * @return true, wenn das Spawnen an der Position erlaubt ist.
     * @throws InvalidSpawnException wenn z.B. bereits ein Objekt an der Position ist oder es sich um eine Wand handelt.
     */
    public static boolean isValidToSpawn(GameState gameState, int positionX, int positionY) throws InvalidSpawnException {

        // Überprüfen, ob es eine (unsichtbare) Wand ist
        Field newField = (gameState.getBoard().getFieldList())[positionY][positionX];
        if(newField == Field.WALL || newField == Field.INVW){
            throw new InvalidSpawnException("There is a Wall");
        }

        // Überprüfen, ob bereits der Spieler dort ist
        if(gameState.getPlayer().getPositionX() == positionX && gameState.getPlayer().getPositionY() == positionY){
            throw new InvalidSpawnException("There is the Player");
        }

        // Überprüfen, ob bereits ein Gegner dort steht
        if(!gameState.getListOfEnemies().isEmpty()){
            for(Enemy e : gameState.getListOfEnemies()){
                if(e.getPositionX() == positionX && e.getPositionY() == positionY){
                    throw new InvalidSpawnException("There is an Enemy");
                }
            }
        }

        // Überprüfen, ob es weit genug von allen Startfeldern ist (Radius 5).
        ArrayList<Coordinates> starts = gameState.getBoard().getIndexForFieldType(Field.START);
        for (Coordinates c : starts) {
            if (Math.sqrt(Math.pow(c.getXCoordinate() - positionX, 2) + Math.pow(c.getYCoordinate() - positionY, 2)) < 5) {
                System.out.println(positionX);
                System.out.println(positionY);
                throw new InvalidSpawnException("In Spawnprotection Radius");
            }
        }

        return true;
    }

    /**
     * Gibt eine Liste aller gültigen Bewegungsrichtungen für eine bestimmte Position zurück.
     *
     * @param gameState Der aktuelle Zustand des Spiels.
     * @param x Die X-Koordinate der Startposition.
     * @param y Die Y-Koordinate der Startposition.
     * @return Eine Liste mit allen erlaubten Bewegungsrichtungen.
     */
    public static ArrayList<Direction> getPossibleMoves(GameState gameState, int x, int y){
        ArrayList<Direction> validDirections = new ArrayList<>();
        ArrayList<Direction> allDirections = new ArrayList<>();
        allDirections.add(Direction.UP);
        allDirections.add(Direction.DOWN);
        allDirections.add(Direction.RIGHT);
        allDirections.add(Direction.LEFT);
        allDirections.add(Direction.NONE);

        // Durchlaufen aller Richtungen und prüfen, ob Bewegung erlaubt ist
        for(Direction direction: allDirections){
            if(((gameState.getBoard().getHeight() > (y + direction.deltaY) && (y + direction.deltaY) > -1)) &&
                    ((gameState.getBoard().getWidth() > (x + direction.deltaX) && (x + direction.deltaX > -1)))){
                try {
                    GameRuleLogic.isValidToMove(gameState, direction, x, y);
                    validDirections.add(direction); // Nur gültige Richtungen hinzufügen
                } catch (InvalidMoveException e){
                    // Ignorieren, da nur gültige Richtungen gespeichert werden
                }
            }
        }
        return validDirections;
    }

    /**
     * Überprüft, ob ein Gegner den Spieler erreicht hat.
     *
     * @param gameState Der aktuelle Zustand des Spiels.
     * @return true, wenn sich ein Gegner auf dem gleichen Feld wie der Spieler befindet.
     */
    public static boolean enemyCatchedPlayer(GameState gameState){

        ArrayList<Enemy> enemies = gameState.getListOfEnemies();
        int playerX = gameState.getPlayer().getPositionX();
        int playerY = gameState.getPlayer().getPositionY();

        for(Enemy e : enemies){
            if(e.getPositionX() == playerX && e.getPositionY() == playerY){
                return true; // Spieler wurde von Gegner erreicht
            }
        }

        return false;
    }

    /**
     * Überprüft, ob sich der Spieler auf einem Zielfeld befindet.
     *
     * @param gameState Der aktuelle Zustand des Spiels.
     * @param x Die X-Koordinate des Spielers.
     * @param y Die Y-Koordinate des Spielers.
     * @return true, wenn das Feld ein Ziel ist.
     */
    public static boolean playerInGoal(GameState gameState, int x, int y) {
        return (gameState.getBoard().getFieldList())[y][x] == Field.GOAL;
    }
}
