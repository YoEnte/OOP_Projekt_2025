package model.enemyPackage;

import model.Direction;
import model.GameState;
import model.rules.GameRuleLogic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Repräsentiert einen einfachen Gegner, der einen zufälligen Zug von seinen Optionen auswählt
 */
public class EasyEnemy extends Enemy {
    static Random random = new Random();

    /**
     * EInfacher Konstruktor siehe Enemy
     * @param positionX
     * @param positionY
     * @param url
     */
    public EasyEnemy(int positionX, int positionY, String url){
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
    public EasyEnemy(int positionX, int positionY, String url, BufferedImage image, BufferedImage imageFlipped) {
        super(positionX, positionY, url, image, imageFlipped);
    }

    /**
     * Copy-Konstruktor (kopiert nur die Referenz zu den Bildern)
     * @param other
     */
    public EasyEnemy(EasyEnemy other) {
        super(other.getPositionX(), other.getPositionY(), other.getUrl(), other.getImage(), other.getImageFlipped());
    }

    /**
     * generiert einen Zug, der aus der aktuellen Position gespielt werden soll
     * @param gameState aktueller Spielstand
     */
    public void performMove(GameState gameState){
        ArrayList<Direction> possibleDirections = GameRuleLogic.getPossibleMoves(gameState, getPositionX(), getPositionY());
        int randomIndex = random.nextInt(possibleDirections.size());

        setX(getPositionX() + possibleDirections.get(randomIndex).deltaX);
        setY(getPositionY() + possibleDirections.get(randomIndex).deltaY);
    }
}
