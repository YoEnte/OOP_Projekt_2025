package model.enemyPackage;
import java.util.*;

public class Enemies {


    private int positionX;
    private int positionY;
    static ArrayList<AbstractEnemy> listOfEnemies = new ArrayList<>();

    public Enemies(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public static ArrayList<AbstractEnemy> getListOfEnemies() {
        return listOfEnemies;
    }

    public Enemies(Enemies other) {

    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }
}
