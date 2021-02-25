package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece {

    public Rook(Color color){
        super("Rook", "R", 5, color);
    }

    @Override
    protected MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addColumn()
                .addRow();
    }
}
