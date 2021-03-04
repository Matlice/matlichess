package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.*;

/**
 * Identifies the Queen Piece in a chess game
 */
public class Queen extends Piece {

    public Queen(PieceColor pieceColor) {
        super("Queen", "Q", 9, pieceColor);
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addColumn()
                .addRow()
                .addDiagonals();
    }

    @Override
    public Piece clone() {
        var clone = new Queen(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
