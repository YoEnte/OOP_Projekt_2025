package model;


import model.enemyPackage.*;
import model.rules.GameRuleLogic;
import model.rules.InvalidMoveException;
import model.rules.InvalidSpawnException;
import view.GraphicView;
import view.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static model.rules.GameRuleLogic.isValidToSpawn;

public class GameState {
    static Random random = new Random();
    private int turn;
    private Board board;
    private Player player;
    private ArrayList<Enemy> listOfEnemies = new ArrayList<>();
    private boolean gameEnd;

    /** Set of views registered to be notified of world updates. */
    private final ArrayList<View> views = new ArrayList<>();

    public GameState(int turn, Board board, Player player){
        this.turn = turn;
        this.board = board;
        this.player = player;
        this.gameEnd = false;

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
                    int x = random.nextInt(gameState.board.getWidth()-1)+1;
                    int y = random.nextInt(gameState.board.getHeight()-1)+1;
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
        playerX = Math.min(board.getWidth(), playerX);
        this.player.setPlayerX(playerX);
    }

    /**
     * Sets the player's y position.
     *
     * @param playerY the player's y position.
     */
    public void setPlayerY(int playerY) {
        playerY = Math.max(0, playerY);
        playerY = Math.min(board.getHeight(), playerY);
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
            //(this.player.getPositionX());
            //(this.player.getPositionY());
            GameRuleLogic.isValidToMove(this, direction, player.getPositionX(), player.getPositionY());
            // The direction tells us exactly how much we need to move along
            // every direction


            player.setPlayerX(player.getPositionX() + direction.deltaX);
            player.setPlayerY(player.getPositionY() + direction.deltaY);
            //(this.player.getPositionX());
            //(this.player.getPositionY());

            turn++;

            //updateViews();

            TimeUnit.MILLISECONDS.sleep(100);
            moveEnemies();
            updateViews();
            if(GameRuleLogic.playerInGoal(this, this.player.getPositionX(), this.player.getPositionY())){
                this.gameEnd = true;
                vanishWalls();
                updateViews();
            }

            History.addGameState(new GameState(this));

        } catch (InvalidMoveException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(player.getPositionX());
        System.out.println(player.getPositionY());

    }
    public void moveEnemies() {
        for (Enemy enemy : listOfEnemies){
            if(enemy.getClass() == EasyEnemy.class){
                ((EasyEnemy)enemy).performMove(this, (EasyEnemy)enemy);
            }
        }
    }

    public void vanishWalls(){
        ArrayList<Coordinates> wallIndexArr = board.getIndexForFieldType(Field.WALL);
        for(Coordinates c : wallIndexArr){
            board = board.setFieldOnBoard(this.board, c, Field.PATH);
            updateViews();
            try {
                TimeUnit.MILLISECONDS.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
    public void updateViews() {
        for (int i = 0; i < views.size(); i++) {
            views.get(i).update(this);
        }
    }

}
