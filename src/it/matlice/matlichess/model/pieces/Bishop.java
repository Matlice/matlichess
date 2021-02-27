package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Identifies the Bishop Piece in a chess game
 */
public class Bishop extends Piece {

    public Bishop(Color color){
        super("Bishop", "B", 3, color);
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addDiagonals();
    }

    @Override
    public Piece clone() {
        var clone = new Bishop(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
