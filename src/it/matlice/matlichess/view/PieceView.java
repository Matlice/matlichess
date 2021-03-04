package it.matlice.matlichess.view;

import it.matlice.matlichess.model.Location;
import it.matlice.settings.Settings;

import java.awt.*;

public class PieceView{

    private PieceType pieceType;
    private Location location;

    /**
     * Constructor for PieceView
     * @param pieceType path of the image
     */
    public PieceView(PieceType pieceType, Location location) {
        this.pieceType = pieceType;
        this.location = location;
    }

    public void draw(Graphics2D g2) {
        //todo change dimension to location
        Settings.CBURNETT_PIECE[pieceType.index].accept(g2, location);
    }

}
