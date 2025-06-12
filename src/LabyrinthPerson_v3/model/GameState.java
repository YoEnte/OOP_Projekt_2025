package model;


import model.enemyPackage.*;
import model.rules.GameRuleLogic;
import model.rules.InvalidMoveException;
import model.rules.InvalidSpawnException;
import view.View;

import java.util.ArrayList;
import java.util.Random;

import static model.rules.GameRuleLogic.isValidToSpawn;

public class GameState {
    static Random random = new Random();
    private int turn;
    private Board board;
    private Player player;
    private ArrayList<Enemy> listOfEnemies = new ArrayList<>();

    /** Set of views registered to be notified of world updates. */
    private final ArrayList<View> views = new ArrayList<>();

    public GameState(int turn, Board board, Player player){
        this.turn = turn;
        this.board = board;
        this.player = player;
        this.createEnemies(this,3);

        if (turn == 0) {
            History.addGameState(new GameState(this));
        }
    }

    public GameState(int turn, Board board, Player player, ArrayList<Enemy> listOfEnemies){
        this.turn = turn;
        this.board = board;
        this.player = player;
        this.listOfEnemies = listOfEnemies;

        if (turn == 0) {
            History.addGameState(new GameState(this));
        }
    }

    public GameState(GameState other) {
        this.turn = other.turn;
        this.board = new Board(other.board);
        this.player = new Player(other.player);

        this.listOfEnemies = new ArrayList<>();
        for (Enemy e : other.listOfEnemies) {
            if (e.getClass() == EasyEnemy.class) {
                this.listOfEnemies.add(new EasyEnemy((EasyEnemy) e));
            } else if (e.getClass() == NormalEnemy.class) {
                this.listOfEnemies.add(new NormalEnemy((NormalEnemy) e));
            } else if (e.getClass() == HardEnemy.class) {
                this.listOfEnemies.add(new HardEnemy((HardEnemy) e));
            }
        }
    }

    public ArrayList<Enemy> getListOfEnemies() {
        return listOfEnemies;
    }

    public void createEnemies(GameState gameState, int count){

        boolean isValid = false;
        for(int i = 0; i < count; i++){
            while (!isValid){
                try {
                    int x = random.nextInt(10)+1;
                    int y = random.nextInt(10)+1;
                    isValidToSpawn(gameState, x, y );
                    listOfEnemies.add(new EasyEnemy(x, y));
                    isValid = true;

                } catch (InvalidSpawnException e){
                    //Pass
                }
            }
            isValid = false;
        }

    }

    public Player getPlayer() {
        return player;
    }


    public Board getBoard() {
        return board;
    }

    public ArrayList<Enemy> getEnemies() {
        return listOfEnemies;
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
            updateViews();

            // TODO Add wait time
            for (Enemy enemy : listOfEnemies){
                if(enemy.getClass() == EasyEnemy.class){
                    ((EasyEnemy)enemy).performMove(this, (EasyEnemy)enemy);
                }

            }
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

            // TODO: Enemy copy method for less redundant code
            listOfEnemies = new ArrayList<>();
            for (Enemy e : historyGamestate.listOfEnemies) {
                if (e.getClass() == EasyEnemy.class) {
                    this.listOfEnemies.add(new EasyEnemy((EasyEnemy) e));
                } else if (e.getClass() == NormalEnemy.class) {
                    this.listOfEnemies.add(new NormalEnemy((NormalEnemy) e));
                } else if (e.getClass() == HardEnemy.class) {
                    this.listOfEnemies.add(new HardEnemy((HardEnemy) e));
                }
            }

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
