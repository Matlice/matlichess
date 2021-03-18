package it.matlice.matlichess.controller.net;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.controller.Game;

public class PositionInit implements ComPacket{

    private String[] moves;
    private Integer[] move_times;
    private String currentFEN;
    private PieceColor user_color;
    private PieceColor turn;

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

    public PositionInit(PieceColor recipient_color, PieceColor turn) {
        var m = Game.getInstance().getPositions();
        this.moves = m.keySet().toArray(new String[0]);
        this.move_times = m.values().toArray(new Integer[0]);
        this.currentFEN = Game.getInstance().getPositionFen();
        this.user_color = recipient_color;
        this.turn = turn;
    }

    @Override
    public String getPacketType() {
        return "POS_INIT";
    }


    public PieceColor getTurn() {
        return turn;
    }
}
