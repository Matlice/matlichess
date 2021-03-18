package it.matlice.matlichess.view;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.settings.Settings;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PieceView{

    private PieceType pieceType;
    private Location location;
    private ScreenLocation offset = new ScreenLocation();

    public static ScreenLocation locationToPointer(Location l, boolean invert){
        int xCoord, yCoord;
        if (!invert) {
            xCoord = l.col() * Settings.CHESSBOARD_SIZE/8;
            yCoord = (7-l.row()) * Settings.CHESSBOARD_SIZE/8;
        } else {
            xCoord = (7-l.col()) * Settings.CHESSBOARD_SIZE/8;
            yCoord = l.row() * Settings.CHESSBOARD_SIZE/8;
        }
        return new ScreenLocation(xCoord, yCoord);
    }

    public static ScreenLocation locationToPointer(Location l) {
        return locationToPointer(l, false);
    }

    public static Location pointerToLocation(MouseEvent e, boolean invert){
        int row, col;
        if (!invert) {
             col = e.getX()/(Settings.CHESSBOARD_SIZE/8);
            row = (Settings.CHESSBOARD_SIZE - e.getY())/(Settings.CHESSBOARD_SIZE/8);
        } else {
            col = (Settings.CHESSBOARD_SIZE - e.getX())/(Settings.CHESSBOARD_SIZE/8);
            row = e.getY()/(Settings.CHESSBOARD_SIZE/8);
        }

        if (0 <= col && col < 8 && 0 <= row && row < 8)
            return new Location(col, row);
        else
            throw new InvalidMoveException();
    }

    public static Location pointerToLocation(MouseEvent e) {
        return pointerToLocation(e, false);
    }

    /**
     * Constructor for PieceView
     * @param pieceType path of the image
     */
    public PieceView(PieceType pieceType, Location location) {
        this.pieceType = pieceType;
        this.location = location;
    }

    public void draw(Graphics2D g2, boolean invert) {
        Settings.CBURNETT_PIECE[pieceType.index].accept(g2, locationToPointer(location, invert), offset);
    }

    public void draw(Graphics2D g2) {
        this.draw(g2, false);
    }

    public Location getLocation(){
        return this.location;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setOffset(ScreenLocation d){
        this.offset = d;
    }

    public void resetOffset(){
        this.offset = new ScreenLocation();
    }

}
