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

public class StockfishPlayer implements PlayerInterface {
    private int depth;
    private int skill;

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
    public void setMove(Location from, Location to) {
        return;
    }

    @Override
    public void setTurn(PieceColor turn) {
    }

    @Override
    public void interrupt() {
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
            private JTextField depth;
            private JTextField skill;
            @Override
            public PlayerInterface getInstance() {
                return new StockfishPlayer(Integer.parseInt(depth.getText()), Integer.parseInt(skill.getText()));
            }

            @Override
            public void buildPanel() {
                this.depth = new JTextField();
                this.skill = new JTextField();
                depth.setPreferredSize( new Dimension( 200, 24 ) );
                skill.setPreferredSize( new Dimension( 200, 24 ) );
                this.add(new Label("Depth:"));
                this.add(depth);
                this.depth.setText("8");
                this.add(new Label("Skill (1-20):"));
                this.add(skill);
                this.skill.setText("10");
            }
        };
    }

    public static String getName() {return "Stockfish CPU";}

}
