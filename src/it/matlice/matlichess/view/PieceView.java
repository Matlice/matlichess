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

    public static ScreenLocation locationToPointer(Location l){
        int xCoord = l.col() * Settings.CHESSBOARD_SIZE/8;
        int yCoord = (7-l.row()) * Settings.CHESSBOARD_SIZE/8;
        return new ScreenLocation(xCoord, yCoord);
    }

    public static Location pointerToLocation(MouseEvent e){
        int col = e.getX()/(Settings.CHESSBOARD_SIZE/8);
        int row = (Settings.CHESSBOARD_SIZE - e.getY())/(Settings.CHESSBOARD_SIZE/8);
        if (0 <= col && col < 8 && 0 <= row && row < 8)
            return new Location(col, row);
        else
            throw new InvalidMoveException();
    }

    /**
     * Constructor for PieceView
     * @param pieceType path of the image
     */
    public PieceView(PieceType pieceType, Location location) {
        this.pieceType = pieceType;
        this.location = location;
    }

    public void draw(Graphics2D g2) {
        Settings.CBURNETT_PIECE[pieceType.index].accept(g2, locationToPointer(location), offset);
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
