package it.matlice.malichess.movements;

import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.pieces.King;
import it.matlice.matlichess.model.pieces.Rook;

import static org.junit.jupiter.api.Assertions.*;

public class NormalMovement {

    @org.junit.jupiter.api.Test
    public void rookTest(){
        var k1 = new King(Color.WHITE);
        var k2 = new King(Color.BLACK);

        Rook r1 = new Rook(Color.WHITE);
        Rook r2 = new Rook(Color.WHITE);
        Rook r5 = new Rook(Color.BLACK);
        Rook r6 = new Rook(Color.BLACK);

        Chessboard c = new Chessboard();
        c.setKing(k1, new Location("C7"));
        c.setKing(k2, new Location("G7"));

        c.setPiece(r1, new Location("D1"));
        c.setPiece(r2, new Location("B4"));
        c.setPiece(r5, new Location("A1"));
        c.setPiece(r6, new Location("D4"));

        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("A1"), new Location("H1")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("A1"), new Location("E1")));
        assertThrows(InvalidMoveException.class, () -> c.clone().move(new Location("D4"), new Location("A4")));
        System.out.println(c.clone());
        assertDoesNotThrow(() -> c.clone().move(new Location("D4"), new Location("F4")));

        assertEquals(r2, c.clone().move(new Location("D4"), new Location("B4")));
        assertEquals(r1, c.clone().move(new Location("D4"), new Location("D1")));
    }

}
