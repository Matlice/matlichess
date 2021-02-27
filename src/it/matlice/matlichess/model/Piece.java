package it.matlice.matlichess.model;

import it.matlice.matlichess.exceptions.InvalidMoveException;

import java.util.Objects;

/**
 * Abstract class to identify a general chess piece. It contains attributes which are the same for all the pieces
 * Every concrete piece which inherits this class will implements its unique way to move
 */
public abstract class Piece {

    private String name;
    private String shortName;
    private int value;
    private Color color;
    protected boolean has_moved = false;

    public Piece(String name, String shortName, int value, Color color) {
        this.name = name;
        this.shortName = shortName;
        this.value = value;
        this.color = color;
    }

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
     */
    public void hasBeenMoved(Chessboard c) {
        this.has_moved = true;
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
    public MoveList getAvailableMoves(Chessboard chessboard, Location myPosition){
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
        return getAvailableMoves(chessboard, myPosition).keySet().contains(destination);
    }

    /**
     * If you pass a generic move as destination (with only location set), this function returns the actual move to do,
     * with the action set if needed
     * @param chessboard Chessboard
     * @param destination the move location to go to
     * @param myPosition
     */
    public MoveAction getAction(Chessboard chessboard, Location destination, Location myPosition) {
        MoveList moves = getAvailableMoves(chessboard, myPosition);
        if (!moves.containsKey(destination)) throw new InvalidMoveException();
        return moves.get(destination);
    }

    public abstract Piece clone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return value == piece.value && has_moved == piece.has_moved && Objects.equals(name, piece.name) && Objects.equals(shortName, piece.shortName) && color == piece.color;
    }

}
