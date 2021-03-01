package it.matlice.malichess;

import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;
import it.matlice.matlichess.model.Piece;
import it.matlice.matlichess.model.King;

import java.util.Map;
import java.util.function.Supplier;

public class ChessboardTest extends Chessboard {

    public ChessboardTest(){}

    public ChessboardTest(Chessboard c){

    }
    
    @Override
    public void _set_piece_at(Location loc, Piece p) {
        super._set_piece_at(loc, p);
    }

    @Override
    public void setPiece(Piece piece, Location loc) {
        super.setPiece(piece, loc);
    }

    @Override
    public void setPiece(Piece piece, String loc) {
        super.setPiece(piece, loc);
    }

    @Override
    public void setKing(King k, Location loc) {
        super.setKing(k, loc);
    }

    @Override
    public void setKing(King k, String loc) {
        super.setKing(k, loc);
    }

    @Override
    public Piece getPieceAt(Location loc) {
        return super.getPieceAt(loc);
    }

    @Override
    public Piece getPieceAt(int col, int row) {
        return super.getPieceAt(col, row);
    }

    @Override
    public Location getEnPassantTargetSquare() {
        return super.getEnPassantTargetSquare();
    }

    @Override
    public void setEnPassantTargetSquare(Location enPassantTargetSquare) {
        super.setEnPassantTargetSquare(enPassantTargetSquare);
    }

    @Override
    public void removePiece(Location location) {
        super.removePiece(location);
    }

    @Override
    public void resetHalfMoveClock() {
        super.resetHalfMoveClock();
    }

    @Override
    public void changeTurn() {
        super.changeTurn();
    }

    @Override
    public Piece _make_move(Location src, Location destination, Supplier<Piece> moveAction) {
        return super._make_move(src, destination, moveAction);
    }

    @Override
    public Piece move(Location src, Location destination) {
        return super.move(src, destination);
    }

    @Override
    public Piece move(String src, String destination) {
        return super.move(src, destination);
    }

    @Override
    public King getOpponentKing(Color c) {
        return super.getOpponentKing(c);
    }

    @Override
    public King getKing(Color c) {
        return super.getKing(c);
    }

    @Override
    public Map<String, Map<Piece, Location>> getPieces() {
        return super.getPieces();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String toFEN() {
        return super.toFEN();
    }
}
