package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {

    public King(Color color) {
        super("King", "K", Math.abs(~0), color);
    }

    @Override
    public Location[] getAvailableMoves(Chessboard chessboard, Location myPosition) {
        //todo validate move
        return new MovePattern(chessboard).addKing(myPosition, this.getColor()).get();
    }

    @Override
    public boolean isPlaceAllowed(Chessboard chessboard, Location l, Location myPosition) {
        return Arrays.stream(getAvailableMoves(chessboard, myPosition)).filter((Location e) -> e == l).toArray().length == 1;
    }
}
