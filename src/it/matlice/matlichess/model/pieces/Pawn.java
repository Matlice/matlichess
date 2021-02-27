package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

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

    /**
     * Notifies that the pawn has made its first move
     * @param from Where the piece started
     * @param to Where the piece has been moved to
     */
    @Override
    public void hasBeenMoved(Chessboard c, Location from, Location to) {
        c.resetHalfMoveClock();
        super.hasBeenMoved(c, from, to);
    }

    @Override
    public Piece clone() {
        var clone = new Pawn(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
