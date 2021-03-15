package it.matlice.matlichess.controller;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.PieceView;
import it.matlice.stockfish.Stockfish;

import java.util.ArrayList;
import java.util.List;

public class StockfishPlayer implements PlayerInterface {
    private int depth;
    private int skill;

    private int delay = 0;

    public StockfishPlayer(int depth, int skill){
        this.depth = depth;
        this.skill = skill;
        Stockfish.getInstance();
    }

    @Override
    public List<Location> waitForUserMove(PieceColor side) throws InterruptedException {
        Stockfish.nSetOption("Use NNUE", String.valueOf(false)); // true or false // use neural network
        Stockfish.nSetOption("Contempt", String.valueOf(-100)); // -100, 100  // lower prefers draw
        Stockfish.nSetOption("Skill Level", String.valueOf(this.skill)); // 0, 20 // skill level, 0 is tough tho
        Stockfish.nSearchBestMove(depth, false);
        var move = Stockfish.nGetFoundNextMoveStr();
        Stockfish.nGetScore(true);
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
        Stockfish.nDbgDisplay();
    }

    @Override
    public void setTurn(PieceColor turn) {
    }
}
