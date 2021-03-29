package it.matlice.matlichess.model;

import it.matlice.matlichess.GameState;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.exceptions.ChessboardLocationException;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.exceptions.InvalidTurnException;
import it.matlice.matlichess.model.pieces.King;
import it.matlice.matlichess.model.pieces.Pawn;
import it.matlice.matlichess.model.pieces.Queen;
import it.matlice.settings.Settings;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * The game field. It contains information about the location of the pieces on it.
 * Here the pieces will move to proceed on the game
 * This class is the representative of the model in the MCV architecture
 */
public class Chessboard {

    private final Piece[][] chessboard = new Piece[8][8];
    private final Class<? extends Piece>[] promotions = new Class[]{Queen.class, Queen.class};
    private Map<String, Map<Piece, Location>> pieces = new HashMap<>();
    private King[] kings = new King[2];
    private PieceColor turn = PieceColor.WHITE;
    private Location enPassantTargetSquare = null;
    //This map is needed to implement a quick algorithm for three repetition rule
    private HashMap<String, Integer> repeatedPositions = new HashMap<>();
    // this is the number of halfMoves since the last capture or pawn advance.
    // The reason for this field is that the value is used in the fifty-move rule.
    private int halfMoveClock = 0;
    // the number of the full move. It starts at 1, and is incremented after Black's move.
    // It is used in the creation of the FEN Notation
    private int fullMoveNumber = 1;

    /**
     * Returns a traditional start game chessboard
     *
     * @return Traditional Chessboard
     */
    public static Chessboard getDefault() {
        Chessboard c = new Chessboard();
        c.setPosition(Settings.STARTING_POSITION_FEN);
        return c;
    }

    /**
     * Returns the hashmap that represents how many times a position has been played in the current game
     * Used to spot repetitions (for endgame)
     *
     * @return
     */
    public HashMap<String, Integer> getRepeatedPositions() {
        return repeatedPositions;
    }

    /**
     * Puts a {@link Piece} on a certain box in the chessboard, WITHOUT checking whether the destination square is empty.
     *
     * @param loc the {@link Location} of the box
     * @param p   the chess {@link Piece} to put
     * @see this.setPiece(Piece, Location)
     */
    public void _set_piece_at(Location loc, Piece p) {
        chessboard[loc.col()][loc.row()] = p;
        if (!pieces.containsKey(p.getName())) pieces.put(p.getName(), new HashMap<>());
        pieces.get(p.getName()).put(p, loc);
    }

    /**
     * Puts a {@link Piece} on a certain box in the chessboard, CHECKING whether the destination square is empty
     *
     * @param piece the chess {@link Piece} to put
     * @param loc   the {@link Location} of the box
     * @see this._set_piece_at(Location, Piece)
     */
    public void setPiece(Piece piece, Location loc) {
        if (getPieceAt(loc) != null) throw new ChessboardLocationException();
        _set_piece_at(loc, piece);
    }

    /**
     * Puts a {@link Piece} on a certain box in the chessboard
     *
     * @param piece the chess {@link Piece} to put
     * @param loc   a string that indicates the coordinate of the chess box
     */
    public void setPiece(Piece piece, String loc) {
        this.setPiece(piece, new Location(loc));
    }

    /**
     * Puts the {@link King} on a certain box in the chessboard
     *
     * @param k   the {@link King} to put
     * @param loc the {@link Location} of the box
     */
    public void setKing(King k, Location loc) {
        this.setPiece(k, loc);
        this.kings[k.getColor().index] = k;
    }

    /**
     * Puts the {@link King} on a certain box in the chessboard
     *
     * @param k   the {@link King} to put
     * @param loc a string that indicates the coordinate of the chess box
     */
    public void setKing(King k, String loc) {
        this.setKing(k, new Location(loc));
    }

    /**
     * Returns the piece located in a certain chessboard box
     *
     * @param loc the {@link Location} of the box
     * @return the {@link Piece} if the box contains one, else null
     */
    public Piece getPieceAt(Location loc) {
        return chessboard[loc.col()][loc.row()];
    }

