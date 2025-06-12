package model;


import view.View;

import java.util.ArrayList;

public class GameState {
    private int turn;
    private Board board;
    private Player player;
    private Enemys enemys;

    /** Set of views registered to be notified of world updates. */
    private final ArrayList<View> views = new ArrayList<>();

    public GameState(int turn, Board board, Player player, Enemys enemys){
        this.turn = turn;
        this.board = board;
        this.player = player;
        this.enemys = enemys;
    }

    public Player getPlayer() {
        return player;
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

        updateViews();
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

        updateViews();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Player Management

    /**
     * Moves the player along the given direction.
     *
     * @param direction where to move.
     */
    public void movePlayer(Direction direction) {
        // The direction tells us exactly how much we need to move along
        // every direction
        setPlayerX(player.getPlayerX() + direction.deltaX);
        setPlayerY(player.getPlayerY() + direction.deltaY);
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
        view.update(this, this.board);
    }

    /**
     * Updates all views by calling their {@link View#update(Board)} methods.
     */
    private void updateViews() {
        for (int i = 0; i < views.size(); i++) {
            views.get(i).update(this, this.board);
        }
    }

}
