package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

/**
 * Identifies the Pawn Piece in a chess game
 */
public class Pawn extends Piece {

    // TODO add enpassant as a move for pawn

    public Pawn(Color color){
        super("Pawn", "P", 1, color);
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addPawn();
    }

    @Override
    public void hasBeenMoved(Chessboard c, Location from, Location to) {
        c.resetHalfmoveClock();
        if (this.getColor().equals(Color.WHITE) && from.row() == 1 && to.row() == 3) {
            c.setEnPassantTargetSquare(new Location(to.col(), to.row()-1));
        }
        if (this.getColor().equals(Color.BLACK) && from.row() == 6 && to.row() == 4) {
            c.setEnPassantTargetSquare(new Location(to.col(), to.row()+1));
        }
        super.hasBeenMoved(c, from, to);
    }
}