    /**
     * Returns the piece located in a certain chessboard box
     *
     * @param col the column index of the box
     * @param row the row index of the box
     * @return the {@link Piece} if the box contains one, else null
     */
    public Piece getPieceAt(int col, int row) {
        return chessboard[col][row];
    }

    /**
     * Return the whole chessboard matrix
     *
     * @return chessboard matrix
     */
    public Piece[][] getChessboardMatrix() {
        return this.chessboard;
    }

    /**
     * Getter for the variable. If a pawn has moved by two squares in the last move, the variable will contain the skipped square, else null
     *
     * @return the enPassant Target Square
     */
    public Location getEnPassantTargetSquare() {
        return enPassantTargetSquare;
    }

    /**
     * Sets the square skipped by the pawn that has moved by two squares
     *
     * @param enPassantTargetSquare square skipped by the pawn that has moved by two squares
     */
    public void setEnPassantTargetSquare(Location enPassantTargetSquare) {
        this.enPassantTargetSquare = enPassantTargetSquare;
    }

    /**
     * Returns the type of the next pawn promotion for white and black
     *
     * @return an array of length 2 with the promotion types
     */
    public Class<? extends Piece>[] getPromotions() {
        return promotions;
    }

    /**
     * Removes a Piece from the chessboard reference map
     *
     * @param toRemove the {@link Piece} to remove
     */
    private void _removePiece(Piece toRemove) {
        if (toRemove == null) return;
        pieces.get(toRemove.getName()).remove(toRemove);
    }

