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
     * @return if the pawn is taking a piece with en Passant Move, it returns the taken Pawn. Else returns null
     */
    @Override
    public Piece hasBeenMoved(Chessboard c, Location from, Location to) {
        super.hasBeenMoved(c, from, to);
        c.resetHalfMoveClock();
        if(to.equals(c.getEnPassantTargetSquare())) {
            if (getColor().equals(Color.WHITE)) return c.getPieceAt(new Location(to.col(), to.row() - 1));
            return c.getPieceAt(new Location(to.col(), to.row() + 1));
        }

        if (this.getColor().equals(Color.WHITE) && from.row() == 1 && to.row() == 3) {
            c.setEnPassantTargetSquare(new Location(to.col(), to.row()-1));
        }
        if (this.getColor().equals(Color.BLACK) && from.row() == 6 && to.row() == 4) {
            c.setEnPassantTargetSquare(new Location(to.col(), to.row()+1));
        }
        return null;
    }
}
