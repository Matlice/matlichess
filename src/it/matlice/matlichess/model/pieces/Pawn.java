package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

/**
 * Identifies the Pawn Piece in a chess game
 */
public class Pawn extends Piece {

    private boolean canEnPassant = false;

    public Pawn(Color color) {
        super("Pawn", "P", 1, color);
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        var mp = new MovePattern(chessboard, myPosition, this.getColor())
                .addPawn();
        if(this.canEnPassant) {
            mp.addSquare(chessboard.getEnPassantTargetSquare());
            this.canEnPassant = false;
        }
        return mp;
    }

    /**
     * Notifies that the pawn has made its first move
     *
     * @param from Where the piece started
     * @param to   Where the piece has been moved to
     * @return if the pawn is taking a piece with en Passant Move, it returns the taken Pawn. Else returns null
     */
    @Override
    public Piece hasBeenMoved(Chessboard c, Location from, Location to) {
        c.resetHalfMoveClock();
        if (
                !this.has_moved &&
                from.equals(this.getColor() == Color.WHITE ? new Location(from.col(), 1) : new Location(from.col(), 6)) &&
                to.equals(this.getColor() == Color.WHITE ? new Location(from.col(), 3) : new Location(from.col(), 4))
        ) {
            c.setEnPassantTargetSquare(new Location(to.col(), to.row() + (this.getColor() == Color.WHITE ? -1 : 1)));
            if(to.col() > 0){
                var p = c.getPieceAt(new Location(to.col()-1, to.row()));
                if(p instanceof Pawn)
                    ((Pawn) p).canEnPassant = true;
            }
            if(to.col() < 7){
                var p = c.getPieceAt(new Location(to.col()+1, to.row()));
                if(p instanceof Pawn)
                    ((Pawn) p).canEnPassant = true;
            }
            this.has_moved = true;
            return null;
        }

        Piece capture = null;
        if(this.canEnPassant && from.col() != to.col()){
            var l = new Location(to.col(), to.row() + (this.getColor() == Color.WHITE ? -1 : 1));
            capture = c.getPieceAt(l);
            c.removePiece(l);
        }
        this.canEnPassant = false;
        c.setEnPassantTargetSquare(null);
        return capture;
    }

    @Override
    public Piece clone() {
        var clone = new Pawn(this.getColor());
        clone.has_moved = this.has_moved;
        clone.canEnPassant = this.canEnPassant;
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (((Pawn) o).canEnPassant == this.canEnPassant);
    }
}
