package it.matlice.matlichess.model;

import it.matlice.matlichess.exceptions.ChessboardLocationException;

import java.util.Objects;

/**
 * Class to describe a particular coordinate on a chessboard
 */
public class Location {
    private int col;
    private int row;

    public Location(int col, int row) {
        this.col = col;
        this.row = row;
    }

    /**
     * Constructor which parses the official notation of the Location
     * example: a1 -> column=0, row=0
     * example: d6 -> column=3, row=5
     * @param location String containing the official notation
     */
    public Location(String location) {
        location = location.toUpperCase();
        if (location.length() != 2) throw new ChessboardLocationException();
        this.col = (location.charAt(0) & 0x5F)-0x41;
        this.row = Integer.parseInt(String.valueOf(location.charAt(1))) - 1;
        if (this.row < 0 || this.row > 7) throw new ChessboardLocationException();
        if (this.col < 0 || this.col > 7) throw new ChessboardLocationException();
    }

    /**
     * Getter for coordinates, row first, then column
     * @return the coordinates
     */
    public int[] coordinates(){
        return new int[] {this.row, this.col};
    }

    /**
     * Getter for the column
     * @return the column
     */
    public int col() {
        return col;
    }

    /**
     * Getter for the row
     * @return the row
     */
    public int row() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return row == location.row &&
                col == location.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    @Override
    public String toString() {
        return String.valueOf((char) (col + 0x41)) + String.valueOf(row+1);
    }
}
