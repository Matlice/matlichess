package it.matlice.matlichess.model;

import it.matlice.matlichess.exceptions.ChessboardLocationException;

import java.util.Objects;

public class Location {
    private int col;
    private int row;

    public Location(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public Location(String location) {
        if (location.length() != 2) throw new ChessboardLocationException();
        this.col = (location.charAt(0) & 0x5F)-0x41;
        this.row = Integer.parseInt(String.valueOf(location.charAt(1))) - 1;
        if (this.row < 0 || this.row > 7) throw new ChessboardLocationException();
        if (this.col < 0 || this.col > 7) throw new ChessboardLocationException();
    }

    public int[] coordinates(){
        return new int[] {this.row, this.col};
    }

    public int col() {
        return col;
    }

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

    /**
     * make sure same location instances have same hash
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    @Override
    public String toString() {
        return String.valueOf((char) (col + 0x41)) + String.valueOf(row+1);
    }
}
