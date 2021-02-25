package it.matlice.matlichess.model;

import it.matlice.matlichess.exceptions.ChessboardLocationException;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.model.pieces.King;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Chessboard {

    private Piece[][] chessboard = new Piece[8][8];
    private Map<String, Map<Piece, Location>> pieces = new HashMap<>();
    private King[] kings = new King[2];

    private void _set_piece_at(Location loc, Piece p) {
        chessboard[loc.col()][loc.row()] = p;
        if(!pieces.containsKey(p.getName())) pieces.put(p.getName(), new HashMap<Piece, Location>());
        pieces.get(p.getName()).put(p, loc);
    }

    public Piece getPieceAt(Location loc) {
        return chessboard[loc.col()][loc.row()];
    }

    public Piece getPieceAt(int col, int row) {
        return chessboard[col][row];
    }

    public void setPiece(Piece piece, Location loc) {
        setPiece(piece, loc, false);
    }

    public void setPiece(Piece piece, Location loc, boolean isPromotion) {
        if (getPieceAt(loc) != null && !isPromotion) throw new ChessboardLocationException();
        _set_piece_at(loc, piece);
    }

    public void setKing(King k, Location loc) {
        this.setPiece(k, loc);
        this.kings[k.getColor().index] = k;
    }

    public Piece _make_move(Location src, Location destination){
        var capture = getPieceAt(destination);
        _set_piece_at(destination, getPieceAt(src));
        getPieceAt(src).hasBeenMoved();
        if(capture != null)
            pieces.get(capture.getName()).remove(capture);
        return capture;
    }

    public Piece move(Location src, Location destination) {
        assert kings[0] != null && kings[1] != null;
        if (!getPieceAt(src).isPlaceAllowed(this, destination, src)) throw new InvalidMoveException();
        return _make_move(src, destination);
    }

    public King getOpponentKing(Color c){
        return this.kings[c.opponent().index];
    }

    public King getKing(Color c){
        return this.kings[c.index];
    }

    public void forEachPiece(BiConsumer<Piece, Location> cb){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if(getPieceAt(i, j) != null) cb.accept(getPieceAt(i, j), new Location(i, j));
    }

    public Chessboard clone(){
        var ret = new Chessboard();
        this.forEachPiece(ret::setPiece);
        ret.kings = new King[]{this.kings[0], this.kings[1]};
        return ret;
    }

    public Map<String, Map<Piece, Location>> getPieces() {
        return pieces;
    }
}
