package it.matlice.matlichess.view;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class View {

   private ChessboardView chessboardView = new ChessboardView();

    public void initialize(){
        new Thread(() -> {
            JFrame frame = new JFrame();
            frame.setBounds(20, 20, 800, 800);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setResizable(false);
            frame.setTitle("MatliChess");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(chessboardView);
            frame.pack();
        }).start();
    }

    public List<Location> waitForUserMove(PieceColor side){
        try {
            return chessboardView.waitForUserMove(side);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPosition(ArrayList<PieceView> pieces) {
        this.chessboardView.setPosition(pieces);
    }

    public void setTurn(PieceColor turn) {
        this.chessboardView.setTurn(turn);
    }
}
