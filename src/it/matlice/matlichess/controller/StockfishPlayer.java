package it.matlice.matlichess.controller;

import it.matlice.matlichess.GameState;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.ConfigurationPanel;
import it.matlice.matlichess.view.PieceView;
import it.matlice.stockfish.Stockfish;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cpu JNI Stockfish player implementation.
 */
public class StockfishPlayer implements PlayerInterface {
    private int depth;
    private int skill;
    private Thread t;

    private int delay = 200;

    public StockfishPlayer(int depth, int skill){
        this.depth = depth;
        this.skill = skill;
        Stockfish.getInstance();
    }

    @Override
    public void setColor(PieceColor color) {
        return;
    }

    @Override
    public List<Location> waitForUserMove() throws InterruptedException {
        Stockfish.nSetOption("Use NNUE", String.valueOf(false)); // true or false // use neural network
        Stockfish.nSetOption("Contempt", String.valueOf(-100)); // -100, 100  // lower prefers draw
        Stockfish.nSetOption("Skill Level", String.valueOf(this.skill)); // 0, 20 // skill level, 0 is tough tho
        Stockfish.nSearchBestMove(depth, false);
        final String[] move = {null};

        t = new Thread(() -> {
            move[0] = Stockfish.nGetFoundNextMoveStr();
        });
        t.start();
        t.join();
        t = null;
        if(move[0] == null) throw new InterruptedException();
        Stockfish.nGetScore(true);
        if (move[0].length() == 5) {
            String promotion = move[0].substring(4, 5);
            Game.getInstance().setPromotion(promotion);
        }
        Thread.sleep(delay);
        return Location.fromExtendedMove(move[0].substring(0, 4));
    }

    @Override
    public void setPosition(ArrayList<PieceView> pieces) {
        if(Game.hasInstance()) {
            Stockfish.nSetPosition(Game.getInstance().getPositionFen());
        }
//        Stockfish.nDbgDisplay();
    }

    @Override
    public void setMove(Location from, Location to) {
        return;
    }

    @Override
    public void setTurn(PieceColor turn) {
    }

    @Override
    public void interrupt() {
        if(this.t != null) {
            try {
                t.join(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isInteractive() {
        return false;
    }

    @Override
    public boolean setState(GameState state, boolean generic, PlayerInterface opponent) {
        return opponent.setState(state, generic, true);
    }

    @Override
    public boolean setState(GameState state, boolean generic, Boolean other_result) {
        return other_result;
    }

    public static ConfigurationPanel getConfigurationInterface(){
        return new ConfigurationPanel(){
            private JSpinner depth;
            private JSpinner skill;
            @Override
            public PlayerInterface getInstance() {
                return new StockfishPlayer( (Integer) this.depth.getValue(), (Integer) this.skill.getValue());
            }

            @Override
            public void buildPanel() {
                SpinnerModel depthModel = new SpinnerNumberModel(12, 1, Integer.MAX_VALUE, 1);
                SpinnerModel skillModel = new SpinnerNumberModel(12, 1, 20, 1);
                this.depth = new JSpinner(depthModel);
                this.depth.setPreferredSize( new Dimension( 70, 24 ) );
                this.skill = new JSpinner(skillModel);
                this.skill.setPreferredSize( new Dimension( 70, 24 ) );
                this.add(new Label("Depth:"));
                this.add(depth);
                this.add(new Label("Skill (1-20):"));
                this.add(skill);
            }
        };
    }

    public static String getName() {return "Stockfish CPU";}

}
