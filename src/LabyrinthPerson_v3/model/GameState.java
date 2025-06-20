package model;

import controller.Controller;
import controller.Labyrinth;
import model.enemyPackage.*;
import model.rules.GameRuleLogic;
import model.rules.InvalidMoveException;
import model.rules.InvalidSpawnException;
import view.GraphicView;
import view.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static model.rules.GameRuleLogic.isValidToSpawn;

/**
 * Diese Klasse repräsentiert den gesamten Zustand des Spiels inklusive Spielfeld,
 * Spieler, Gegner, Schwierigkeitsgrad, Historie und registrierten Views.
 */
public class GameState {

    static Random random = new Random(); // Zufallszahlengenerator für Spawns
    private int turn; // Aktuelle Spielrunde
    private Board board; // Spielfeld
    private Player player; // Spieler
    private ArrayList<Enemy> listOfEnemies = new ArrayList<>(); // Liste aller Gegner im Spiel
    private boolean gameEnd; // Gibt an, ob das Spiel beendet wurde
    private Difficulty difficulty; // Aktueller Schwierigkeitsgrad
    private boolean closeWindow = false; // Status zum Schließen des Spielfensters
    private boolean win = false; // Gibt an, ob das Spiel beendet wurde

    /** Liste der Views, die bei Änderungen benachrichtigt werden */
    private final ArrayList<View> views = new ArrayList<>();

    /**
     * Konstruktor für neue Spielzustände (startet Spiel).
     * @param turn Welche Runde eines GameStates ist es
     * @param board Spielfeld
     * @param player Der Spieler (vom User gesteuert)
     * @param difficulty Schwierigkeit die das Spiel haben soll
     */
    public GameState(int turn, Board board, Player player, Difficulty difficulty){
        this.turn = turn;
        this.board = board;
        this.player = player;
        this.gameEnd = false;
        this.difficulty = difficulty;

        // Gegner basierend auf Schwierigkeitsgrad erzeugen
        this.createEnemies(this,difficulty);

        // Ursprünglichen Zustand in die Historie eintragen
        if (turn == 0) {
            History.addGameState(new GameState(this));
        }
    }

    /**
     * Kopierkonstruktor: Erstellt eine deep Copy des übergebenen Spielzustands.
     * @param other Spielstand der kopiert werden soll
     */
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

    /** Gibt die aktuelle Liste aller Gegner zurück. */
    public ArrayList<Enemy> getListOfEnemies() {
        return listOfEnemies;
    }

    /**
     * Erstellt eine Anzahl an "schweren" Gegnern abhängig vom Schwierigkeitsgrad.
     * Es sind immer 3 Gegner insgesamt. Je nach Schwierigkeit eine bestimmte Anzahl
     * an schweren Gegnern und dann aufgefüllt mit Random-Bots
     */
    private void createEnemies(GameState gameState, Difficulty difficulty) {
        int enemyCount = 3; // Anzahl der Gegner
        int hardCount = switch (difficulty) {
            case EASY -> 1;
            case MEDIUM -> 2;
            case HARD -> 3;
            default -> 0;
        };

        String[] enemyUrls = {
                "./src/LabyrinthPerson_v3/resources/enemy_gray",
                "./src/LabyrinthPerson_v3/resources/enemy_black",
                "./src/LabyrinthPerson_v3/resources/enemy_brown"
        };

        boolean isValid = false;
        for(int i = 0; i < enemyCount; i++){
            while (!isValid){

                try {
                    // Zufällige Position für Gegner
                    int x = random.nextInt(gameState.board.getWidth()-1)+1;
                    int y = random.nextInt(gameState.board.getHeight()-1)+1;

                    // Prüfen, ob Spawn erlaubt
                    isValidToSpawn(gameState, x, y );

                    // Gegnertyp je nach Index und Schwierigkeitsgrad
                    if (i < hardCount) {
                        listOfEnemies.add(new NormalEnemy(x, y, enemyUrls[i % enemyUrls.length]));
                    } else {
                        listOfEnemies.add(new EasyEnemy(x, y, enemyUrls[i % enemyUrls.length]));
                    }
                    isValid = true;

                } catch (InvalidSpawnException e){
                    // Bei ungültigem Spawn erneut versuchen
                }
            }
            isValid = false;
        }
    }

    /** Gibt den Spieler zurück. */
    public Player getPlayer() {
        return player;
    }

    /** Gibt das aktuelle Spielfeld zurück. */
    public Board getBoard() {
        return board;
    }

    /** Gibt die difficulty zurück */
    public Difficulty getDifficulty () {return difficulty; }

    /** Setzt die X-Koordinate des Spielers (innerhalb der Spielfeldgrenzen). */
    public void setPlayerX(int playerX) {
        playerX = Math.max(0, playerX);
        playerX = Math.min(board.getWidth(), playerX);
        this.player.setPlayerX(playerX);
    }

