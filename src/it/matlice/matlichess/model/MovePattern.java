package it.matlice.matlichess.model;

import java.util.ArrayList;

public class MovePattern {

    private ArrayList<Location> locations = new ArrayList<>();
    private Chessboard chessboard;

    public MovePattern(Chessboard c) {
        this.chessboard = c;
    }

    private boolean piece_can_take(int row, int col, Color color) {
        //do not ask.
        if (row > 7 || row < 0 || col > 7 || col < 0) return true;
        if (chessboard.getPieceAt(col, row) != null) {
            if (chessboard.getPieceAt(col, row).getColor() != color)
                locations.add(new Location(col, row));
            return true;
        }
        locations.add(new Location(col, row));
        return false;
    }

    public Location[] get() {
        return (Location[]) locations.toArray();
    }

    public MovePattern addRow(Location l, Color mycolor) {
        var col = l.col();
        var row = l.row();
        for (int i = col + 1; i < 8; i++)
            if (piece_can_take(row, i, mycolor)) break;

        for (int i = col - 1; i >= 0; i--) {
            if (piece_can_take(row, i, mycolor)) break;
        }
        return this;
    }

    public MovePattern addColumn(Location l, Color mycolor) {
        var col = l.col();
        var row = l.row();
        for (int i = row + 1; i < 8; i++)
            if (piece_can_take(i, col, mycolor)) break;
        for (int i = row - 1; i >= 0; i--)
            if (piece_can_take(i, col, mycolor)) break;

        return this;
    }

    public MovePattern addDiagonals(Location l, Color mycolor) {
        var col = l.col();
        var row = l.row();

        for (int i = 0; i < 8; i++) {
            if (piece_can_take(row + i, col + i, mycolor)) break;
        }
        for (int i = 0; i < 8; i++) {
            if (piece_can_take(row + i, col - i, mycolor)) break;
        }
        for (int i = 0; i < 8; i++) {
            if (piece_can_take(row - i, col + i, mycolor)) break;
        }
        for (int i = 0; i < 8; i++) {
            if (piece_can_take(row - i, col - i, mycolor)) break;
        }
        return this;
    }

    public MovePattern addKnight(Location l, Color mycolor) {
        var col = l.col();
        var row = l.row();
        piece_can_take(row + 2, col + 1, mycolor);
        piece_can_take(row + 2, col - 1, mycolor);
        piece_can_take(row + 1, col + 2, mycolor);
        piece_can_take(row + 1, col - 2, mycolor);
        piece_can_take(row - 2, col + 1, mycolor);
        piece_can_take(row - 2, col - 1, mycolor);
        piece_can_take(row - 1, col + 2, mycolor);
        piece_can_take(row - 1, col - 2, mycolor);
        return this;
    }

    public MovePattern addKing(Location l, Color mycolor) {
        var col = l.col();
        var row = l.row();
        piece_can_take(row + 1, col + 1, mycolor);
        piece_can_take(row + 1, col + 0, mycolor);
        piece_can_take(row + 1, col - 1, mycolor);
        piece_can_take(row + 0, col + 1, mycolor);
        piece_can_take(row + 0, col - 1, mycolor);
        piece_can_take(row - 1, col + 1, mycolor);
        piece_can_take(row - 1, col + 0, mycolor);
        piece_can_take(row - 1, col - 1, mycolor);
        return this;
    }

//    public MovePattern addRing(int column, int row) {
//
//    }

}
