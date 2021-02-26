package it.matlice.malichess.chessboard;

import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.pieces.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ToFEN {

    static Chessboard c = new Chessboard();

    public static void setupStartingPosition() {
        c = new Chessboard();

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
    }

    @org.junit.jupiter.api.Test
    public void startingPositionFEN(){
        setupStartingPosition();
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", c.toFEN());
    }

    @org.junit.jupiter.api.Test
    public void firstMovesFEN(){
        setupStartingPosition();
        c._make_move(new Location("E2"), new Location("E4"));
        assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", c.toFEN());
        c._make_move(new Location("C7"), new Location("C5"));
        assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2", c.toFEN());
        c._make_move(new Location("G1"), new Location("F3"));
        assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2", c.toFEN());
    }

}