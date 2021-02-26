package it.matlice.matlichess.model;

import java.util.Set;

/**
 * Abstract class to identify a general chess piece. It contains attributes which are the same for all the pieces
 * Every concrete piece which inherits this class will implements its unique way to move
 */
public abstract class Piece {

    private String name;
    private String shortName;
    private int value;
    private Color color;
    private boolean has_moved = false;

    /**
     * Checks if the piece has already made a move
     * @return a boolean to describe if the piece has moved
     */
    public boolean hasMoved() {
        return has_moved;
    }
    public void _reset_movement(boolean v) {
        has_moved = v;
    }

    /**
     * Notifies that a piece has made its first move
     * @param from Where the piece started
     * @param to Where the piece has been moved to
     * @return it will be used to allow the en Passant Move, returning the pawn that has just moved by two squares. for any other use, returns null
     */
    public Piece hasBeenMoved(Chessboard c, Location from, Location to) {
        this.has_moved = true;
        return null;
    }

    public Piece(String name, String shortName, int value, Color color) {
        this.name = name;
        this.shortName = shortName;
        this.value = value;
        this.color = color;
    }

    /**
     * Getter for the name of the piece
     * @return the name of the piece
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the color of the piece
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for the short name of the piece
     * @return the short name of the piece
     */
    public String getShortName() {
        return getColor() == Color.WHITE ? shortName.toUpperCase() : shortName.toLowerCase();
    }

    /**
     * Getter for the value of the piece
     * @return the value of the piece
     */
    public int getValue() {
        return value;
    }

    /**
     * Describes the Locations reachable by a chess Piece without checking if the king is under attack
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the Position of the Piece
     * @return the MovePattern of the piece without checking if the king is under attack
     */
    public abstract MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition);

    /**
     * Describes the Locations reachable by a chess Piece
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the Position of the Piece
     * @return the MovePattern of the piece
     */
    public Set<Location> getAvailableMoves(Chessboard chessboard, Location myPosition){
        return this.unvalidated_move_pattern(chessboard, myPosition).validate().get();
    }

    /**
     * Returns if the piece can move to a certain Location
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param destination the final Position
     * @param myPosition the Position of the Piece
     * @return if the piece can move to a certain Location
     */
    public boolean isMoveAllowed(Chessboard chessboard, Location destination, Location myPosition){
        return getAvailableMoves(chessboard, myPosition).contains(destination);
    }

}
