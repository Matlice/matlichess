package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece {

    public Rook(Color color){
        super("Rook", "R", 5, color);
    }

    @Override
    public Location[] getAvailableMoves(Chessboard chessboard, Location myPosition) {
        //todo validate move
        return new MovePattern(chessboard)
                .addColumn(myPosition, this.getColor())
                .addRow(myPosition, this.getColor())
                .get();
    }

    @Override
    public boolean isPlaceAllowed(Chessboard chessboard, Location l, Location myPosition) {
        return Arrays.stream(getAvailableMoves(chessboard, myPosition)).filter((Location e) -> e == l).toArray().length == 1;
    }
}
