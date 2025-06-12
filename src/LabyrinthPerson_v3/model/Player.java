package model;

public class Player {
    private int playerX;
    private int playerY;

    public Player(int x, int y){
        this.playerX = x;
        this.playerY = y;

    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
