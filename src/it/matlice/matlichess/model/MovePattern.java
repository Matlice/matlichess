package it.matlice.matlichess.model;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.pieces.King;

import java.util.function.Supplier;

/**
 * Set of patterns used by the pieces to move along the chessboard
 * It also provides utility methods for the control of the came, like checking if the king is under attack
 */
public class MovePattern {

    private MoveList locations = new MoveList();
    private final Chessboard chessboard;
    private final Location pieceLocation;
    private final PieceColor myPieceColor;

    public MovePattern(Chessboard c, Location l, PieceColor myPieceColor) {
        this.chessboard = c;
        this.pieceLocation = l;
        this.myPieceColor = myPieceColor;
    }

    /**
     * Utility class function used to add {@link Location}s to the private variable, according to the instruction given by the patterns
     * When the program validates if a Chess box is reachable, it calls this method to save the locations.
     * Returns a boolean to continue or stop the iteration.
     *
     * @param col        the column index of the location to check
     * @param row        the row index of the location to check
     * @param pieceColor      the player's color
     * @return true if it's needed to stop the iteration, false to continue
     */
    private boolean piece_can_take(int col, int row, PieceColor pieceColor) {
        if (row > 7 || row < 0 || col > 7 || col < 0) return true;
        if (chessboard.getPieceAt(col, row) != null) {
            if (chessboard.getPieceAt(col, row).getColor().equals(pieceColor.opponent()))
                locations.put(col, row);
            return true;
        }
        locations.put(col, row);
        return false;
    }

    /**
     * Pattern to add the reachable locations by a pawn, including the first move skipping two squares and
     * the adjacent diagonals when it can take a piece, and en passant when it can
     *
     * @return the updated pattern
     */
    public MovePattern addPawn() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();

        // forward movements
        if (myPieceColor == PieceColor.WHITE) {
            if (row == 7) return this; // end of chessboard, should not happen
            else if (row == 1) // if still in original row, then it can go up two squares
                if (chessboard.getPieceAt(col, row + 1) == null && chessboard.getPieceAt(col, row + 2) == null)
                    locations.put(col, row + 2, () -> {
                        chessboard.setEnPassantTargetSquare(new Location(col, row + 1));
                        return null;
                    });
        } else /* if (myColor == Color.BLACK) */ {
            if (row == 0) return this;
            else if (row == 6)
                if (chessboard.getPieceAt(col, row - 1) == null && chessboard.getPieceAt(col, row - 2) == null)
                    locations.put(col, row - 2, () -> {
                        chessboard.setEnPassantTargetSquare(new Location(col, row - 1));
                        return null;
                    });
        }

        int dir = (myPieceColor == PieceColor.WHITE) ? 1 : -1;

        if (chessboard.getPieceAt(col, row + dir) == null) {
            Supplier<Piece> action;
            if (row + dir == 0 || row + dir == 7) {
                action = () -> {
                    chessboard.promote(new Location(col, row), myPieceColor);
                    return null;
                };
            } else {
                action = () -> null;
            }
            locations.put(col, row + dir, action);
        }

        // diagonal capture and en passant
        Location target = new Location(col - 1, row + dir);
        Location passantTarget = new Location(col - 1, row);
        if (col > 0) _pawnCapture(chessboard, myPieceColor, target, passantTarget, pieceLocation);

        target = new Location(col + 1, row + dir);
        passantTarget = new Location(col + 1, row);
        if (col < 7) _pawnCapture(chessboard, myPieceColor, target, passantTarget, pieceLocation);

