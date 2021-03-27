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

    /**
     * Describes the Locations reachable by a chess Pawn without checking if the king is under attack
     *
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the Position of the Pawn
     * @return the MovePattern of the piece without checking if the king is under attack
     */
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

    /**
     * Abstract method that clone a Pawn into an identical other Pawn
     * @return the cloned Pawn
     */
    @Override
    public Piece clone() {
        var clone = new Pawn(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