    /**
     * Removes a Piece from the chessboard
     *
     * @param location the {@link Location} of the Piece to remove
     */
    public void removePiece(Location location) {
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
     * Set the halfMoveClock to a specific number, used for initialisation of a new position
     *
     * @param halfMoveClock int of the half moves
     */
    public void setHalfMoveClock(int halfMoveClock) {
        this.halfMoveClock = halfMoveClock;
    }

    /**
     * Set the fullMoveNumber to a specific number, used for initialisation of a new position
     *
     * @param fullMoveNumber int of the full moves
     */
    public void setFullMoveNumber(int fullMoveNumber) {
        this.fullMoveNumber = fullMoveNumber;
    }

    /**
     * Getter for the turn
     *
     * @return which player has to move
     */
    public PieceColor getTurn() {
        return turn;
    }

    /**
     * Setter for the turn
     *
     * @param t the color of the player's turn
     */
    public void setTurn(PieceColor t) {
        turn = t;
    }

    /**
     * Changes the turn
     */
    protected void changeTurn() {
        turn = turn.opponent();
    }

    /**
     * Takes the piece in a {@link Location} and moves it to a new box
     * If the final box is occupied, it removes the old piece and replaces it with the new one
     *
     * @param src         the source {@link Location}
     * @param destination the final {@link Location}
     * @return the taken {@link Piece} if exists, else null
     */
    public Piece _make_move(Location src, Location destination, Supplier<Piece> moveAction) {
        if (!getPieceAt(src).getColor().equals(turn)) throw new InvalidTurnException();

        halfMoveClock += 1; // increment now, capturing a piece or pushing a pawn will reset it
        if (turn == PieceColor.BLACK) fullMoveNumber += 1; // increments the number of the total moves
        changeTurn();
        enPassantTargetSquare = null;

        Piece toCapture = getPieceAt(destination); //if there's no piece taken, it will be null
        if (toCapture != null) removePiece(destination);

        Piece possibleCapture = moveAction.get();
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
     *
     * @param src         the source {@link Location}
     * @param destination the final {@link Location}
     * @return the captured {@link Piece} if exists, else null
     */
    public Piece move(Location src, Location destination) {
        assert kings[0] != null && kings[1] != null;
        if (getPieceAt(src) == null) throw new InvalidMoveException();
        Supplier<Piece> action = getPieceAt(src).getAction(this, destination, src);
        Piece captured = _make_move(src, destination, action);
        saveFEN(toFEN(false));
        return captured;
    }

    /**
     * Checks if a piece is allowed to move to a certain box, then takes the piece in a {@link Location} and moves it to the new box.
     * If the final box is occupied, it removes the old piece and replaces it with the new one
     *
     * @param src         String containing the official notation of the Location
     * @param destination the final {@link Location} as string ("A4")
     * @return the taken {@link Piece} if exists, else null
     * @see this.move(Location, Location)
     */
    public Piece move(String src, String destination) {
        return move(new Location(src), new Location(destination));
    }

    /**
     * Return all the available moves of a piece in a certain Location
     *
     * @param l the location where the piece is
     * @return all the available moves of the piece
     */
    public MoveList getAvailableMoves(Location l) {
        if (this.getPieceAt(l) != null) return this.getPieceAt(l).getAvailableMoves(this, l);
        return null;
    }

    /**
     * Returns whether a move is valid, it's done by doing that move and checking if an InvalidMoveException is thrown
     *
     * @param from source of the move
     * @param to   destination of the move
     * @return true if the move is valid
     */
    public boolean isMoveValid(Location from, Location to) {
        Chessboard c = this.clone();
        try {
            c.move(from, to);
            return true;
        } catch (InvalidMoveException e) {
            return false;
        }
    }

    /**
     * Set the promotion type for the player
     *
     * @param color The color of the piece to promote
     * @param klass The type of the piece setted to promote to
     */
    public void setPromotion(PieceColor color, Class<? extends Piece> klass) {
        promotions[color.index] = klass;
    }

    /**
     * Removes the piece and replaces it with a new piece
     *
     * @param location Location of the piece
     * @param color    Color of the piece
     */
    public void promote(Location location, PieceColor color) {
        removePiece(location); // remove the pawn
        try {
            setPiece((Piece) promotions[color.index].getConstructors()[0].newInstance(color), location);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns whether the move is a pawn promotion
     * Used to decide whether to ask a player to which piece to promote
     *
     * @param from source of the move
     * @param to   destination of the move
     * @return true if the specified move is a promotion
     */
    public boolean isPromoting(Location from, Location to) {
        Piece toMove = getPieceAt(from);
        if (!(toMove instanceof Pawn)) return false;
        if (!isMoveValid(from, to)) return false;
        if (toMove.getColor().equals(PieceColor.WHITE) && to.row() == 7) return true;
        return toMove.getColor().equals(PieceColor.BLACK) && to.row() == 0;
    }

    /**
     * Returns the opponent's King
     *
     * @param c the {@link PieceColor} of the player
     * @return The opponent's {@link King}
     */
    public King getOpponentKing(PieceColor c) {
        return this.kings[c.opponent().index];
    }

    /**
     * Returns the player's King
     *
     * @param c the {@link PieceColor} of the player
     * @return The player's {@link King}
     */
    public King getKing(PieceColor c) {
        return this.kings[c.index];
    }

    /**
     * Describes a functional interface
     *
     * @param cb {@link BiConsumer}
     */
    private void forEachPiece(BiConsumer<Piece, Location> cb) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (getPieceAt(i, j) != null) cb.accept(getPieceAt(i, j), new Location(i, j));
    }

    /**
     * Returns a copy of the chessboard
     *
     * @return copy of the {@link Chessboard}
     */
    public Chessboard clone() {
        Chessboard cloned = new Chessboard();

        final King[] kings = new King[]{null, null};
        cloned.kings = new King[]{this.kings[0], this.kings[1]};
        this.forEachPiece((Piece p, Location l) -> {
            Piece c = p.clone();
            cloned.setPiece(c, l);
            if (c instanceof King)
                kings[c.getColor().index] = (King) c;
        });
        cloned.kings = kings;

        cloned.enPassantTargetSquare = this.enPassantTargetSquare;
        cloned.fullMoveNumber = this.fullMoveNumber;
        cloned.halfMoveClock = this.halfMoveClock;
        cloned.turn = this.turn;
        return cloned;
    }

    /**
     * Returns all the pieces on the chessboard
     *
     * @return a map containing all the {@link Piece}s
     */
    public Map<String, Map<Piece, Location>> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            s.append("  +---+---+---+---+---+---+---+---+\n");
            for (int j = 0; j < 8; j++) {
                if (j == 0) s.append(i + 1).append(" ");

                if (this.getPieceAt(j, i) == null) s.append("|   ");
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
     *
     * @param complete set true if you want the compete fen, false to cut the move numbers
     * @return the string representation of the FEN
     */
    public String toFEN(boolean complete) {
        StringBuilder fen = new StringBuilder();

        // base position
        for (int r = 7; r >= 0; r--) {
            int emptyCounter = 0;
            for (int c = 0; c < 8; c++) {
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
        fen.append(turn.equals(PieceColor.WHITE) ? "w" : "b");

        // castling
        StringBuilder castling = new StringBuilder();
        if (this.kings[PieceColor.WHITE.index].isKingCastlingAvailable(this)) castling.append("K");
        if (this.kings[PieceColor.WHITE.index].isQueenCastlingAvailable(this)) castling.append("Q");
        if (this.kings[PieceColor.BLACK.index].isKingCastlingAvailable(this)) castling.append("k");
        if (this.kings[PieceColor.BLACK.index].isQueenCastlingAvailable(this)) castling.append("q");

        fen.append(" ");
        fen.append(castling.toString().isBlank() ? "-" : castling);

        // en passant
        fen.append(" ");
        fen.append(enPassantTargetSquare != null ? enPassantTargetSquare.toString().toLowerCase() : "-");

        if (!complete) return fen.toString();

        // move number
        fen.append(" ");
        fen.append(halfMoveClock);
        fen.append(" ");
        fen.append(fullMoveNumber);

        return fen.toString();
    }

    public String toFEN() {
        return toFEN(true);
    }

    /**
     * Saves in a variable how many times is reached the same position
     *
     * @param fen the relative FEN of the position
     */
    public void saveFEN(String fen) {
        if (!repeatedPositions.containsKey(fen)) repeatedPositions.put(fen, 1);
        else repeatedPositions.put(fen, repeatedPositions.get(fen) + 1);
    }

    /**
     * Imports a position from a FEN
     *
     * @param fen the String representation of the FEN
     */
    public void setPosition(String fen) {
        repeatedPositions = new HashMap<>();
        pieces = new HashMap<>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                this.chessboard[i][j] = null;

        this.kings = new King[]{null, null};

        FenReader f = FenReader.PIECE_POSITION;
        int c = 0;
        while (f != FenReader.FINISHED) {
            try {
                f = f.action(this, fen.charAt(c++));
            } catch (Exception e) {
                break;
            }
        }
    }

    /**
     * Setter for the repeatedPositions attribute, used for draw by repetition
     * Note that the pos_fen[i] position has appeared times[i] times etc...
     *
     * @param pos_fen the array of positions
     * @param times   the number of time the positions has appeared
     */
    public void setPositions(String[] pos_fen, Integer[] times) {
        assert times.length == pos_fen.length;
        for (int i = 0; i < pos_fen.length; i++)
            this.repeatedPositions.put(pos_fen[i], times[i]);
    }

    /**
     * Notifies if the match is not over, if it is in a draw position or if a player has won
     *
     * @return the state of the game
     */
    public GameState getGameState() {
        if (halfMoveClock == 50) {
            //System.out.println("DRAW by 50 moves");
            return GameState.DRAW;
        }
        if (repeatedPositions.containsValue(3)) {
            //System.out.println("DRAW by repetition");
            return GameState.DRAW;
        }
        ArrayList<Location> allMoves = new ArrayList<>();
        for (Map<Piece, Location> family : getPieces().values()) {
            for (Map.Entry<Piece, Location> entry : family.entrySet()) {
                if (entry.getKey().getColor().equals(turn))
                    allMoves.addAll(entry.getKey().getAvailableMoves(this, entry.getValue()).keySet());
            }
        }
        if (allMoves.isEmpty())
            if (getKing(turn).isUnderCheck(this, getPieces().get("King").get(getKing(turn)))) {
                if (turn.equals(PieceColor.BLACK)) {
                    //System.out.println("WIN WHITE by checkmate");
                    return GameState.WHITE_WIN;
                }
                //System.out.println("WIN BLACK by checkmate");
                return GameState.BLACK_WIN;
            } else {
                //System.out.println("DRAW by stalemate");
                return GameState.DRAW;
            }
        return GameState.PLAYING;
    }

}
