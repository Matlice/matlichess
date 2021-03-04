package it.matlice.matlichess.model;

import it.matlice.matlichess.Location;

import java.util.HashMap;
import java.util.function.Supplier;

public class MoveList extends HashMap<Location, Supplier<Piece>> {

    public Supplier<Piece> put(String key, Supplier<Piece> action) {
        return super.put(new Location(key), action);
    }

    public Supplier<Piece> put(int col, int row, Supplier<Piece> action) {
        return super.put(new Location(col, row), action);
    }

    public Supplier<Piece> put(Location key) {
        return super.put(key, () -> null);
    }

    public Supplier<Piece> put(String key) {
        return super.put(new Location(key), () -> null);
    }

    public Supplier<Piece> put(int col, int row) {
        return super.put(new Location(col, row), () -> null);
    }

}
