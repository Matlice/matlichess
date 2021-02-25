package it.matlice.matlichess;

import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.pieces.Bishop;
import it.matlice.matlichess.model.pieces.King;
import it.matlice.matlichess.model.pieces.Pawn;
import it.matlice.matlichess.model.pieces.Rook;


/**
 * EntryPoint of the program
 */
public class EntryPoint {
    public static void main(String[] args) {
        var k = new King(Color.BLACK);
        var k2 = new King(Color.WHITE);

        var t = new Rook(Color.WHITE);
        var t2 = new Rook(Color.WHITE);

        var b1 = new Bishop(Color.BLACK);

        var p1 = new Pawn(Color.WHITE);
        var p2 = new Pawn(Color.WHITE);
        var p3 = new Pawn(Color.WHITE);

        var c = new Chessboard();
        c.setKing(k, new Location("A1"));
        c.setKing(k2, new Location("F8"));
        c.setPiece(t, new Location("D2"));
        c.setPiece(t2, new Location("H2"));
        c.setPiece(b1, new Location("C3"));
        c.setPiece(p1, new Location("C2"));
        c.setPiece(p2, new Location("D8"));
        c.setPiece(p3, new Location("E4"));

        var asdasd = p1.getAvailableMoves(c, new Location("C2"));

        var a = k.getAvailableMoves(c, new Location("A1"));

        System.out.println(c);
    }
}
