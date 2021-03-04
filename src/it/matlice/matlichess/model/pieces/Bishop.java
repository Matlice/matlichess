package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.*;

/**
 * Identifies the Bishop Piece in a chess game
 */
public class Bishop extends Piece {

    public Bishop(PieceColor pieceColor) {
        super("Bishop", "B", 3, pieceColor);
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
