package model;

public class Coordinates {

    private int xCoordinate;
    private int yCoordinate;
    public Coordinates(int x, int y){

        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public Coordinates(Coordinates other) {
        this.xCoordinate = other.xCoordinate;
        this.yCoordinate = other.yCoordinate;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
