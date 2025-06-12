package model;

import java.util.ArrayList;

public class History {
    private static ArrayList<GameState> pastGameStates = new ArrayList<>();

    public static ArrayList<GameState> getPastGameStates() {
        return pastGameStates;
    }

    public void addGameState(GameState gameToAdd){
        pastGameStates.add(gameToAdd);
    }

    public void removeGamStates(int count){
        for(int i = 0; i < count; i++){
            pastGameStates.removeLast();
        }
    }
}
