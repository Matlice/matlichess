package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

import java.util.Map;

/**
 * Identifies the King Piece in a chess game
 */
public class King extends Piece {

    public King(Color color) {
        super("King", "K", Math.abs(~0), color);
    }

    /**
     * Given a chessboard pattern, returns if the king is under attack
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the position of the king
     * @return true if the king is under attack, else false
     */
    public boolean isUnderCheck(Chessboard chessboard, Location myPosition) {
        for (Map<Piece, Location> family : chessboard.getPieces().values())
            for (Map.Entry<Piece, Location> value : family.entrySet())
                // value contains an opponent Piece and his Location
                if(value.getKey().getColor().equals(this.getColor().opponent()))
                    // unvalidated_move_pattern is used because is not necessary to move the opponent piece to check
                    if (value.getKey().unvalidated_move_pattern(chessboard, value.getValue()).get().contains(myPosition))
                        return true;
        return false;
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor()).addKing();
    }
}
