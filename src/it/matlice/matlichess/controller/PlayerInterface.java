package it.matlice.matlichess.controller;

import it.matlice.matlichess.GameState;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.PieceView;

import java.util.ArrayList;
import java.util.List;

public interface PlayerInterface {
    /**
     * This call will wait until the chosen move is available to be returned, giving the opportunity of being interrupted from another thread
     *
     * @return the selected move or null in case of interruption
     * @throws InterruptedException in case of interruption
     */
    List<Location> waitForUserMove() throws InterruptedException;

    /**
     * Sets the player color
     *
     * @param color color
     */
    void setColor(PieceColor color);

    /**
     * Sets the player position by passing an array of {@link PieceView}
     *
     * @param pieces
     */
    void setPosition(ArrayList<PieceView> pieces);

    /**
     * Tells the player the opponent has made the given move.
     *
     * @param from starting location
     * @param to   finish location
     */
    void setMove(Location from, Location to);

    /**
     * tells the player the current turn
     *
     * @param turn
     */
    void setTurn(PieceColor turn);

    /**
     * tells if the program has to ask the player if he wants a rematch
     * @return true if Is needed to ask the player if he wants the rematch, else false
     */
    default boolean isInteractive() {
        return true;
    }

    /**
     * allows another thread to interrupt the current asking move process
     */
    void interrupt();

    /**
     * Tells the player which is the current state of the game, fired for every turn.
     * <p>
     * if this function is called that means that is a duty of the player class to call {@link PlayerInterface#setState(GameState, boolean, Boolean)}
     * of the opponent referenced passing his choice.
     *
     * @param state    the {@link GameState}
     * @param generic  true if the player is an external watcher
     * @param opponent the reference to the opponent
     * @return
     */
    boolean setState(GameState state, boolean generic, PlayerInterface opponent);

    /**
     * @see PlayerInterface#setState(GameState, boolean, PlayerInterface)
     */
    boolean setState(GameState state, boolean generic, Boolean other_result);
}
