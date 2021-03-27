package it.matlice.matlichess.controller;

import it.matlice.matlichess.GameState;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.ConfigurationPanel;
import it.matlice.matlichess.view.PieceView;
import it.matlice.matlichess.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * basic implementation of the main player
 */
public class PhysicalPlayer implements PlayerInterface {

    public static ConfigurationPanel getConfigurationInterface(){
        return new ConfigurationPanel() {
            @Override
            public PlayerInterface getInstance() {
                return View.getInstance().getPlayerInterface();
            }

            @Override
            public void buildPanel() {

            }
        };
    }

    public static String getName() {return "Person";}

    @Override
    public List<Location> waitForUserMove() throws InterruptedException {
        return View.getInstance().getPlayerInterface().waitForUserMove();
    }

    @Override
    public void setColor(PieceColor color) {
        View.getInstance().getPlayerInterface().setColor(color);
    }

    @Override
    public void setPosition(ArrayList<PieceView> pieces) {
        View.getInstance().getPlayerInterface().setPosition(pieces);
    }

    @Override
    public void setMove(Location from, Location to) {
        View.getInstance().getPlayerInterface().setMove(from, to);
    }

    @Override
    public void setTurn(PieceColor turn) {
        View.getInstance().getPlayerInterface().setTurn(turn);
    }

    @Override
    public void interrupt() {
        View.getInstance().getPlayerInterface().interrupt();
    }

    @Override
    public boolean setState(GameState state, boolean generic, PlayerInterface opponent) {
        return View.getInstance().getPlayerInterface().setState(state, generic, opponent);
    }

    @Override
    public boolean setState(GameState state, boolean generic, Boolean other_result) {
        return View.getInstance().getPlayerInterface().setState(state, generic, other_result);
    }
}
