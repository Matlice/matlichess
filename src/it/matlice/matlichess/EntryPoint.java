package it.matlice.matlichess;

import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.pieces.King;
import it.matlice.matlichess.model.pieces.Rook;


/**
 * EntryPoint of the program
 */
public class EntryPoint {
    public static void main(String[] args) {
        var k = new King(Color.BLACK);
        var t = new Rook(Color.WHITE);
        var t2 = new Rook(Color.WHITE);

        var c = new Chessboard();
        c.setKing(k, new Location("A1"));
        c.setPiece(t, new Location("D2"));
        c.setPiece(t2, new Location("H2"));

        var a = k.getAvailableMoves(c, new Location("A1"));

        System.out.println(c);
    }
}
