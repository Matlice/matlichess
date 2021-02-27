package it.matlice.malichess.movements;

import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.Move;
import it.matlice.matlichess.model.pieces.*;

import static org.junit.jupiter.api.Assertions.*;

public class UnderCheckControl {

    @org.junit.jupiter.api.Test
    public void directAttackTest(){
        King k1 = new King(Color.WHITE);
        King k2 = new King(Color.BLACK);

        Rook r = new Rook(Color.WHITE);
        Bishop b = new Bishop(Color.WHITE);
        Queen q = new Queen(Color.WHITE);
        Knight kn = new Knight(Color.WHITE);

        Chessboard c = new Chessboard();
        c.setKing(k1, new Location("E1"));
        c.setKing(k2, new Location("E8"));

        c.setPiece(r, new Location("H2"));
        c.setPiece(b, new Location("C4"));
        c.setPiece(q, new Location("C6"));
        c.setPiece(kn, new Location("G4"));

        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.move(new Location("C6"), new Move("D5"));
        assertFalse(c.getOpponentKing(Color.WHITE).isUnderCheck(c));

        c.changeTurn();
        c.move(new Location("C4"), new Move("B5"));
        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("B5"), new Move("C4"));

        c.changeTurn();
        c.move(new Location("G4"), new Move("F6"));
        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("F6"), new Move("G4"));

        c.changeTurn();
        c.move(new Location("H2"), new Move("H8"));
        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("H8"), new Move("H2"));

        c.changeTurn();
        c.move(new Location("H2"), new Move("E2"));
        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("E2"), new Move("H2"));

        c.changeTurn();
        c.move(new Location("D5"), new Move("A8"));
        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("A8"), new Move("D5"));

        Pawn p = new Pawn(Color.WHITE);
        c.changeTurn();
        c.setPiece(p, new Location("F7"));
        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("E8"), new Move("F7")));
    }

    @org.junit.jupiter.api.Test
    public void discoveredAttackTest(){
        King k1 = new King(Color.WHITE);
        King k2 = new King(Color.BLACK);

        Bishop b1 = new Bishop(Color.WHITE);
        Bishop b2 = new Bishop(Color.WHITE);
        Queen q = new Queen(Color.WHITE);
        Knight kn = new Knight(Color.WHITE);

        Chessboard c = new Chessboard();
        c.setKing(k1, new Location("E1"));
        c.setKing(k2, new Location("E8"));

        c.setPiece(b1, new Location("E4"));
        c.setPiece(b2, new Location("B5"));
        c.setPiece(q, new Location("E3"));
        c.setPiece(kn, new Location("C6"));

        assertFalse(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.move(new Location("C6"), new Move("D4"));
        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("D4"), new Move("C6"));
        c.changeTurn();
        c.move(new Location("E4"), new Move("H1"));
        assertTrue(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        c.move(new Location("H1"), new Move("E4"));
        assertFalse(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
    }

    @org.junit.jupiter.api.Test
    public void IllegalMoveTest(){
        King k1 = new King(Color.WHITE);
        King k2 = new King(Color.BLACK);

        Bishop b = new Bishop(Color.WHITE);
        Queen q = new Queen(Color.WHITE);
        Rook r = new Rook(Color.BLACK);

        Chessboard c = new Chessboard();
        c.setKing(k1, new Location("E1"));
        c.setKing(k2, new Location("E8"));

        c.setPiece(b, new Location("C4"));
        c.setPiece(q, new Location("E3"));
        c.setPiece(r, new Location("E7"));

        assertFalse(c.getOpponentKing(Color.WHITE).isUnderCheck(c));
        c.changeTurn();
        System.out.println(c);
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("E7"), new Move("A7")));
        assertDoesNotThrow(() -> c.clone().move(new Location("E7"), new Move("E3")));
        c.changeTurn();
        c.move(new Location("C4"), new Move("B5"));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("E7"), new Move("E3")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("E8"), new Move("D7")));
        assertDoesNotThrow(() -> c.clone().move(new Location("E8"), new Move("F8")));

    }

}
