package it.matlice.matlichess.model;

import it.matlice.matlichess.exceptions.ChessboardLocationException;
import it.matlice.matlichess.exceptions.InvalidMoveException;

public class Chessboard {

    private Piece[][] chessboard = new Piece[8][8];

    private void _set_piece_at(Location loc, Piece p){
        chessboard[loc.col()][loc.row()] = p;
    }

    public Piece getPieceAt(Location loc){
        return chessboard[loc.col()][loc.row()];
    }
    public Piece getPieceAt(int col, int row){
        return chessboard[col][row];
    }

    public void setPiece(Piece piece, Location loc){
        setPiece(piece, loc, false);
    }

    public void setPiece(Piece piece, Location loc, boolean isPromotion){
        if(getPieceAt(loc) != null && !isPromotion) throw new ChessboardLocationException();
        _set_piece_at(loc, piece);
    }

    public Piece move(Location src, Location destination){
        if(!getPieceAt(src).isPlaceAllowed(this, destination, src)) throw new InvalidMoveException();
        var capture = getPieceAt(destination);
        _set_piece_at(destination, getPieceAt(src));
        getPieceAt(src).hasBeenMoved();
        return capture;
    }
}
