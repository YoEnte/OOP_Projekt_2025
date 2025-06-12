package model.enemy;

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
}
