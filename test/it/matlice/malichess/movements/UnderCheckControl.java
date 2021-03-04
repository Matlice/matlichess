package it.matlice.malichess.movements;

import it.matlice.malichess.ChessboardTest;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.pieces.*;

import static org.junit.jupiter.api.Assertions.*;

public class UnderCheckControl {

    @org.junit.jupiter.api.Test
    public void directAttackTest(){
        King k1 = new King(PieceColor.WHITE);
        King k2 = new King(PieceColor.BLACK);

        Rook r = new Rook(PieceColor.WHITE);
        Bishop b = new Bishop(PieceColor.WHITE);
        Queen q = new Queen(PieceColor.WHITE);
        Knight kn = new Knight(PieceColor.WHITE);

        ChessboardTest c = new ChessboardTest();
        c.setKing(k1, new Location("E1"));
        c.setKing(k2, new Location("E8"));

        c.setPiece(r, new Location("H2"));
        c.setPiece(b, new Location("C4"));
        c.setPiece(q, new Location("C6"));
        c.setPiece(kn, new Location("G4"));

        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.move(new Location("C6"), new Location("D5"));
        assertFalse(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));

        c.changeTurn();
        c.move(new Location("C4"), new Location("B5"));
        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("B5"), new Location("C4"));

        c.changeTurn();
        c.move(new Location("G4"), new Location("F6"));
        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("F6"), new Location("G4"));

        c.changeTurn();
        c.move(new Location("H2"), new Location("H8"));
        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("H8"), new Location("H2"));

        c.changeTurn();
        c.move(new Location("H2"), new Location("E2"));
        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("E2"), new Location("H2"));

        c.changeTurn();
        c.move(new Location("D5"), new Location("A8"));
        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("A8"), new Location("D5"));

        Pawn p = new Pawn(PieceColor.WHITE);
        c.changeTurn();
        c.setPiece(p, new Location("F7"));
        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("E8"), new Location("F7")));
    }

    @org.junit.jupiter.api.Test
    public void discoveredAttackTest(){
        King k1 = new King(PieceColor.WHITE);
        King k2 = new King(PieceColor.BLACK);

        Bishop b1 = new Bishop(PieceColor.WHITE);
        Bishop b2 = new Bishop(PieceColor.WHITE);
        Queen q = new Queen(PieceColor.WHITE);
        Knight kn = new Knight(PieceColor.WHITE);

        ChessboardTest c = new ChessboardTest();
        c.setKing(k1, new Location("E1"));
        c.setKing(k2, new Location("E8"));

        c.setPiece(b1, new Location("E4"));
        c.setPiece(b2, new Location("B5"));
        c.setPiece(q, new Location("E3"));
        c.setPiece(kn, new Location("C6"));

        assertFalse(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.move(new Location("C6"), new Location("D4"));
        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("D4"), new Location("C6"));
        c.changeTurn();
        c.move(new Location("E4"), new Location("H1"));
        assertTrue(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("H1"), new Location("E4"));
        assertFalse(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
    }

    @org.junit.jupiter.api.Test
    public void IllegalMoveTest(){
        King k1 = new King(PieceColor.WHITE);
        King k2 = new King(PieceColor.BLACK);

        Bishop b = new Bishop(PieceColor.WHITE);
        Queen q = new Queen(PieceColor.WHITE);
        Rook r = new Rook(PieceColor.BLACK);

        ChessboardTest c = new ChessboardTest();
        c.setKing(k1, new Location("E1"));
        c.setKing(k2, new Location("E8"));

        c.setPiece(b, new Location("C4"));
        c.setPiece(q, new Location("E3"));
        c.setPiece(r, new Location("E7"));

        assertFalse(c.getOpponentKing(PieceColor.WHITE).isUnderCheck(c));
        c.changeTurn();
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("E7"), new Location("A7")));
        assertDoesNotThrow(() -> c.clone().move(new Location("E7"), new Location("E3")));
        c.changeTurn();
        c.move(new Location("C4"), new Location("B5"));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("E7"), new Location("E3")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("E8"), new Location("D7")));
        assertDoesNotThrow(() -> c.clone().move(new Location("E8"), new Location("F8")));

    }

}
