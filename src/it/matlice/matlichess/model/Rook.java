package it.matlice.matlichess.model;

/**
 * Identifies the Rook Piece in a chess game
 */
public class Rook extends Piece {

    public Rook(Color color) {
        super("Rook", "R", 5, color);
    }

    @Override
    public MovePattern unvalidated_move_pattern(Chessboard chessboard, Location myPosition) {
        return new MovePattern(chessboard, myPosition, this.getColor())
                .addColumn()
                .addRow();
    }

    @Override
    public Piece clone() {
        var clone = new Rook(this.getColor());
        clone.has_moved = this.has_moved;
        return clone;
    }

}
