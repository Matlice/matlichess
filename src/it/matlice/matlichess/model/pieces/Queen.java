package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.MovePattern;
import it.matlice.matlichess.model.Piece;

/**
 * Identifies the Queen Piece in a chess game
 */
public class Queen extends Piece {

    public Queen(PieceColor pieceColor) {
        super("Queen", "Q", 9, pieceColor);
    }

    /**
     * Describes the Locations reachable by a chess Queen without checking if the king is under attack
     *
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the Position of the Queen
     * @return the MovePattern of the piece without checking if the king is under attack
     */
    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addColumn()
                .addRow()
                .addDiagonals();
    }

    /**
     * Abstract method that clone a Queen into an identical other Queen
     *
     * @return the cloned Queen
     */
    @Override
    public Piece clone() {
        Queen clone = new Queen(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
