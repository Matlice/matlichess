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
     * Given a chessboard and a position, returns if the position is under attack
     *
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @param location   the location to check
     * @return true if the king is under attack, else false
     */
    public boolean isUnderCheck(Chessboard chessboard, Location location) {
        for (Map<Piece, Location> family : chessboard.getPieces().values()) {
            for (Map.Entry<Piece, Location> value : family.entrySet()) {
                if (value.getKey() instanceof King) break;
                // value contains an opponent Piece and his Location
                if (value.getKey().getColor().equals(this.getColor().opponent()))
                    // unvalidated_move_pattern is used because is not necessary to move the opponent piece to check
                    if (value.getKey().unvalidated_move_pattern(chessboard, value.getValue()).get().keySet().contains(location))
                        return true;
            }
        }
        return false;
    }

    /**
     * Given a chessboard, returns if the king is under attack
     *
     * @param chessboard the {@link Chessboard} where are placed the pieces
     * @return true if the king is under attack, else false
     */
    public boolean isUnderCheck(Chessboard chessboard) {
        return isUnderCheck(chessboard, chessboard.getPieces().get("King").get(chessboard.getKing(this.getColor())));
    }

    /**
     * Check whether the king has the possibility to castle queen's side, doesn't check for particular position that prevents the castling
     *
     * @param c chessboard
     * @return true if queen side castling is available
     */
    public boolean isQueenCastlingAvailable(Chessboard c) {
        var rook = c.getPieceAt(this.getColor().equals(Color.WHITE) ? WHITE_QUEEN_ROOK_LOCATION : BLACK_QUEEN_ROOK_LOCATION);
        if(rook == null) return false;
        return rook.getName().equals("Rook") && !rook.hasMoved();
    }

    /**
     * Check whether the king has the possibility to castle king's side, doesn't check for particular position that prevents the castling
     *
     * @param c chessboard
     * @return true if king side castling is available
     */
    public boolean isKingCastlingAvailable(Chessboard c) {
        Piece rook = c.getPieceAt(this.getColor().equals(Color.WHITE) ? WHITE_KING_ROOK_LOCATION : BLACK_KING_ROOK_LOCATION);
        if(rook == null) return false;
        return rook.getName().equals("Rook") && !rook.hasMoved();
    }

    /**
     * Check whether the castling is doable right now
     *
     * @param c    chessboard
     * @param side the side to check, "Queen" or "King"
     * @return true if can castle
     */
    public boolean canCastle(Chessboard c, String side) {
        //if the king has moved we cant castle
        if (this.hasMoved()) return false;
        var king_position = this.getColor().equals(Color.WHITE) ? new Location("E1") : new Location("E8");
        //if the king is under check it can not castle
        if (this.isUnderCheck(c, king_position)) return false;

        switch (side) {
            case "Queen": {
                //castling queen side
                //cant castle if the rook has moved or have been taken
                if (!isQueenCastlingAvailable(c)) return false;

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
                if (!isKingCastlingAvailable(c)) return false;

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
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addKing();
    }

    @Override
    public Piece clone() {
        var clone = new King(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
