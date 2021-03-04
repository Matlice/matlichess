package it.matlice.malichess.movements;

import it.matlice.malichess.ChessboardTest;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.model.pieces.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Promotion {

    @Test
    public void promotionToDefaultQueen() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.WHITE), "A7");
        c.move("A7", "A8");

        assertNull(c.getPieceAt(new Location("A7")));
        assertNotNull(c.getPieceAt(new Location("A8")));
        assertEquals(c.getPieceAt(new Location("A8")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("A8")).getColor(), PieceColor.WHITE);

    }

    @Test
    public void promotionToKnight() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.WHITE), "D7");

        c.setPromotion(PieceColor.WHITE, Knight.class);
        c.move("D7", "D8");

        assertNull(c.getPieceAt(new Location("D7")));
        assertNotNull(c.getPieceAt(new Location("D8")));
        assertEquals(c.getPieceAt(new Location("D8")).getName(), "Knight");
        assertEquals(c.getPieceAt(new Location("D8")).getColor(), PieceColor.WHITE);

    }

    @Test
    public void promotionToBishop() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.WHITE), "F7");

        c.setPromotion(PieceColor.WHITE, Bishop.class);
        c.move("F7", "F8");

        assertNull(c.getPieceAt(new Location("F7")));
        assertNotNull(c.getPieceAt(new Location("F8")));
        assertEquals(c.getPieceAt(new Location("F8")).getName(), "Bishop");
        assertEquals(c.getPieceAt(new Location("F8")).getColor(), PieceColor.WHITE);

    }

    @Test
    public void promotionToRook() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.WHITE), "B7");

        c.setPromotion(PieceColor.WHITE, Rook.class);
        c.move("B7", "B8");

        assertNull(c.getPieceAt(new Location("B7")));
        assertNotNull(c.getPieceAt(new Location("B8")));
        assertEquals(c.getPieceAt(new Location("B8")).getName(), "Rook");
        assertEquals(c.getPieceAt(new Location("B8")).getColor(), PieceColor.WHITE);

    }

    @Test
    public void promotionToQueen() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.WHITE), "G7");

        c.setPromotion(PieceColor.WHITE, Knight.class);

        c.setPromotion(PieceColor.WHITE, Queen.class);
        c.move("G7", "G8");

        assertNull(c.getPieceAt(new Location("G7")));
        assertNotNull(c.getPieceAt(new Location("G8")));
        assertEquals(c.getPieceAt(new Location("G8")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("G8")).getColor(), PieceColor.WHITE);

    }

    @Test
    public void promotionToDefaultQueenBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.BLACK), "A2");

        c.changeTurn();

        c.move("A2", "A1");

        assertNull(c.getPieceAt(new Location("A2")));
        assertNotNull(c.getPieceAt(new Location("A1")));
        assertEquals(c.getPieceAt(new Location("A1")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("A1")).getColor(), PieceColor.BLACK);

    }

    @Test
    public void promotionToKnightBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.BLACK), "D2");

        c.changeTurn();

        c.setPromotion(PieceColor.BLACK, Knight.class);
        c.move("D2", "D1");

        assertNull(c.getPieceAt(new Location("D2")));
        assertNotNull(c.getPieceAt(new Location("D1")));
        assertEquals(c.getPieceAt(new Location("D1")).getName(), "Knight");
        assertEquals(c.getPieceAt(new Location("D1")).getColor(), PieceColor.BLACK);

    }

    @Test
    public void promotionToBishopBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.BLACK), "F2");

        c.changeTurn();

        c.setPromotion(PieceColor.BLACK, Bishop.class);
        c.move("F2", "F1");

        assertNull(c.getPieceAt(new Location("F2")));
        assertNotNull(c.getPieceAt(new Location("F1")));
        assertEquals(c.getPieceAt(new Location("F1")).getName(), "Bishop");
        assertEquals(c.getPieceAt(new Location("F1")).getColor(), PieceColor.BLACK);

    }

    @Test
    public void promotionToRookBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.BLACK), "B2");

        c.changeTurn();

        c.setPromotion(PieceColor.BLACK, Rook.class);
        c.move("B2", "B1");

        assertNull(c.getPieceAt(new Location("B2")));
        assertNotNull(c.getPieceAt(new Location("B1")));
        assertEquals(c.getPieceAt(new Location("B1")).getName(), "Rook");
        assertEquals(c.getPieceAt(new Location("B1")).getColor(), PieceColor.BLACK);

    }

    @Test
    public void promotionToQueenBlack() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.BLACK), "G2");

        c.changeTurn();

        c.setPromotion(PieceColor.BLACK, Knight.class);
        c.setPromotion(PieceColor.BLACK, Queen.class);

        c.move("G2", "G1");

        assertNull(c.getPieceAt(new Location("G2")));
        assertNotNull(c.getPieceAt(new Location("G1")));
        assertEquals(c.getPieceAt(new Location("G1")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("G1")).getColor(), PieceColor.BLACK);

    }

    @Test
    public void promotionOpponentTest() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.WHITE), "D7");

        c.setPromotion(PieceColor.BLACK, Knight.class);
        c.move("D7", "D8");

        assertNull(c.getPieceAt(new Location("D7")));
        assertNotNull(c.getPieceAt(new Location("D8")));
        assertEquals(c.getPieceAt(new Location("D8")).getName(), "Queen");
        assertEquals(c.getPieceAt(new Location("D8")).getColor(), PieceColor.WHITE);

    }

    @Test
    public void captureToPromotion() {

        ChessboardTest c = new ChessboardTest();

        c.setKing(new King(PieceColor.WHITE), "E1");
        c.setKing(new King(PieceColor.BLACK), "E7");

        c.setPiece(new Pawn(PieceColor.WHITE), "D7");
        c.setPiece(new Rook(PieceColor.BLACK), "E8");

        c.setPromotion(PieceColor.WHITE, Bishop.class);
        c.move("D7", "E8");

        assertNull(c.getPieceAt(new Location("D7")));
        assertNotNull(c.getPieceAt(new Location("E8")));
        assertEquals(c.getPieceAt(new Location("E8")).getName(), "Bishop");
        assertEquals(c.getPieceAt(new Location("E8")).getColor(), PieceColor.WHITE);

    }

}