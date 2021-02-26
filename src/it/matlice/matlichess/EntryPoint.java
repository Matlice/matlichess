package it.matlice.matlichess;

import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.pieces.*;


/**
 * EntryPoint of the program
 */
public class EntryPoint {
    public static void main(String[] args) {

        var k1 = new King(Color.WHITE);
        var k2 = new King(Color.BLACK);

        var p1 = new Pawn(Color.WHITE);
        var p2 = new Pawn(Color.BLACK);

        var c = new Chessboard();
        c.setKing(k1, "E1");
        c.setKing(k2, "E8");
        c.setPiece(p1, "E2");
        c.setPiece(p2, "D4");

        System.out.println(c);
        c.move("E2", "E4");
        System.out.println(c);
        c.move("D4", "E3");
        System.out.println(c);
    }
}
