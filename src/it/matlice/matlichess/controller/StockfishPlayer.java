package it.matlice.matlichess.controller;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.PieceView;
import it.matlice.stockfish.Stockfish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockfishPlayer implements PlayerInterface {
    private int depth = 20;

    private int delay = 100;

    public StockfishPlayer(int depth){
        this.depth = depth;
        Stockfish.getInstance();
    }

    @Override
    public List<Location> waitForUserMove(PieceColor side) throws InterruptedException {
        Stockfish.nSearchBestMove(depth, false);
        var move = Stockfish.nGetFoundNextMoveStr();
        if (move.length() == 5) {
            String promotion = move.substring(4, 5);
            Game.getInstance().setPromotion(promotion);
        }
        Thread.sleep(delay);
        return Location.fromExtendedMove(move.substring(0, 4));
    }

    @Override
    public void setPosition(ArrayList<PieceView> pieces) {
        if(Game.hasInstance()) {
            Stockfish.nSetPosition(Game.getInstance().getPositionFen());
        }
        // Stockfish.nDbgDisplay();
    }

    @Override
    public void setTurn(PieceColor turn) {
    }
}
