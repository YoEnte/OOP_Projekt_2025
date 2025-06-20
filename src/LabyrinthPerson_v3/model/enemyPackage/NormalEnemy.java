package model.enemyPackage;

import model.Direction;
import model.GameState;
import model.rules.GameRuleLogic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Repräsentiert einen stärkeren Gegner, der immer versucht seine euklidische Distanz zum Spieler zuverringern
 */
public class NormalEnemy extends Enemy {

    static Random random = new Random();

    /**
     * Einfacher Konstrukor siehe Enemy
     * @param positionX
     * @param positionY
     * @param url
     */
    public NormalEnemy(int positionX, int positionY, String url){
        super(positionX, positionY, url);
    }

    /**
     * Erweiterter Konstruktor siehe Enemy
     * @param positionX
     * @param positionY
     * @param url
     * @param image
     * @param imageFlipped
     */
    public NormalEnemy(int positionX, int positionY, String url, BufferedImage image, BufferedImage imageFlipped) {
        super(positionX, positionY, url, image, imageFlipped);
    }

    /**
     * Copy-Konstrukor (kopiert nur die Referenzen zu den Bildern)
     * @param other
     */
    public NormalEnemy(NormalEnemy other) {
        super(other.getPositionX(), other.getPositionY(), other.getUrl(), other.getImage(), other.getImageFlipped());
    }

    /**
     * generiert einen Zug, der aus der aktuellen Position gespielt werden soll
     * @param gameState aktueller Spielstand
     */
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