    /** Setzt die Y-Koordinate des Spielers (innerhalb der Spielfeldgrenzen). */
    public void setPlayerY(int playerY) {
        playerY = Math.max(0, playerY);
        playerY = Math.min(board.getHeight(), playerY);
        this.player.setPlayerY(playerY);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Spielersteuerung

    /**
     * Bewegt den Spieler in die angegebene Richtung, prüft Sieg/Niederlage,
     * bewegt die Gegner und speichert anschließend den Spielstand.
     *
     * @param direction Bewegungsrichtung
     */
    public void movePlayer(Direction direction) {
        History.removeGameStatesUntil(turn);

        try {
            // Bewegung prüfen, ob sie Valide ist -> nicht gegen Wände bspw.
            GameRuleLogic.isValidToMove(this, direction, player.getPositionX(), player.getPositionY());

            // Spielerposition ändern
            player.setPlayerX(player.getPositionX() + direction.deltaX);
            player.setPlayerY(player.getPositionY() + direction.deltaY);
            turn++;

            updateViews();

            // Ziel erreicht
            if(GameRuleLogic.playerInGoal(this, this.player.getPositionX(), this.player.getPositionY())){
                this.win = true;
                deleteBoard();
                Labyrinth.main(new String[0]);
            }

            // Vom Gegner gefangen
            if(GameRuleLogic.enemyCatchedPlayer(this)){
                deleteBoard();
                Labyrinth.main(new String[0]);
            }

            // Wartezeit zur Animation
            TimeUnit.MILLISECONDS.sleep(10);

            // Gegner bewegen sich
            moveEnemies();
            updateViews();

            // Nochmals prüfen, ob Spieler nun gefangen worden ist
            if(GameRuleLogic.enemyCatchedPlayer(this)){
                deleteBoard();
                Labyrinth.main(new String[0]);
            }

            // Spielstand sichern im Spielverlauf
            History.addGameState(new GameState(this));

        } catch (InvalidMoveException e){
            // Zeigt hauptsächlich an, wenn man gegen eine Wand läuft
            // System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /** Führt die Bewegungen aller Gegner aus. */
    public void moveEnemies() {
        for (Enemy enemy : listOfEnemies){
            if(enemy.getClass() == EasyEnemy.class){
                ((EasyEnemy)enemy).performMove(this);
            } else if(enemy.getClass() == NormalEnemy.class){
                ((NormalEnemy)enemy).performMove(this);
            }
        }
    }

    /** Entfernt alle Wände vom Spielfeld, mit visueller Aktualisierung. */
    public void deleteAllWalls(){
        ArrayList<Coordinates> wallIndexArr = board.getIndexForFieldType(Field.WALL);
        for(Coordinates c : wallIndexArr){
            board = board.setFieldOnBoard(this.board, c, Field.PATH);
            updateViews();
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /** Entfernt alle Gegner mit kurzer Pause zwischen jedem. */
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

    /** Entfernt alle Start- und Ziel-Felder vom Spielfeld. */
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

    /** Zeigt ein "W" für gewonnen oder ein "L" für verloren */
    public void winScreen(){

        for(int i = 0; i < 3; i++){

            board = new Board(this.board.getWidth(), this.board.getHeight(), win);
            updateViews();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            board = new Board(this.board.getWidth(), this.board.getHeight(), 69_420);
            updateViews();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Setzt das gesamte Spiel zurück, entfernt Wände, Start/Ziel, Gegner etc.
     * und markiert das Spiel als beendet.
     */
    public void deleteBoard(){
        this.gameEnd = true;
        player.setPlayerX(-1);
        player.setPlayerY(-1);

        deleteAllWalls();
        deleteAllEnemies();
        deleteAllSGElements();

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        winScreen();

        closeWindow = true;

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        updateViews();
    }


    ///////////////////////////////////////////////////////////////////////////
    // Zeitreise / Spielhistorie

    /**
     * Navigiert zum Spielzustand in der Historie relativ zur aktuellen Runde.
     *
     * @param stepSize Anzahl der Schritte vorwärts oder rückwärts.
     */
    public void stepGameState(int stepSize) {
        try {
            GameState historyGamestate = History.getGameStateByIndex(turn + stepSize);

            turn += stepSize;
            board = new Board(historyGamestate.getBoard());
            player = new Player(historyGamestate.getPlayer());

            // Gegner kopieren
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
            // Kein gültiger Spielstand vorhanden
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // View-Management

    /**
     * Registriert eine View zur automatischen Aktualisierung bei Änderungen.
     *
     * @param view Die View, die registriert wird.
     */
    public void registerView(View view) {
        views.add(view);
        view.update(this);
    }

    /**
     * Aktualisiert alle registrierten Views mit dem aktuellen Spielzustand.
     */
    public void updateViews() {
        for (int i = 0; i < views.size(); i++) {
            views.get(i).update(this);
        }
    }

    /**
     * Gibt an, ob das Spiel beendet ist (Fenster schließen).
     */
    public boolean isGameEnded() {
        return closeWindow;
    }

    /**
     * Gibt an, ob das Spiel an sich zu Ende ist (Gewonnen / Verloren).
     *
     * @return Spiel zu Ende
     */
    public boolean getGameEnd() {
        return gameEnd;
    }
}
