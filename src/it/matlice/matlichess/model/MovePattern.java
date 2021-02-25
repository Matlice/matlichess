package it.matlice.matlichess.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Set of patterns used by the pieces to move along the chessboard
 * It also provides utility methods for the control of the came, like checking if the king is under attack
 */
public class MovePattern {

    private Set<Location> locations = new HashSet<>();
    private Chessboard chessboard;
    private Location pieceLocation;
    private Color myColor;

    public MovePattern(Chessboard c, Location l, Color myColor) {
        this.chessboard = c;
        this.pieceLocation = l;
        this.myColor = myColor;
    }

    /**
     * Utility class function used to add {@link Location}s to the private variable, according to the instruction given by the patterns
     * When the program validates if a Chess box is reachable, it calls this method to save the locations.
     * Returns a boolean to continue or stop the iteration.
     * @param row the row index of the location to check
     * @param col the column index of the location to check
     * @param color the player's color
     * @param addIfEmpty add the destination location if it's empty (false for pawns)
     * @return true if it's needed to stop the iteration, false to continue
     */
    private boolean piece_can_take(int row, int col, Color color, boolean addIfEmpty) {
        if (row > 7 || row < 0 || col > 7 || col < 0) return true;
        if (chessboard.getPieceAt(col, row) != null) {
            if (chessboard.getPieceAt(col, row).getColor().equals(color.opponent()))
                locations.add(new Location(col, row));
            return true;
        }
        if (addIfEmpty) locations.add(new Location(col, row));
        return false;
    }

    /**
     * Utility class function used to add {@link Location}s to the private variable, according to the instruction given by the patterns
     * When the program validates if a Chess box is reachable, it calls this method to save the locations.
     * Returns a boolean to continue or stop the iteration.
     * @param row the row index of the location to check
     * @param col the column index of the location to check
     * @param color the player's color
     * @return true if it's needed to stop the iteration, false to continue
     */
    private boolean piece_can_take(int row, int col, Color color) {
        return piece_can_take(row, col, color, true);
    }

    /**
     * Returns the saved locations
     * @return the saved locations
     */
    public Set<Location> get() {
        return locations;
    }

    public MovePattern addPawn() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();

        // todo add promotion

        if (myColor == Color.WHITE) {
            if (row == 7) return this; // end of chessboard, should not happen
            else if (row == 1) // if still in original row, then it can go up two squares
                if (chessboard.getPieceAt(col, row+1) == null && chessboard.getPieceAt(col, row+2) == null)
                    locations.add(new Location(col, row+2));
        } else /* if (myColor == Color.BLACK) */ {
            if (row == 0) return this;
            else if (row == 6)
                if (chessboard.getPieceAt(col, row-1) == null && chessboard.getPieceAt(col, row-2) == null)
                    locations.add(new Location(col, row-2));
        }

        var dir = (myColor == Color.WHITE) ? 1 : -1;

        if (chessboard.getPieceAt(col, row+dir) == null) {
            locations.add(new Location(col, row+dir));
        }

        if (col < 7) piece_can_take(row+dir, col+1, myColor, false);
        if (col > 0) piece_can_take(row+dir, col-1, myColor, false);

        return this;
    }

    /**
     * Pattern to add the reachable locations across the row
     * @return the updated pattern
     */
    public MovePattern addRow() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();
        for (int i = col + 1; i < 8; i++)
            if (piece_can_take(row, i, myColor)) break;

        for (int i = col - 1; i >= 0; i--) {
            if (piece_can_take(row, i, myColor)) break;
        }
        return this;
    }

    /**
     * Pattern to add the reachable locations across the column
     * @return the updated pattern
     */
    public MovePattern addColumn() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();
        for (int i = row + 1; i < 8; i++)
            if (piece_can_take(i, col, myColor)) break;
        for (int i = row - 1; i >= 0; i--)
            if (piece_can_take(i, col, myColor)) break;

        return this;
    }

    /**
     * Pattern to add the reachable locations across the diagonals
     * @return the updated pattern
     */
    public MovePattern addDiagonals() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();

        for (int i = 0; i < 8; i++) {
            if (piece_can_take(row + i, col + i, myColor)) break;
        }
        for (int i = 0; i < 8; i++) {
            if (piece_can_take(row + i, col - i, myColor)) break;
        }
        for (int i = 0; i < 8; i++) {
            if (piece_can_take(row - i, col + i, myColor)) break;
        }
        for (int i = 0; i < 8; i++) {
            if (piece_can_take(row - i, col - i, myColor)) break;
        }
        return this;
    }

    /**
     * Pattern to add the locations reachable by a knight
     * @return the updated pattern
     */
    public MovePattern addKnight() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();
        piece_can_take(row + 2, col + 1, myColor);
        piece_can_take(row + 2, col - 1, myColor);
        piece_can_take(row + 1, col + 2, myColor);
        piece_can_take(row + 1, col - 2, myColor);
        piece_can_take(row - 2, col + 1, myColor);
        piece_can_take(row - 2, col - 1, myColor);
        piece_can_take(row - 1, col + 2, myColor);
        piece_can_take(row - 1, col - 2, myColor);
        return this;
    }

    /**
     * Pattern to add the locations reachable by a king
     * @return the updated pattern
     */
    public MovePattern addKing() {
        var col = pieceLocation.col();
        var row = pieceLocation.row();
        piece_can_take(row + 1, col + 1, myColor);
        piece_can_take(row + 1, col, myColor);
        piece_can_take(row + 1, col - 1, myColor);
        piece_can_take(row, col + 1, myColor);
        piece_can_take(row, col - 1, myColor);
        piece_can_take(row - 1, col + 1, myColor);
        piece_can_take(row - 1, col, myColor);
        piece_can_take(row - 1, col - 1, myColor);
        return this;
    }

    /**
     * Validates the MovePattern checking if the king will result under attack after the move.
     * Removes the wrong Locations
     * @return the updated pattern
     */
    public MovePattern validate(){
        ArrayList<Location> locations = new ArrayList<>(this.locations);
        for (Location dest : locations) {
            Chessboard nextMoveBoard = chessboard.clone();
            nextMoveBoard._make_move(pieceLocation, dest);
            if (chessboard.getKing(this.myColor).isUnderCheck(nextMoveBoard))
                this.locations.remove(dest);
        }
        return this;
    }
}
