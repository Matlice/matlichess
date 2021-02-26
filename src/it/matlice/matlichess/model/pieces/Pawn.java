package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Identifies the Pawn Piece in a chess game
 */
public class Pawn extends Piece {

    public Pawn(Color color){
        super("Pawn", "P", 1, color);
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addPawn();
    }
}
