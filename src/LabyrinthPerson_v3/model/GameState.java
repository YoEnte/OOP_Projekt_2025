package model;


import model.enemy.AbstractEnemy;
import model.enemy.EasyEnemy;
import model.enemy.Enemies;
import model.rules.GameRuleLogic;
import model.rules.InvalidMoveException;
import view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameState {
    static Random random = new Random();
    private int turn;
    private Board board;
    private Player player;
    static ArrayList<AbstractEnemy> listOfEnemies = new ArrayList<>();
    private Enemies enemies;

    /** Set of views registered to be notified of world updates. */
    private final ArrayList<View> views = new ArrayList<>();

    public GameState(int turn, Board board, Player player, Enemies enemies){
        this.turn = turn;
        this.board = board;
        this.player = player;
        this.enemies = enemies;
        GameState.createEnemies();


        if (turn == 0) {
            History.addGameState(new GameState(this));
        }
    }

    public GameState(GameState other) {
        this.turn = other.turn;
        this.board = new Board(other.board);
        this.player = new Player(other.player);
        this.enemies = new Enemies(other.enemies);
    }

    public static ArrayList<AbstractEnemy> getListOfEnemies() {
        return listOfEnemies;
    }

    public static void createEnemies(){
        listOfEnemies.add(new EasyEnemy(random.nextInt(10)+1, random.nextInt(10)+1));
        listOfEnemies.add(new EasyEnemy(random.nextInt(10)+1, random.nextInt(10)+1));
        listOfEnemies.add(new EasyEnemy(random.nextInt(10)+1, random.nextInt(10)+1));




    }

    public Player getPlayer() {
        return player;
    }


    public Board getBoard() {
        return board;
    }

    public Enemies getEnemies() {
        return enemies;
    }



    /**
     * Sets the player's x position.
     *
     * @param playerX the player's x position.
     */
    public void setPlayerX(int playerX) {
        playerX = Math.max(0, playerX);
        playerX = Math.min(board.getWidth() - 1, playerX);
        this.player.setPlayerX(playerX);
    }

    /**
     * Sets the player's y position.
     *
     * @param playerY the player's y position.
     */
    public void setPlayerY(int playerY) {
        playerY = Math.max(0, playerY);
        playerY = Math.min(board.getHeight() - 1, playerY);
        this.player.setPlayerY(playerY);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Player Management

    /**
     * Moves the player along the given direction.
     *
     * @param direction where to move.
     */
    public void movePlayer(Direction direction) {
        History.removeGameStatesUntil(turn);

        try {
            GameRuleLogic.isValidToMove(this, direction, player.getPositionX(), player.getPositionY());
            // The direction tells us exactly how much we need to move along
            // every direction
            setPlayerX(player.getPositionX() + direction.deltaX);
            setPlayerY(player.getPositionY() + direction.deltaY);
            turn++;

            History.addGameState(new GameState(this));

            updateViews();


        } catch (InvalidMoveException e){
            //Pass
        }



    }

    ///////////////////////////////////////////////////////////////////////////
    // Timeline Management
    public void stepGameState(int stepSize) {

        try {
            GameState historyGamestate = History.getGameStateByIndex(turn + stepSize);

            turn += stepSize;
            board = new Board(historyGamestate.getBoard());
            player = new Player(historyGamestate.getPlayer());
            enemies = new Enemies(historyGamestate.getEnemies());

            updateViews();

        } catch (IndexOutOfBoundsException e) {
            // pass
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // View Management

    /**
     * Adds the given view of the world and updates it once. Once registered through
     * this method, the view will receive updates whenever the world changes.
     *
     * @param view the view to be registered.
     */
    public void registerView(View view) {
        views.add(view);
        view.update(this);
    }

    /**
     * Updates all views by calling their {@link View#update(GameState)} methods.
     */
    private void updateViews() {
        for (int i = 0; i < views.size(); i++) {
            views.get(i).update(this);
        }
    }

}
