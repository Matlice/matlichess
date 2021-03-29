package it.matlice.matlichess.controller.net;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.controller.Game;

import java.util.HashMap;

/**
 * This packet is used to synchronize the client and the server after the initialization.
 * we make the server rule out the position.
 */
public class PositionInit implements ComPacket {

    private final String[] moves;
    private final Integer[] move_times;
    private final String currentFEN;
    private final PieceColor user_color;

    public PositionInit(PieceColor recipient_color) {
        HashMap<String, Integer> m = Game.getInstance().getPositions();
        this.moves = m.keySet().toArray(new String[0]);
        this.move_times = m.values().toArray(new Integer[0]);
        this.currentFEN = Game.getInstance().getPositionFen();
        this.user_color = recipient_color;
    }

    public String[] getMoves() {
        return moves;
    }

    public Integer[] getMove_times() {
        return move_times;
    }

    public String getCurrentFEN() {
        return currentFEN;
    }

    public PieceColor getColor() {
        return user_color;
    }

    @Override
    public String getPacketType() {
        return "POS_INIT";
    }
}
