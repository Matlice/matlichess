package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Identifies the Knight Piece in a chess game
 */
public class Knight extends Piece {

    public Knight(Color color){
        super("Knight", "N", 3, color);
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addKnight();
    }

    @Override
    public Piece clone() {
        var clone = new Knight(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }
}
