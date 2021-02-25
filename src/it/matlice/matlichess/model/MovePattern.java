package it.matlice.matlichess.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MovePattern {

    private Set<Location> locations = new HashSet<>();
    private Chessboard chessboard;
    private Location l;
    private Color mycolor;

    public MovePattern(Chessboard c, Location l, Color mycolor) {
        this.chessboard = c;
        this.l = l;
        this.mycolor = mycolor;
    }

    private boolean piece_can_take(int row, int col, Color color) {
        //do not ask.
        if (row > 7 || row < 0 || col > 7 || col < 0) return true;
        if (chessboard.getPieceAt(col, row) != null) {
            if (chessboard.getPieceAt(col, row).getColor() == color.opponent())
                locations.add(new Location(col, row));
            return true;
        }
        locations.add(new Location(col, row));
        return false;
    }

    public Location[] get() {
        return locations.toArray(Location[]::new);
    }

    public MovePattern addRow() {
        var col = l.col();
        var row = l.row();
        for (int i = col + 1; i < 8; i++)
            if (piece_can_take(row, i, mycolor)) break;

        for (int i = col - 1; i >= 0; i--) {
            if (piece_can_take(row, i, mycolor)) break;
        }
        return this;
    }

    public MovePattern addColumn() {
        var col = l.col();
        var row = l.row();
        for (int i = row + 1; i < 8; i++)
            if (piece_can_take(i, col, mycolor)) break;
        for (int i = row - 1; i >= 0; i--)
            if (piece_can_take(i, col, mycolor)) break;

        return this;
    }

    public MovePattern addDiagonals() {
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

    public MovePattern addKnight() {
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

    public MovePattern addKing() {
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

    public MovePattern validate(){
        ArrayList<Location> locations = new ArrayList(Arrays.asList(this.locations.toArray()));
        for (int i = 0; i < locations.size(); i++) {
            var dest = locations.get(i);
            var next_move = chessboard.clone();
            next_move._make_move(l, dest);
            if(chessboard.getKing(this.mycolor).isUnderCheck(next_move, dest))
                this.locations.remove(dest);
        }
        return this;
    }
}
