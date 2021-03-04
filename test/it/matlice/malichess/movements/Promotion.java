package it.matlice.malichess.movements;

import it.matlice.malichess.ChessboardTest;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.pieces.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Promotion {

    @Test
    public void promotionToDefaultQueen() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.WHITE), "A7");
        c.move("A7", "A8");

        assertNull(c.getPieceAt(new Location("A7")));
        assertNotNull(c.getPieceAt(new Location("A8")));
        assertEquals(c.getPieceAt(new Location("A8")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("A8")).getColor(), Color.WHITE);

    }

    @Test
    public void promotionToKnight() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.WHITE), "D7");

        c.setPromotion(Color.WHITE, Knight.class);
        c.move("D7", "D8");

        assertNull(c.getPieceAt(new Location("D7")));
        assertNotNull(c.getPieceAt(new Location("D8")));
        assertEquals(c.getPieceAt(new Location("D8")).getName(), "Knight");
        assertEquals(c.getPieceAt(new Location("D8")).getColor(), Color.WHITE);

    }

    @Test
    public void promotionToBishop() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.WHITE), "F7");

        c.setPromotion(Color.WHITE, Bishop.class);
        c.move("F7", "F8");

        assertNull(c.getPieceAt(new Location("F7")));
        assertNotNull(c.getPieceAt(new Location("F8")));
        assertEquals(c.getPieceAt(new Location("F8")).getName(), "Bishop");
        assertEquals(c.getPieceAt(new Location("F8")).getColor(), Color.WHITE);

    }

    @Test
    public void promotionToRook() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.WHITE), "B7");

        c.setPromotion(Color.WHITE, Rook.class);
        c.move("B7", "B8");

        assertNull(c.getPieceAt(new Location("B7")));
        assertNotNull(c.getPieceAt(new Location("B8")));
        assertEquals(c.getPieceAt(new Location("B8")).getName(), "Rook");
        assertEquals(c.getPieceAt(new Location("B8")).getColor(), Color.WHITE);

    }

    @Test
    public void promotionToQueen() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.WHITE), "G7");

        c.setPromotion(Color.WHITE, Knight.class);

        c.setPromotion(Color.WHITE, Queen.class);
        c.move("G7", "G8");

        assertNull(c.getPieceAt(new Location("G7")));
        assertNotNull(c.getPieceAt(new Location("G8")));
        assertEquals(c.getPieceAt(new Location("G8")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("G8")).getColor(), Color.WHITE);

    }

    @Test
    public void promotionToDefaultQueenBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.BLACK), "A2");

        c.changeTurn();
        
        c.move("A2", "A1");

        assertNull(c.getPieceAt(new Location("A2")));
        assertNotNull(c.getPieceAt(new Location("A1")));
        assertEquals(c.getPieceAt(new Location("A1")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("A1")).getColor(), Color.BLACK);

    }

    @Test
    public void promotionToKnightBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.BLACK), "D2");

        c.changeTurn();

        c.setPromotion(Color.BLACK, Knight.class);
        c.move("D2", "D1");

        assertNull(c.getPieceAt(new Location("D2")));
        assertNotNull(c.getPieceAt(new Location("D1")));
        assertEquals(c.getPieceAt(new Location("D1")).getName(), "Knight");
        assertEquals(c.getPieceAt(new Location("D1")).getColor(), Color.BLACK);

    }

    @Test
    public void promotionToBishopBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.BLACK), "F2");

        c.changeTurn();

        c.setPromotion(Color.BLACK, Bishop.class);
        c.move("F2", "F1");

        assertNull(c.getPieceAt(new Location("F2")));
        assertNotNull(c.getPieceAt(new Location("F1")));
        assertEquals(c.getPieceAt(new Location("F1")).getName(), "Bishop");
        assertEquals(c.getPieceAt(new Location("F1")).getColor(), Color.BLACK);

    }

    @Test
    public void promotionToRookBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.BLACK), "B2");

        c.changeTurn();

        c.setPromotion(Color.BLACK, Rook.class);
        c.move("B2", "B1");

        assertNull(c.getPieceAt(new Location("B2")));
        assertNotNull(c.getPieceAt(new Location("B1")));
        assertEquals(c.getPieceAt(new Location("B1")).getName(), "Rook");
        assertEquals(c.getPieceAt(new Location("B1")).getColor(), Color.BLACK);

    }

    @Test
    public void promotionToQueenBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.BLACK), "G2");

        c.changeTurn();

        c.setPromotion(Color.BLACK, Knight.class);
        c.setPromotion(Color.BLACK, Queen.class);

        c.move("G2", "G1");

        assertNull(c.getPieceAt(new Location("G2")));
        assertNotNull(c.getPieceAt(new Location("G1")));
        assertEquals(c.getPieceAt(new Location("G1")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("G1")).getColor(), Color.BLACK);

    }

    @Test
    public void promotionOpponentTest() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(Color.WHITE), "E1");
        c.setKing(new King(Color.BLACK), "E7");

        c.setPiece(new Pawn(Color.WHITE), "D7");

        c.setPromotion(Color.BLACK, Knight.class);
        c.move("D7", "D8");

        assertNull(c.getPieceAt(new Location("D7")));
        assertNotNull(c.getPieceAt(new Location("D8")));
        assertEquals(c.getPieceAt(new Location("D8")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("D8")).getColor(), Color.WHITE);

    }

}
