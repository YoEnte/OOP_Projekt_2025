package model.enemyPackage;

public class NormalEnemy extends Enemy {

    public NormalEnemy(int positionX,int positionY){
        super(positionX, positionY);


    }

    public NormalEnemy(NormalEnemy other) {
        super(other.getPositionX(), other.getPositionY());
    }
}
