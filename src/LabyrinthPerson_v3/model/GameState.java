package model;



public class GameState {
    private int turn;
    private Board board;
    private Player player;
    private Enemys enemys;

    public GameState(int turn, Board board, Player player, Enemys enemys){
        this.turn = turn;
    }

    public void addGameStateList(GameState currentGame){
        History.getPastGameStates().add(currentGame);
    }

    public void prevTurn(){

    }
}
