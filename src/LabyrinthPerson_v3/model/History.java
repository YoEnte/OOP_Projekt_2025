package model;

import java.util.ArrayList;

public class History {
    private static ArrayList<GameState> pastGameStates;

    public static ArrayList<GameState> getPastGameStates() {
        return pastGameStates;
    }
}
