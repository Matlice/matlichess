package it.matlice.malichess.movements;

import it.matlice.malichess.ChessboardTest;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.pieces.*;

import static org.junit.jupiter.api.Assertions.*;

public class NormalMovement {

    @org.junit.jupiter.api.Test
    public void rookTest(){
        King k1 = new King(PieceColor.WHITE);
        King k2 = new King(PieceColor.BLACK);

        Rook r1 = new Rook(PieceColor.WHITE);
        Rook r2 = new Rook(PieceColor.WHITE);
        Rook r5 = new Rook(PieceColor.BLACK);
        Rook r6 = new Rook(PieceColor.BLACK);

        ChessboardTest c = new ChessboardTest();
        c.setKing(k1, new Location("C7"));
        c.setKing(k2, new Location("G7"));

        c.setPiece(r1, new Location("D1"));
        c.setPiece(r2, new Location("B4"));
        c.setPiece(r5, new Location("A1"));
        c.setPiece(r6, new Location("D4"));

        c.changeTurn();
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("A1"), new Location("H1")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("A1"), new Location("E1")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("D4"), new Location("A4")));

        assertEquals(r2, c.clone().move(new Location("D4"), new Location("B4")));
        assertEquals(r1, c.clone().move(new Location("D4"), new Location("D1")));
    }


    @org.junit.jupiter.api.Test
    public void bishopTest(){
        King k1 = new King(PieceColor.WHITE);
        King k2 = new King(PieceColor.BLACK);

        Bishop b1 = new Bishop(PieceColor.WHITE);
        Bishop b2 = new Bishop(PieceColor.WHITE);
        Bishop b3 = new Bishop(PieceColor.BLACK);
        Pawn p1 = new Pawn(PieceColor.BLACK);

        ChessboardTest c = new ChessboardTest();
        c.setKing(k1, new Location("C7"));
        c.setKing(k2, new Location("G7"));

        c.setPiece(b1, "H1");
        c.setPiece(b2, "C2");
        c.setPiece(b3, new Location("D5"));
        c.setPiece(p1, "F5");

        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("H1"), new Location("C6")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("H1"), new Location("H1")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("H1"), new Location("F1")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("C2"), new Location("H7")));

        c.changeTurn();
        assertEquals(b1, c.clone().move(new Location("D5"), new Location("H1")));
        c.changeTurn();
        assertEquals(b3, c.clone().move(new Location("H1"), new Location("D5")));
        assertEquals(p1, c.clone().move(new Location("C2"), new Location("F5")));

        c.move(new Location("C2"), new Location("E4"));
        assertEquals(c.getPieceAt(new Location("E4")), b2);
    }

    @org.junit.jupiter.api.Test
    public void queenTest(){
        King k1 = new King(PieceColor.WHITE);
        King k2 = new King(PieceColor.BLACK);

        Queen q1 = new Queen(PieceColor.WHITE);
        Queen q2 = new Queen(PieceColor.WHITE);
        Queen q3 = new Queen(PieceColor.BLACK);

        ChessboardTest c = new ChessboardTest();
        c.setKing(k1, new Location("C7"));
        c.setKing(k2, new Location("G7"));

        c.setPiece(q1, "D5");
        c.setPiece(q2, "F5");
        c.setPiece(q3, "B3");

        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("D5"), new Location("F5")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("D5"), new Location("A2")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("D5"), new Location("C3")));
        assertEquals(q3, c.clone().move(new Location("D5"), new Location("B3")));

        c.changeTurn();
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("B3"), new Location("G8")));
        assertEquals(q1, c.clone().move(new Location("B3"), new Location("D5")));
    }

    @org.junit.jupiter.api.Test
    public void KnightTest(){
        King k1 = new King(PieceColor.WHITE);
        King k2 = new King(PieceColor.BLACK);

        Knight kn1 = new Knight(PieceColor.WHITE);
        Knight kn2 = new Knight(PieceColor.WHITE);
        Knight kn3 = new Knight(PieceColor.BLACK);

        ChessboardTest c = new ChessboardTest();
        c.setKing(k1, new Location("C7"));
        c.setKing(k2, new Location("G7"));

        c.setPiece(kn1, "D4");
        c.setPiece(kn2, "F3");
        c.setPiece(kn3, "B3");

        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("D4"), new Location("F3")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("D4"), new Location("H4")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("F3"), new Location("D4")));

        assertDoesNotThrow(() -> c.clone().move(new Location("D4"), new Location("F5")));
        assertDoesNotThrow(() -> c.clone().move(new Location("D4"), new Location("E6")));
        assertDoesNotThrow(() -> c.clone().move(new Location("D4"), new Location("C6")));
        assertDoesNotThrow(() -> c.clone().move(new Location("D4"), new Location("B5")));
        assertDoesNotThrow(() -> c.clone().move(new Location("D4"), new Location("B3")));
        assertDoesNotThrow(() -> c.clone().move(new Location("D4"), new Location("C2")));
        assertDoesNotThrow(() -> c.clone().move(new Location("D4"), new Location("E2")));

        assertEquals(kn3, c.clone().move(new Location("D4"), new Location("B3")));
        c.changeTurn();
        assertEquals(kn1, c.clone().move(new Location("B3"), new Location("D4")));
    }

}
