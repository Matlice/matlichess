package it.matlice.matlichess.view;

import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class View {

    private ChessboardView chessboardView = new ChessboardView();

    public void initialize(){
        new Thread(() -> {
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 800, 800);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setTitle("MatliChess");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(chessboardView);
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

    public void makeMove(Location frm, Location to){
        chessboardView.makeMove(frm, to);
    }
}
