package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

public class King extends Piece {

    public King(Color color) {
        super("King", "K", Math.abs(~0), color);
    }


    public boolean isUnderCheck(Chessboard chessboard, Location myPosition) {
        for (var fam : chessboard.getPieces().values())
            for (var v : fam.entrySet())
                if (v.getKey().getColor() == this.getColor().opponent() && v.getKey().canCapture(chessboard, myPosition, v.getValue()))
                    return true;
        return false;
    }

    @Override
    protected MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor()).addKing();
    }
}
