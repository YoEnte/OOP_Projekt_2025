package model;

import java.util.ArrayList;

/**
 * Diese Klasse speichert den Verlauf des Spiels in Form einer Liste von GameStates.
 * Sie stellt Methoden zum Hinzufügen, Abrufen und Zurücksetzen von Spielständen bereit.
 */
public class History {

    // Statische Liste, in der vergangene Spielstände gespeichert werden
    private static ArrayList<GameState> pastGameStates = new ArrayList<>();

    /**
     * Gibt die Liste aller vergangenen Spielstände zurück.
     *
     * @return Eine Liste von GameStates, die die Spielhistorie darstellen.
     */
    public static ArrayList<GameState> getPastGameStates() {
        return pastGameStates;
    }

    /**
     * Fügt einen neuen Spielstand der Historie hinzu.
     *
     * @param gameToAdd Der hinzuzufügende Spielstand.
     */
    public static void addGameState(GameState gameToAdd){
        pastGameStates.add(gameToAdd);
    }

    /**
     * Gibt einen Spielstand anhand seines Index zurück.
     *
     * @param i Der Index des gewünschten Spielstandes.
     * @return Der GameState am gegebenen Index.
     * @throws IndexOutOfBoundsException Wenn der Index außerhalb der gültigen Grenzen liegt.
     */
    public static GameState getGameStateByIndex(int i) throws IndexOutOfBoundsException {
        // Überprüfe, ob der Index gültig ist
        if (i < 0 || i >= pastGameStates.size()) {
            throw new IndexOutOfBoundsException("No GameStates available");
        } else {
            return pastGameStates.get(i);
        }
    }

    /**
     * Entfernt alle Spielstände nach einer bestimmten Spielrunde.
     *
     * @param turn Die Spielrunde, bis zu der die Historie beibehalten werden soll.
     */
    public static void removeGameStatesUntil(int turn){
        // Entfernt alle Spielstände nach dem gegebenen Zug (Index)
        while (pastGameStates.size() > turn + 1) {
            pastGameStates.removeLast(); // Entfernt das letzte Element der Liste
        }
    }
}

