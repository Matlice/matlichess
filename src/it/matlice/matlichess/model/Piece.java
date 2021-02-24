package it.matlice.matlichess.model;

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

    public abstract Location[] getAvailableMoves(Chessboard chessboard, Location myPosition); //todo
    public abstract boolean isPlaceAllowed(Chessboard chessboard, Location l, Location myPosition);
}
