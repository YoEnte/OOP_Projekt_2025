package model;

/**
 * Die Klasse {@code Coordinates} repräsentiert eine zweidimensionale Koordinate mit X- und Y-Werten.
 * Sie wird verwendet, um Positionen auf einem Spielfeld oder in einer 2D-Umgebung zu beschreiben.
 */
public class Coordinates {

    // X-Koordinate
    private int xCoordinate;

    // Y-Koordinate
    private int yCoordinate;

    /**
     * Konstruktor zur Initialisierung der Koordinaten mit gegebenen Werten.
     *
     * @param x der X-Wert der Koordinate
     * @param y der Y-Wert der Koordinate
     */
    public Coordinates(int x, int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    /**
     * Kopierkonstruktor: Erstellt ein neues {@code Coordinates}-Objekt,
     * das eine Kopie des übergebenen Objekts ist.
     *
     * @param other das zu kopierende {@code Coordinates}-Objekt
     */
    public Coordinates(Coordinates other) {
        this.xCoordinate = other.xCoordinate;
        this.yCoordinate = other.yCoordinate;
    }

    /**
     * Gibt die aktuelle X-Koordinate zurück.
     *
     * @return der X-Wert
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Gibt die aktuelle Y-Koordinate zurück.
     *
     * @return der Y-Wert
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * Setzt die X-Koordinate auf den angegebenen Wert.
     *
     * @param xCoordinate der neue X-Wert
     */
    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Setzt die Y-Koordinate auf den angegebenen Wert.
     *
     * @param yCoordinate der neue Y-Wert
     */
    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
