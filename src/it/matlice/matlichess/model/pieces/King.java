package it.matlice.matlichess.model.pieces;

import it.matlice.matlichess.model.*;

import java.util.Map;

/**
 * Identifies the King Piece in a chess game
 */
public class King extends Piece {

    private final Location BLACK_QUEEN_ROOK_LOCATION = new Location("A8");
    private final Location BLACK_KING_ROOK_LOCATION = new Location("H8");
    private final Location WHITE_QUEEN_ROOK_LOCATION = new Location("A1");
    private final Location WHITE_KING_ROOK_LOCATION = new Location("H1");

    public King(Color color) {
        super("King", "K", Math.abs(~0), color);
    }

    /**
     * Given a chessboard pattern, returns if the king is under attack
     *
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param myPosition the position of the king
     * @return true if the king is under attack, else false
     */
    public boolean isUnderCheck(Chessboard chessboard, Location myPosition) {
        for (Map<Piece, Location> family : chessboard.getPieces().values())
            for (Map.Entry<Piece, Location> value : family.entrySet())
                // value contains an opponent Piece and his Location
                if (value.getKey().getColor().equals(this.getColor().opponent()))
                    // unvalidated_move_pattern is used because is not necessary to move the opponent piece to check
                    if (value.getKey().unvalidated_move_pattern(chessboard, value.getValue()).get().contains(myPosition))
                        return true;
        return false;
    }


    private boolean canCastle(Chessboard c, String side) {
        //if the king has moved we cant castle
        if (this.hasMoved()) return false;
        var king_position = this.getColor().equals(Color.WHITE) ? new Location("E1") : new Location("E8");
        //if the king is under check it can not castle
        if (this.isUnderCheck(c, king_position)) return false;

        switch (side) {
            case "Queen": {
                //castling queen side
                //cant castle if the rook has moved or have been taken
                var rook = c.getPieceAt(this.getColor().equals(Color.WHITE) ? WHITE_QUEEN_ROOK_LOCATION : BLACK_QUEEN_ROOK_LOCATION);
                if (!rook.getName().equals("Rook") || rook.hasMoved())
                    return false;

                //there are some pieces between me and the rook?
                //does the king have to cross attacked locations?
                if (this.getColor().equals(Color.WHITE)) {
                    if (c.getPieceAt(new Location("D1")) != null || c.getPieceAt(new Location("C1")) != null || c.getPieceAt(new Location("B1")) != null)
                        return false;
                    if (this.isUnderCheck(c, new Location("D1")) || this.isUnderCheck(c, new Location("C1")))
                        return false;
                } else {
                    if (c.getPieceAt(new Location("D8")) != null || c.getPieceAt(new Location("C8")) != null || c.getPieceAt(new Location("B8")) != null)
                        return false;
                    if (this.isUnderCheck(c, new Location("D8")) || this.isUnderCheck(c, new Location("C8")))
                        return false;
                }
                return true;
            }
            case "King": {
                //castling king side
                //cant castle if the rook has moved or have been taken
                var rook = c.getPieceAt(this.getColor().equals(Color.WHITE) ? WHITE_KING_ROOK_LOCATION : BLACK_KING_ROOK_LOCATION);
                if (!rook.getName().equals("Rook") || rook.hasMoved())
                    return false;
                //there are some pieces between me and the rook?
                //does the king have to cross attacked locations?
                if (this.getColor().equals(Color.WHITE)) {
                    if (c.getPieceAt(new Location("F1")) != null || c.getPieceAt(new Location("G1")) != null)
                        return false;
                    if (this.isUnderCheck(c, new Location("F1")) || this.isUnderCheck(c, new Location("G1")))
                        return false;
                } else {
                    if (c.getPieceAt(new Location("F8")) != null || c.getPieceAt(new Location("G8")) != null)
                        return false;
                    if (this.isUnderCheck(c, new Location("F8")) || this.isUnderCheck(c, new Location("G8")))
                        return false;
                }
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        var mp = new MovePattern(chessboard, myPosition, this.getColor()).addKing();
        if(canCastle(chessboard, "Queen")) mp.addSquare(this.getColor().equals(Color.WHITE) ? new Location("C1") : new Location("C8"));
        if(canCastle(chessboard, "King")) mp.addSquare(this.getColor().equals(Color.WHITE) ? new Location("G1") : new Location("G8"));
        return mp;
    }

    /**
     * Notifies that a piece has made its first move
     */
    @Override
    public void hasBeenMoved(Chessboard c, Location to) {
        if(!this.hasMoved()){
            if(this.getColor().equals(Color.WHITE) && to.equals(new Location("C1")))
                c._make_move(new Location("A1"), new Location("D1"));
            if(this.getColor().equals(Color.WHITE) && to.equals(new Location("G1")))
                c._make_move(new Location("H1"), new Location("F1"));
            if(this.getColor().equals(Color.BLACK) && to.equals(new Location("C8")))
                c._make_move(new Location("A8"), new Location("D8"));
            if(this.getColor().equals(Color.BLACK) && to.equals(new Location("G8")))
                c._make_move(new Location("H8"), new Location("F8"));
        }
        super.hasBeenMoved(c, to);
    }
}
