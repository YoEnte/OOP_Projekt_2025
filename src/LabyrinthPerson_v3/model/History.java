package model;

import java.util.ArrayList;

public class History {
    private static ArrayList<GameState> pastGameStates = new ArrayList<>();

    public static ArrayList<GameState> getPastGameStates() {
        return pastGameStates;
    }

    public static void addGameState(GameState gameToAdd){
        pastGameStates.add(gameToAdd);
    }

    public static GameState getGameStateByIndex(int i) throws IndexOutOfBoundsException {

        if (i < 0 || i >= pastGameStates.size()) {
            throw new IndexOutOfBoundsException("No GameStates available");
        } else {
            return pastGameStates.get(i);
        }
    }

    public static void removeGameStatesUntil(int turn){

        while (pastGameStates.size() > turn + 1) {
            pastGameStates.removeLast();
        }
    }
}
