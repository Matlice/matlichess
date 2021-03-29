package it.matlice.matlichess.model;

import it.matlice.matlichess.Location;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Class to manage the possible Locations that a piece can reach
 * You can add with a lambda function an action that will be solved if the piece moves in that square.
 * An example could be the enPassant Move, where you need to remove a piece that is placed in a different Location
 */
public class MoveList extends HashMap<Location, Supplier<Piece>> {

    /**
     * Utility method that converts a String to a Location and then puts it inside the map
     *
     * @param key    the String representing the location
     * @param action the action associated with the move
     * @return the previously value associated with the key
     */
    public Supplier<Piece> put(String key, Supplier<Piece> action) {
        return super.put(new Location(key), action);
    }

    /**
     * Utility method that converts a pair of ints to a Location and then puts it inside the map
     *
     * @param col    the int representing the column
     * @param row    the int representing the column
     * @param action the action associated with the move
     * @return the previously value associated with the key
     */
    public Supplier<Piece> put(int col, int row, Supplier<Piece> action) {
        return super.put(new Location(col, row), action);
    }

    /**
     * Puts a location inside the map mapping it to an empty lambda, no action is associated with the move
     *
     * @param key the location to put
     * @return the previously value associated with the key
     */
    public Supplier<Piece> put(Location key) {
        return super.put(key, () -> null);
    }

    /**
     * Puts a location inside the map mapping it to an empty lambda, no action is associated with the move
     *
     * @param key the string representation of the location
     * @return the previously value associated with the key
     */
    public Supplier<Piece> put(String key) {
        return super.put(new Location(key), () -> null);
    }

    /**
     * Puts a location inside the map mapping it to an empty lambda, no action is associated with the move
     *
     * @param col the int representing the column
     * @param row the int representing the column
     * @return the previously value associated with the key
     */
    public Supplier<Piece> put(int col, int row) {
        return super.put(new Location(col, row), () -> null);
    }

}
