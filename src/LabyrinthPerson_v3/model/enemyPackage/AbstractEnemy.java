package model.enemyPackage;

public class AbstractEnemy {

    private int x;
    private int y;
    protected AbstractEnemy(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getPositionX(){
        return x;
    }
    public int getPositionY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
