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

        Chessboard c = new Chessboard();

        c.setPiece(new Rook(Color.WHITE), "A1");
        c.setPiece(new Knight(Color.WHITE), "B1");
        c.setPiece(new Bishop(Color.WHITE), "C1");
        c.setPiece(new Queen(Color.WHITE), "D1");
        c.setKing(new King(Color.WHITE), "E1");
        c.setPiece(new Bishop(Color.WHITE), "F1");
        c.setPiece(new Knight(Color.WHITE), "G1");
        c.setPiece(new Rook(Color.WHITE), "H1");

        c.setPiece(new Pawn(Color.WHITE), "A2");
        c.setPiece(new Pawn(Color.WHITE), "B2");
        c.setPiece(new Pawn(Color.WHITE), "C2");
        c.setPiece(new Pawn(Color.WHITE), "D2");
        c.setPiece(new Pawn(Color.WHITE), "E2");
        c.setPiece(new Pawn(Color.WHITE), "F2");
        c.setPiece(new Pawn(Color.WHITE), "G2");
        c.setPiece(new Pawn(Color.WHITE), "H2");

        c.setPiece(new Rook(Color.BLACK), "A8");
        c.setPiece(new Knight(Color.BLACK), "B8");
        c.setPiece(new Bishop(Color.BLACK), "C8");
        c.setPiece(new Queen(Color.BLACK), "D8");
        c.setKing(new King(Color.BLACK), "E8");
        c.setPiece(new Bishop(Color.BLACK), "F8");
        c.setPiece(new Knight(Color.BLACK), "G8");
        c.setPiece(new Rook(Color.BLACK), "H8");

        c.setPiece(new Pawn(Color.BLACK), "A7");
        c.setPiece(new Pawn(Color.BLACK), "B7");
        c.setPiece(new Pawn(Color.BLACK), "C7");
        c.setPiece(new Pawn(Color.BLACK), "D7");
        c.setPiece(new Pawn(Color.BLACK), "E7");
        c.setPiece(new Pawn(Color.BLACK), "F7");
        c.setPiece(new Pawn(Color.BLACK), "G7");
        c.setPiece(new Pawn(Color.BLACK), "H7");

        System.out.println(c);
        System.out.println(c.toFEN());

    }
}
