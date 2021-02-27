package it.matlice.matlichess.model;

import java.util.HashMap;

public class MoveList extends HashMap<Location, MoveAction> {

    public MoveAction put(String key, MoveAction action) {
        return super.put(new Location(key), action);
    }

    public MoveAction put(int col, int row, MoveAction action) {
        return super.put(new Location(col, row), action);
    }

    public MoveAction put(Location key) {
        return super.put(key, () -> null);
    }

    public MoveAction put(String key) {
        return super.put(new Location(key), () -> null);
    }

    public MoveAction put(int col, int row) {
        return super.put(new Location(col, row), () -> null);
    }

}
