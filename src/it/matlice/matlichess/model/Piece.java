package it.matlice.matlichess.model;

import java.util.Arrays;

public abstract class Piece {

    private String name;
    private String shortName;
    private int value;
    private Color color;
    private boolean has_moved = false;

    public boolean hasMoved() {
        return has_moved;
    }

    public void hasBeenMoved() {
        this.has_moved = true;
    }

    public Piece(String name, String shortName, int value, Color color) {
        this.name = name;
        this.shortName = shortName;
        this.value = value;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public String getShortName() {
        return shortName;
    }

    public int getValue() {
        return value;
    }

    protected abstract MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition);

    public Location[] getAvailableMoves(Chessboard chessboard, Location myPosition){
        return this.unvalidated_move_pattern(chessboard, myPosition).validate().get();
    }

    public boolean isPlaceAllowed(Chessboard chessboard, Location l, Location myPosition){
        return Arrays.stream(getAvailableMoves(chessboard, myPosition)).filter((Location e) -> e == l).toArray().length == 1;
    }

    public boolean canCapture(Chessboard chessboard, Location capture, Location myposition){ ;
        return Arrays.stream(unvalidated_move_pattern(chessboard, myposition).get()).filter((Location e) -> e.equals(capture)).toArray().length == 1 && chessboard.getPieceAt(capture) != null;
    }
}
