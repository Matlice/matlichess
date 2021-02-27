package it.matlice.matlichess.model;

import it.matlice.matlichess.exceptions.ChessboardLocationException;
import it.matlice.matlichess.exceptions.InvalidTurnException;
import it.matlice.matlichess.model.pieces.King;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The game field. It contains information about the location of the pieces on it.
 * Here the pieces will move to proceed on the game
 */
public class Chessboard {

    private Piece[][] chessboard = new Piece[8][8];
    private Map<String, Map<Piece, Location>> pieces = new HashMap<>();
    private King[] kings = new King[2];
    private Color turn = Color.WHITE;

    private Location enPassantTargetSquare = null;
    private Location tmpEnPassantTargetSquare = null;

    // this is the number of halfMoves since the last capture or pawn advance.
    // The reason for this field is that the value is used in the fifty-move rule.
    // TODO condizioni di patta
    private int halfMoveClock = 0;

    // the number of the full move. It starts at 1, and is incremented after Black's move.
    // It is used in the creation of the FEN Notation
    private int fullMoveNumber = 1;

    /**
     * Puts a {@link Piece} on a certain box in the chessboard.
     * @param loc the {@link Location} of the box
     * @param p the chess {@link Piece} to put
     */
    public void _set_piece_at(Location loc, Piece p) {
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

    /**
     * Puts a {@link Piece} on a certain box in the chessboard
     * @param piece the chess {@link Piece} to put
     * @param loc a string that indicates the coordinate of the chess box
     */
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

    /**
     * Puts the {@link King} on a certain box in the chessboard
     * @param k the {@link King} to put
     * @param loc a string that indicates the coordinate of the chess box
     */
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
     * Getter for the variable. If a pawn has moved by two squares in the last move, the variable will contain the skipped square, else null
     * @return the enPassant Target Square
     */
    public Location getEnPassantTargetSquare() {
        return enPassantTargetSquare;
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
     * Resets the number of consecutive moves without taking a piece or pawn pushes
     */
    public void resetHalfMoveClock() {
        halfMoveClock = 0;
    }

    /**
     * Change the turn
     */
    public void changeTurn(){
        turn = turn.opponent();
    }

    /**
     * Takes the piece in a {@link Location} and moves it to a new box
     * If the final box is occupied, it removes the old piece and replaces it with the new one
     * @param src the source {@link Location}
     * @param destination the final {@link Location}
     * @return the taken {@link Piece} if exists, else null
     */
    public Piece _make_move(Location src, Location destination, MoveAction moveAction){
        if(!getPieceAt(src).getColor().equals(turn)) throw new InvalidTurnException();

        halfMoveClock += 1; // increment now, capturing a piece or pushing a pawn will reset it
        if (turn == Color.BLACK) fullMoveNumber += 1; // increments the number of the total moves
        changeTurn();
        enPassantTargetSquare = null;

        Piece toCapture = getPieceAt(destination); //if there's no piece taken, it will be null
        if (toCapture != null) removePiece(destination);

        Piece possibleCapture = moveAction.action();
        if (possibleCapture != null) toCapture = possibleCapture;

        getPieceAt(src).hasBeenMoved(this);

        if (toCapture != null) resetHalfMoveClock();

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
        // TODO check if its correct player turn
        assert kings[0] != null && kings[1] != null;
        MoveAction action = getPieceAt(src).getAction(this, destination, src);
        return _make_move(src, destination, action);
    }

    /**
     * Checks if a piece is allowed to move to a certain box, then takes the piece in a {@link Location} and moves it to the new box.
     * If the final box is occupied, it removes the old piece and replaces it with the new one
     * @param src String containing the official notation of the Location
     * @param destination the final {@link Location} as string ("A4")
     * @return the taken {@link Piece} if exists, else null
     */
    public Piece move(String src, String destination) {
        return move(new Location(src), new Location(destination));
    }

    /**
     * Sets the square skipped by the pawn that has moved by two squares
     * @param enPassantTargetSquare
     */
    public void setEnPassantTargetSquare(Location enPassantTargetSquare) {
        this.enPassantTargetSquare = enPassantTargetSquare;
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


        final King[] kings = new King[]{null, null};
        cloned.kings = new King[]{this.kings[0], this.kings[1]};
        this.forEachPiece((Piece p, Location l) -> {
            var c = p.clone();
            cloned.setPiece(c, l);
            if(c instanceof King)
                kings[c.getColor().index] = (King) c;
        });
        cloned.kings = kings;


        cloned.enPassantTargetSquare = this.enPassantTargetSquare;
        cloned.fullMoveNumber = this.fullMoveNumber;
        cloned.halfMoveClock = this.halfMoveClock;
        cloned.turn = this.turn;
        cloned.tmpEnPassantTargetSquare = this.tmpEnPassantTargetSquare;
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
                if(j == 0) s.append(i+1).append(" ");

                if(this.getPieceAt(j, i) == null) s.append("|   ");
                else s.append("| ").append(this.getPieceAt(j, i).getShortName()).append(" ");
            }
            s.append("|\n");
        }
        s.append("  +---+---+---+---+---+---+---+---+\n    A   B   C   D   E   F   G   H");
        return s.toString();
    }

    /**
     * Returns the Forsyth–Edwards Notation (FEN) used for describing a particular board position of a chess game
     * The purpose of FEN is to provide all the necessary information to restart a game from a particular position
     * @return the string representation of the FEN
     */
    public String toFEN() {
        StringBuilder fen = new StringBuilder();

        // base position
        for(int r=7; r>=0; r--) {
            int emptyCounter = 0;
            for (int c=0; c<8; c++) {
                Piece piece = chessboard[c][r];
                if (piece != null) {
                    if (emptyCounter != 0) {
                        fen.append(emptyCounter);
                        emptyCounter = 0;
                    }
                    fen.append(piece.getShortName());
                } else emptyCounter++;
            }
            if (emptyCounter != 0) fen.append(emptyCounter);
            if (r != 0) fen.append("/");
        }

        // turn
        fen.append(" ");
        fen.append(turn.equals(Color.WHITE) ? "w" : "b");

        // castling
        StringBuilder castling = new StringBuilder();
        if (this.kings[Color.WHITE.index].isKingCastlingAvailable(this)) castling.append("K");
        if (this.kings[Color.WHITE.index].isQueenCastlingAvailable(this)) castling.append("Q");
        if (this.kings[Color.BLACK.index].isKingCastlingAvailable(this)) castling.append("k");
        if (this.kings[Color.BLACK.index].isQueenCastlingAvailable(this)) castling.append("q");

        fen.append(" ");
        fen.append(castling.toString().isBlank() ? "-" : castling);

        // en passant
        fen.append(" ");
        fen.append(enPassantTargetSquare != null ? enPassantTargetSquare.toString().toLowerCase() : "-");

        // move number
        fen.append(" ");
        fen.append(halfMoveClock);
        fen.append(" ");
        fen.append(fullMoveNumber);

        return fen.toString();
    }

}
