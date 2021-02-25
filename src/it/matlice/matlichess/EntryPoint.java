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

        var tw1 = new Rook(Color.BLACK);
        var tw2 = new Rook(Color.BLACK);
        var tw3 = new Rook(Color.BLACK);
        var kw = new King(Color.BLACK);

        var c = new Chessboard();

        System.out.println(c);
    }
}
