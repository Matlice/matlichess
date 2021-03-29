package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.*;

/**
 * Identifies the Rook Piece in a chess game
 */
public class Rook extends Piece {

    public Rook(PieceColor pieceColor) {
        super("Rook", "R", 5, pieceColor);
    }

    /**
     * Describes the Locations reachable by a chess Rook without checking if the king is under attack
     *
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the Position of the Rook
     * @return the MovePattern of the piece without checking if the king is under attack
     */
    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addColumn()
                .addRow();
    }

    /**
     * Abstract method that clone a Rook into an identical other Rook
     * @return the cloned Rook
     */
    @Override
    public Piece clone() {
        Rook clone = new Rook(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
