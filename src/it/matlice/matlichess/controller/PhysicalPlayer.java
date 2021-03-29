package it.matlice.matlichess.controller;

import it.matlice.matlichess.GameState;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.ChessboardView;
import it.matlice.matlichess.view.ConfigurationPanel;
import it.matlice.matlichess.view.PieceView;
import it.matlice.matlichess.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * basic implementation of the main player
 */
public class PhysicalPlayer implements PlayerInterface {

    public static ConfigurationPanel getConfigurationInterface() {
        return new ConfigurationPanel() {
            @Override
            public PlayerInterface getInstance() {
                return new PhysicalPlayer();
            }

            @Override
            public void buildPanel() {

            }
        };
    }

    public static String getName() {
        return "Person";
    }

    @Override
    public List<Location> waitForUserMove() throws InterruptedException {
        return View.getInstance().getChessboardView().waitForUserMove();
    }

    @Override
    public void setColor(PieceColor color) {
        View.getInstance().getChessboardView().setColor(color);
    }

    @Override
    public void setPosition(ArrayList<PieceView> pieces) {
        View.getInstance().getChessboardView().setPosition(pieces);
    }

    @Override
    public void setMove(Location from, Location to) {
        View.getInstance().getChessboardView().setMove(from, to);
    }

    @Override
    public void setTurn(PieceColor turn) {
        View.getInstance().getChessboardView().setTurn(turn);
    }

    @Override
    public void interrupt() {
        View.getInstance().getChessboardView().interrupt();
    }

    @Override
    public boolean setState(GameState state, boolean generic, PlayerInterface opponent) {
        // solo se color è settato, altrimenti è spettatore
        boolean consensus = View.getInstance().getChessboardView().askForConsensus(state, generic || opponent instanceof PhysicalPlayer);
        return consensus && opponent == null || opponent instanceof PhysicalPlayer || opponent.setState(state, generic, consensus);
    }

    public boolean setState(GameState state, boolean generic, Boolean other_choice) {
        if (!other_choice) return false;
        return View.getInstance().getChessboardView().askForConsensus(state, generic);
    }

}
