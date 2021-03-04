package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.*;

/**
 * Identifies the Knight Piece in a chess game
 */
public class Knight extends Piece {

    public Knight(PieceColor pieceColor) {
        super("Knight", "N", 3, pieceColor);
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
