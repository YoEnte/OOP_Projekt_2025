package model;

/**
 * Repräsentiert den Spieler im Spiel mit einer x- und y-Position auf dem Spielfeld.
 */
public class Player {

    // X-Koordinate des Spielers
    private int playerX;

    // Y-Koordinate des Spielers
    private int playerY;

    /**
     * Konstruktor zum Erstellen eines Spielers mit spezifischer Position.
     *
     * @param x Die X-Position des Spielers.
     * @param y Die Y-Position des Spielers.
     */
    public Player(int x, int y){
        this.playerX = x;
        this.playerY = y;
    }

    /**
     * Kopierkonstruktor. Erstellt eine neue Spielerinstanz basierend auf einem bestehenden Spielerobjekt.
     *
     * @param other Das Spielerobjekt, das kopiert werden soll.
     */
    public Player(Player other) {
        this.playerX = other.playerX;
        this.playerY = other.playerY;
    }

    /**
     * Setzt die X-Position des Spielers neu.
     *
     * @param playerX Neue X-Position.
     */
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    /**
     * Setzt die Y-Position des Spielers neu.
     *
     * @param playerY Neue Y-Position.
     */
    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    /**
     * Gibt die aktuelle X-Position des Spielers zurück.
     *
     * @return X-Position des Spielers.
     */
    public int getPositionX() {
        return playerX;
    }

    /**
     * Gibt die aktuelle Y-Position des Spielers zurück.
     *
     * @return Y-Position des Spielers.
     */
    public int getPositionY() {
        return playerY;
    }
}
