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
        var tw1 = new Rook(Color.BLACK);
        var tw2 = new Rook(Color.BLACK);
        var tw3 = new Rook(Color.BLACK);
        var kw = new King(Color.BLACK);

        var c = new Chessboard();
        c.setKing(kw, "E8");
        c.setPiece(tw1, "A8");
        c.setPiece(tw2, "H8");
        c.setPiece(tw3, "C8");

        c.move("C8", "C6");
        System.out.println(c);

        c.move("E8", "G8");
        System.out.println(c);

        c.move("G8", "H8");
        System.out.println(c);
    }
}
