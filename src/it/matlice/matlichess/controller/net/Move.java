package it.matlice.matlichess.controller.net;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.controller.Game;

public class Move implements ComPacket{

    private final String extendedMove;

    public String getExtendedMove() {
        return extendedMove;
    }

    public Move(String extendedMove) {
        this.extendedMove = extendedMove;
    }

    @Override
    public String getPacketType() {
        return "MOVE";
    }
}
