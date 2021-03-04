package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.*;

/**
 * Identifies the Pawn Piece in a chess game
 */
public class Pawn extends Piece {

    public Pawn(PieceColor pieceColor) {
        super("Pawn", "P", 1, pieceColor);
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addPawn();
    }

    /**
     * Notifies that the pawn has made its first move
     */
    @Override
    public void hasBeenMoved(Chessboard c) {
        c.resetHalfMoveClock();
        super.hasBeenMoved(c);
    }

    @Override
    public Piece clone() {
        var clone = new Pawn(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
