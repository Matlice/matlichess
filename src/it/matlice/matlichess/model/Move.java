package it.matlice.matlichess.model;

import java.util.Objects;

public class Move {

    private Location location;
    private MoveAction action;

    public Move(Location move, MoveAction action) {
        this.location = move;
        this.action = action;
    }

    public Move(String move, MoveAction action) {
        this(new Location(move), action);
    }

    public Move(int col, int row, MoveAction action) {
        this(new Location(col, row), action);
    }

    public Move(Location location) {
        this(location, () -> null);
    }

    public Move(String location) {
        this(location, () -> null);
    }

    public Move(int col, int row) {
        this(col, row, () -> null);
    }

    public Location getLocation() {
        return location;
    }

    public MoveAction getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return location.equals(move.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
