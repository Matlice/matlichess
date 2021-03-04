package it.matlice.malichess.chessboard;

import it.matlice.malichess.ChessboardTest;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.pieces.*;

import static org.junit.jupiter.api.Assertions.*;

public class ToFEN {

    static ChessboardTest c = new ChessboardTest();

    public static void setupStartingPosition() {
        c = new ChessboardTest();

        c.setPiece(new Rook(PieceColor.WHITE), "A1");
        c.setPiece(new Knight(PieceColor.WHITE), "B1");
        c.setPiece(new Bishop(PieceColor.WHITE), "C1");
        c.setPiece(new Queen(PieceColor.WHITE), "D1");
        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setPiece(new Bishop(PieceColor.WHITE), "F1");
        c.setPiece(new Knight(PieceColor.WHITE), "G1");
        c.setPiece(new Rook(PieceColor.WHITE), "H1");

        c.setPiece(new Pawn(PieceColor.WHITE), "A2");
        c.setPiece(new Pawn(PieceColor.WHITE), "B2");
        c.setPiece(new Pawn(PieceColor.WHITE), "C2");
        c.setPiece(new Pawn(PieceColor.WHITE), "D2");
        c.setPiece(new Pawn(PieceColor.WHITE), "E2");
        c.setPiece(new Pawn(PieceColor.WHITE), "F2");
        c.setPiece(new Pawn(PieceColor.WHITE), "G2");
        c.setPiece(new Pawn(PieceColor.WHITE), "H2");

        c.setPiece(new Rook(PieceColor.BLACK), "A8");
        c.setPiece(new Knight(PieceColor.BLACK), "B8");
        c.setPiece(new Bishop(PieceColor.BLACK), "C8");
        c.setPiece(new Queen(PieceColor.BLACK), "D8");
        c.setKing(new King(PieceColor.BLACK), "E8");
        c.setPiece(new Bishop(PieceColor.BLACK), "F8");
        c.setPiece(new Knight(PieceColor.BLACK), "G8");
        c.setPiece(new Rook(PieceColor.BLACK), "H8");

        c.setPiece(new Pawn(PieceColor.BLACK), "A7");
        c.setPiece(new Pawn(PieceColor.BLACK), "B7");
        c.setPiece(new Pawn(PieceColor.BLACK), "C7");
        c.setPiece(new Pawn(PieceColor.BLACK), "D7");
        c.setPiece(new Pawn(PieceColor.BLACK), "E7");
        c.setPiece(new Pawn(PieceColor.BLACK), "F7");
        c.setPiece(new Pawn(PieceColor.BLACK), "G7");
        c.setPiece(new Pawn(PieceColor.BLACK), "H7");
    }

    @org.junit.jupiter.api.Test
    public void startingPositionFEN(){
        setupStartingPosition();
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", c.toFEN());
    }

    @org.junit.jupiter.api.Test
    public void firstMovesFEN(){
        setupStartingPosition();
        c.move(new Location("E2"), new Location("E4"));
        assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", c.toFEN());
        c.move(new Location("C7"), new Location("C5"));
        assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2", c.toFEN());
        c.move(new Location("G1"), new Location("F3"));
        assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2", c.toFEN());
    }

}