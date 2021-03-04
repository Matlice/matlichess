package it.matlice.matlichess.view;

public enum PieceType {

    PAWN_WHITE(0),
    BISHOP_WHITE(1),
    KNIGHT_WHITE(2),
    ROOK_WHITE(3),
    QUEEN_WHITE(4),
    KING_WHITE(5),
    PAWN_BLACK(6),
    BISHOP_BLACK(7),
    KNIGHT_BLACK(8),
    ROOK_BLACK(9),
    QUEEN_BLACK(10),
    KING_BLACK(11);

    public final int index;

    PieceType(int index) {
        this.index = index;
    }

}