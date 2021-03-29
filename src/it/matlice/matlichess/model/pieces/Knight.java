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

    /**
     * Describes the Locations reachable by a chess Knight without checking if the king is under attack
     *
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the Position of the Knight
     * @return the MovePattern of the piece without checking if the king is under attack
     */
    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addKnight();
    }

    /**
     * Abstract method that clone a Knight into an identical other Knight
     * @return the cloned Knight
     */
    @Override
    public Piece clone() {
        Knight clone = new Knight(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
