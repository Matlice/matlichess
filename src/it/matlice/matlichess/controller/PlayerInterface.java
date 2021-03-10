package it.matlice.matlichess.controller;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.PieceView;

import java.util.ArrayList;
import java.util.List;

public interface PlayerInterface {
    public List<Location> waitForUserMove(PieceColor side) throws InterruptedException;
    public void setPosition(ArrayList<PieceView> pieces);
    public void setTurn(PieceColor turn);
}
