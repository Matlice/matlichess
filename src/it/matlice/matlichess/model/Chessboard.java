package it.matlice.matlichess.model;

import it.matlice.matlichess.exceptions.ChessboardLocationException;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.model.pieces.King;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The game field. It contains information about the location of the pieces on it.
 * Here the pieces will move to proceed on the game
 */
public class Chessboard {

    private Piece[][] chessboard = new Piece[8][8];
    private Map<String, Map<Piece, Location>> pieces = new HashMap<>();
    private King[] kings = new King[2];

    /**
     * Puts a {@link Piece} on a certain box in the chessboard.
     * @param loc the {@link Location} of the box
     * @param p the chess {@link Piece} to put
     */
    private void _set_piece_at(Location loc, Piece p) {
        chessboard[loc.col()][loc.row()] = p;
        if(!pieces.containsKey(p.getName())) pieces.put(p.getName(), new HashMap<Piece, Location>());
        pieces.get(p.getName()).put(p, loc);
    }

    /**
     * Puts a {@link Piece} on a certain box in the chessboard
     * @param piece the chess {@link Piece} to put
     * @param loc the {@link Location} of the box
     */
    public void setPiece(Piece piece, Location loc) {
        if (getPieceAt(loc) != null) throw new ChessboardLocationException();
        _set_piece_at(loc, piece);
    }

    public void setPiece(Piece piece, String loc) {
        this.setPiece(piece, new Location(loc));
    }

    /**
     * Puts the {@link King} on a certain box in the chessboard
     * @param k the {@link King} to put
     * @param loc the {@link Location} of the box
     */
    public void setKing(King k, Location loc) {
        this.setPiece(k, loc);
        this.kings[k.getColor().index] = k;
    }

    public void setKing(King k, String loc) {
        this.setKing(k, new Location(loc));
    }

    /**
     * Returns the piece located in a certain chessboard box
     * @param loc the {@link Location} of the box
     * @return the {@link Piece} if the box contains one, else null
     */
    public Piece getPieceAt(Location loc) {
        return chessboard[loc.col()][loc.row()];
    }

    /**
     * Returns the piece located in a certain chessboard box
     * @param col the column index of the box
     * @param row the row index of the box
     * @return the {@link Piece} if the box contains one, else null
     */
    public Piece getPieceAt(int col, int row) {
        return chessboard[col][row];
    }

    /**
     * Removes a Piece from the chessboard reference map
     * @param toRemove the {@link Piece} to remove
     */
    private void _removePiece(Piece toRemove){
        if(toRemove == null) return;
        pieces.get(toRemove.getName()).remove(toRemove);
    }

    /**
     * Removes a Piece from the chessboard
     * @param location the {@link Location} of the Piece to remove
     */
    public void removePiece(Location location){
        _removePiece(getPieceAt(location));
        chessboard[location.col()][location.row()] = null;
    }

    /**
     * Takes the piece in a {@link Location} and moves it to a new box
     * If the final box is occupied, it removes the old piece and replaces it with the new one
     * @param src the source {@link Location}
     * @param destination the final {@link Location}
     * @return the taken {@link Piece} if exists, else null
     */
    public Piece _make_move(Location src, Location destination){
        Piece toCapture = getPieceAt(destination);
        removePiece(destination);
        getPieceAt(src).hasBeenMoved(this, destination);
        _set_piece_at(destination, getPieceAt(src));
        chessboard[src.col()][src.row()] = null;
        return toCapture;
    }

    /**
     * Checks if a piece is allowed to move to a certain box, then takes the piece in a {@link Location} and moves it to the new box.
     * If the final box is occupied, it removes the old piece and replaces it with the new one
     * @param src the source {@link Location}
     * @param destination the final {@link Location}
     * @return the taken {@link Piece} if exists, else null
     */
    public Piece move(Location src, Location destination) {
        assert kings[0] != null && kings[1] != null;
        if (!getPieceAt(src).isMoveAllowed(this, destination, src)) throw new InvalidMoveException();
        return _make_move(src, destination);
    }

    public Piece move(String src, String destination) {
        return move(new Location(src), new Location(destination));
    }


    /**
     * Returns the opponent's King
     * @param c the {@link Color} of the player
     * @return The opponent's {@link King}
     */
    public King getOpponentKing(Color c){
        return this.kings[c.opponent().index];
    }

    /**
     * Returns the player's King
     * @param c the {@link Color} of the player
     * @return The player's {@link King}
     */
    public King getKing(Color c){
        return this.kings[c.index];
    }

    /**
     * Describes a functional interface
     * @param cb {@link BiConsumer}
     */
    private void forEachPiece(BiConsumer<Piece, Location> cb){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if(getPieceAt(i, j) != null) cb.accept(getPieceAt(i, j), new Location(i, j));
    }

    /**
     * Returns a copy of the chessboard
     * @return copy of the {@link Chessboard}
     */
    public Chessboard clone(){
        Chessboard cloned = new Chessboard();
        this.forEachPiece(cloned::setPiece);
        cloned.kings = new King[]{this.kings[0], this.kings[1]};
        return cloned;
    }

    /**
     * Returns all the pieces on the chessboard
     * @return a map containing all the {@link Piece}s
     */
    public Map<String, Map<Piece, Location>> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            s.append("  +---+---+---+---+---+---+---+---+\n");
            for (int j = 0; j < 8; j++) {
                if(j == 0) s.append(i).append(" ");

                if(this.getPieceAt(j, i) == null) s.append("|   ");
                else s.append("| ").append(this.getPieceAt(j, i).getShortName()).append(" ");
            }
            s.append("|\n");
        }
        s.append("  +---+---+---+---+---+---+---+---+\n    A   B   C   D   E   F   G   H");
        return s.toString();
    }
}
