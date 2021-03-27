package it.matlice.matlichess.controller.net;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.model.Piece;

import java.util.Objects;

/**
 * This packet is used to share incrementally moves made from the users.
 * after this packet is sent the sender expect a NOP response
 */
public class Move implements ComPacket{

    private final String extendedMove;
    private String[] promotionTypes = new String[2];

    public Move(String extendedMove) {
        this.extendedMove = extendedMove;
        this.promotionTypes = Game.getInstance().getPromotions();
    }

    public Move(Location from, Location to) {
        this(from.toString() + to.toString());
    }

    @Override
    public String getPacketType() {
        return "MOVE";
    }

    public String getExtendedMove() {
        return extendedMove;
    }

    public String[] getPromotionTypes() {
        return promotionTypes;
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
