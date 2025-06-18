package model;


import controller.Controller;
import controller.Labyrinth;
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
    private Difficulty difficulty;
    private boolean closeWindow = false;

    /** Set of views registered to be notified of world updates. */
    private final ArrayList<View> views = new ArrayList<>();

    public GameState(int turn, Board board, Player player, Difficulty difficulty){
        this.turn = turn;
        this.board = board;
        this.player = player;
        this.gameEnd = false;
        this.difficulty = difficulty;

        this.createEnemies(this,difficulty);

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
            }
        }
    }

    public ArrayList<Enemy> getListOfEnemies() {
        return listOfEnemies;
    }

    private void createEnemies(GameState gameState, Difficulty difficulty) {
        int enemyCount = 3;
        int hardCount = switch (difficulty) {
            case EASY -> 1;
            case MEDIUM -> 2;
            case HARD -> 3;
            default -> 0;
        };

        boolean isValid = false;
        for(int i = 0; i < enemyCount; i++){
            while (!isValid){
                try {
                    int x = random.nextInt(gameState.board.getWidth()-1)+1;
                    int y = random.nextInt(gameState.board.getHeight()-1)+1;
                    isValidToSpawn(gameState, x, y );
                    if (i < hardCount) {
                        listOfEnemies.add(new NormalEnemy(x, y));
                    } else {
                        listOfEnemies.add(new EasyEnemy(x, y));
                    }
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
            GameRuleLogic.isValidToMove(this, direction, player.getPositionX(), player.getPositionY());
            player.setPlayerX(player.getPositionX() + direction.deltaX);
            player.setPlayerY(player.getPositionY() + direction.deltaY);
            turn++;

            updateViews();

            if(GameRuleLogic.playerInGoal(this, this.player.getPositionX(), this.player.getPositionY())){
                deleteBoard();
                Labyrinth.main(new String[0]);

            }
            if(GameRuleLogic.enemyCatchedPlayer(this)){
                deleteBoard();
                Labyrinth.main(new String[0]);
            }

            TimeUnit.MILLISECONDS.sleep(100);
            moveEnemies();
            updateViews();

            if(GameRuleLogic.enemyCatchedPlayer(this)){
                deleteBoard();
                Labyrinth.main(new String[0]);
            }

            History.addGameState(new GameState(this));

        } catch (InvalidMoveException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void moveEnemies() {
        for (Enemy enemy : listOfEnemies){
            if(enemy.getClass() == EasyEnemy.class){
                ((EasyEnemy)enemy).performMove(this);
            } else if(enemy.getClass() == NormalEnemy.class){
                ((NormalEnemy)enemy).performMove(this);
            }
        }
    }

    public void deleteAllWalls(){
        ArrayList<Coordinates> wallIndexArr = board.getIndexForFieldType(Field.WALL);
        for(Coordinates c : wallIndexArr){
            board = board.setFieldOnBoard(this.board, c, Field.PATH);
            updateViews();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public void deleteAllEnemies(){
        for(int i = 0; i < listOfEnemies.size();){
            this.listOfEnemies.removeLast();
            updateViews();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void deleteBoard(){
        this.gameEnd = true;
        player.setPlayerX(-1);
        player.setPlayerY(-1);

        deleteAllWalls();
        deleteAllEnemies();
        deleteAllSGElements();
        closeWindow = true;

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        updateViews();
    }
    public void deleteAllSGElements(){
        ArrayList<Coordinates> startIndexArr = board.getIndexForFieldType(Field.START);
        for(Coordinates c : startIndexArr){
            board = board.setFieldOnBoard(this.board, c, Field.PATH);
            updateViews();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ArrayList<Coordinates> goalIndexArr = board.getIndexForFieldType(Field.GOAL);
        for(Coordinates c : goalIndexArr){
            board = board.setFieldOnBoard(this.board, c, Field.PATH);
            updateViews();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
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

    public boolean isGameEnded() {
        return closeWindow;
    }

}
