package it.matlice.malichess.movements;

import it.matlice.malichess.ChessboardTest;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.King;
import it.matlice.matlichess.model.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class PawnMoves {

    @org.junit.jupiter.api.Test
    public void enPassantTest(){
        King k1 = new King(Color.WHITE);
        King k2 = new King(Color.BLACK);

        Pawn p1 = new Pawn(Color.WHITE);
        Pawn p2 = new Pawn(Color.WHITE);
        Pawn p3 = new Pawn(Color.WHITE);
        Pawn p4 = new Pawn(Color.WHITE);
        Pawn p5 = new Pawn(Color.BLACK);
        Pawn p6 = new Pawn(Color.BLACK);
        Pawn p7 = new Pawn(Color.BLACK);
        Pawn p8 = new Pawn(Color.BLACK);
        Pawn p9 = new Pawn(Color.BLACK);

        ChessboardTest c = new ChessboardTest();
        c.setKing(k1, new Location("E1"));
        c.setKing(k2, new Location("E8"));

        c.setPiece(p1, new Location("B2"));
        c.setPiece(p2, new Location("D2"));
        c.setPiece(p3, new Location("E2"));
        c.setPiece(p4, new Location("G2"));
        c.setPiece(p5, new Location("A7"));
        c.setPiece(p6, new Location("C7"));
        c.setPiece(p7, new Location("D7"));
        c.setPiece(p8, new Location("F7"));
        c.setPiece(p9, new Location("H7"));

        c.move("B2", "B4");
        c.move("C7", "C5");
        c.move("B4", "B5");
        c.move("A7", "A5");

        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("B5"), new Location("C6")));
        assertDoesNotThrow(() -> c.clone().move("B5", "A6"));
        assertDoesNotThrow(() -> c.clone().move("B5", "B6"));

        c.move("G2", "G4");
        c.move("C5", "C4");
        c.move("D2", "D4");
        assertDoesNotThrow(() -> c.clone().move("C4", "D3"));

        c.move("C4", "D3");
        c.move("E2", "D3");

        c.move("F7", "F5");
        c.move("G4", "G5");
        c.move("H7", "H5");
        assertDoesNotThrow(() -> c.clone().move("G5", "H6"));
    }



}
