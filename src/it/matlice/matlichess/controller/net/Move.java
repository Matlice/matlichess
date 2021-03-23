package it.matlice.matlichess.controller.net;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.controller.Game;

import java.util.Objects;

public class Move implements ComPacket{

    private final String extendedMove;

    public String getExtendedMove() {
        return extendedMove;
    }

    public Move(String extendedMove) {
        this.extendedMove = extendedMove;
    }

    public Move(Location from, Location to) {
        this(from.toString() + to.toString());
    }

    @Override
    public String getPacketType() {
        return "MOVE";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return extendedMove.equals(move.extendedMove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extendedMove);
    }

}
