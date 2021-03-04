package it.matlice.matlichess.view;

import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class View {

   private ChessboardView chessboardView = new ChessboardView();

    public void initialize(){
        new Thread(() -> {
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 800, 800);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setResizable(false);
            frame.setTitle("MatliChess");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(chessboardView);
            frame.pack();
        }).start();
    }

    public List<Location> waitForUserMove(Color side){
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
//
//    public void makeMove(int from_col, int from_row, int to_col, int to_row) {
//        chessboardView.makeMove(from_col, from_row, to_col, to_row);
//    }

}