        return this;
    }

    /**
     * Evaluates whether a pawn can capture a piece diagonally, or capture a pawn en passant
     * @param chessboard      chessboard
     * @param myPieceColor         color
     * @param moveTarget      the target location the pawn can capture and move to
     * @param enPassantTarget the location of the other pawn that can be captured en passant
     * @param actualPosition
     */
    private void _pawnCapture(Chessboard chessboard, PieceColor myPieceColor, Location moveTarget, Location enPassantTarget, Location actualPosition) {
        if (chessboard.getPieceAt(moveTarget) != null) {
            // normal diagonal capture
            if (chessboard.getPieceAt(moveTarget).getColor().equals(myPieceColor.opponent())) {
                Supplier<Piece> action;
                if (moveTarget.row() == 0 || moveTarget.row() == 7) {
                    action = () -> {
                        chessboard.promote(actualPosition, myPieceColor);
                        return null;
                    };
                } else {
                    action = () -> null;
                }
                locations.put(moveTarget, action);
            }

        } else if (moveTarget.equals(chessboard.getEnPassantTargetSquare())) {
            // left en passant
            locations.put(moveTarget, () -> {
                Piece p = chessboard.getPieceAt(enPassantTarget);
                chessboard.removePiece(enPassantTarget);
                return p;
            });
        }
    }

    /**
     * Pattern to add the reachable locations across the row
     *
     * @return the updated pattern
     */
    public MovePattern addRow() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();
        for (int i = col + 1; i < 8; i++)
            if (piece_can_take(i, row, myPieceColor)) break;

        for (int i = col - 1; i >= 0; i--) {
            if (piece_can_take(i, row, myPieceColor)) break;
        }
        return this;
    }

    /**
     * Pattern to add the reachable locations across the column
     *
     * @return the updated pattern
     */
    public MovePattern addColumn() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();
        for (int i = row + 1; i < 8; i++)
            if (piece_can_take(col, i, myPieceColor)) break;
        for (int i = row - 1; i >= 0; i--)
            if (piece_can_take(col, i, myPieceColor)) break;

        return this;
    }

    /**
     * Pattern to add the reachable locations across the diagonals
     *
     * @return the updated pattern
     */
    public MovePattern addDiagonals() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();

        for (int i = 1; i < 8; i++) {
            if (piece_can_take(col + i, row + i, myPieceColor)) break;
        }
        for (int i = 1; i < 8; i++) {
            if (piece_can_take(col - i, row + i, myPieceColor)) break;
        }
        for (int i = 1; i < 8; i++) {
            if (piece_can_take(col + i, row - i, myPieceColor)) break;
        }
        for (int i = 1; i < 8; i++) {
            if (piece_can_take(col - i, row - i, myPieceColor)) break;
        }
        return this;
    }

    /**
     * Pattern to add the locations reachable by a knight
     *
     * @return the updated pattern
     */
    public MovePattern addKnight() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();
        piece_can_take(col + 1, row + 2, myPieceColor);
        piece_can_take(col - 1, row + 2, myPieceColor);
        piece_can_take(col + 2, row + 1, myPieceColor);
        piece_can_take(col - 2, row + 1, myPieceColor);
        piece_can_take(col + 1, row - 2, myPieceColor);
        piece_can_take(col - 1, row - 2, myPieceColor);
        piece_can_take(col + 2, row - 1, myPieceColor);
        piece_can_take(col - 2, row - 1, myPieceColor);
        return this;
    }

    /**
     * Pattern to add the locations reachable by a king
     *
     * @return the updated pattern
     */
    public MovePattern addKing() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();
        piece_can_take(col + 1, row + 1, myPieceColor);
        piece_can_take(col, row + 1, myPieceColor);
        piece_can_take(col - 1, row + 1, myPieceColor);
        piece_can_take(col + 1, row, myPieceColor);
        piece_can_take(col - 1, row, myPieceColor);
        piece_can_take(col + 1, row - 1, myPieceColor);
        piece_can_take(col, row - 1, myPieceColor);
        piece_can_take(col - 1, row - 1, myPieceColor);

        // castling
        King king = (King) chessboard.getPieceAt(pieceLocation);
        if (king != null) {

            // since the castling moves are symmetrical, the only thing that changes is the row
            int castlingRow = myPieceColor.equals(PieceColor.WHITE) ? 0 : 7;

            if (king.canCastle(chessboard, "Queen"))
                this.locations.put(2, castlingRow, () -> {
                    // moving the tower after castling
                    Piece castlingRook = chessboard.getPieceAt(new Location(0, castlingRow));
                    castlingRook.hasBeenMoved(chessboard);
                    chessboard._set_piece_at(new Location(3, castlingRow), castlingRook);
                    chessboard.removePiece(new Location(0, castlingRow));
                    return null;
                });
            if (king.canCastle(chessboard, "King"))
                this.locations.put(6, castlingRow, () -> {
                    // moving the tower after castling
                    Piece castlingRook = chessboard.getPieceAt(new Location(7, castlingRow));
                    castlingRook.hasBeenMoved(chessboard);
                    chessboard._set_piece_at(new Location(5, castlingRow), castlingRook);
                    chessboard.removePiece(new Location(7, castlingRow));
                    return null;
                });
        }

        return this;
    }

    /**
     * Validates the MovePattern checking if the king will result under attack after the move.
     * Removes the wrong Locations
     *
     * @return the updated pattern
     */
    public MovePattern validate() {

        MoveList validatedLocations = new MoveList();
        validatedLocations.putAll(this.locations);

        var has_moved = chessboard.getPieceAt(pieceLocation).hasMoved();
        for (Location dest : locations.keySet()) {
            Chessboard nextMoveBoard = chessboard.clone();
            chessboard.getPieceAt(pieceLocation)._reset_movement(has_moved);
            nextMoveBoard._make_move(pieceLocation, dest, () -> null);
            if (chessboard.getKing(this.myPieceColor).isUnderCheck(nextMoveBoard))
                validatedLocations.remove(dest);
            chessboard.getPieceAt(pieceLocation)._reset_movement(has_moved);
        }

        this.locations = validatedLocations;
        return this;
    }

    /**
     * Returns the saved locations
     *
     * @return the saved locations
     */
    public MoveList get() {
        return locations;
    }

}
