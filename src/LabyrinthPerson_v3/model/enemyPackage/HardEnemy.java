package model.enemyPackage;

public class HardEnemy extends Enemy {

    public HardEnemy(int positionX,int positionY){
        super(positionX, positionY);


    }

    public HardEnemy(HardEnemy other) {
        super(other.getPositionX(), other.getPositionY());
    }
}
