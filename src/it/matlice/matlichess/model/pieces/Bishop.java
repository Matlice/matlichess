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

    /**
     * Describes the Locations reachable by a chess Bishop without checking if the king is under attack
     *
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the Position of the Bishop
     * @return the MovePattern of the piece without checking if the king is under attack
     */
    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addDiagonals();
    }

    /**
     * Abstract method that clone a Bishop into an identical other Bishop
     * @return the cloned Bishop
     */
    @Override
    public Piece clone() {
        var clone = new Bishop(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
