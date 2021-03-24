package it.matlice.matlichess.view;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.controller.PlayerInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View {

    private JFrame frame;
    private ChessboardView chessboardView;

    public View() {
        this.frame = new JFrame();
        this.chessboardView = new ChessboardView(frame);
    }

    public PlayerInterface initialize(){
        EventQueue.invokeLater(() -> {
            frame.setBounds(20, 20, 800, 800);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setResizable(false);
            frame.setTitle("MatliChess");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(chessboardView);
            frame.pack();
        });
        return chessboardView;
    }

    public void setPosition(ArrayList<PieceView> pieces) {
        this.chessboardView.setPosition(pieces);
    }

    public void setTurn(PieceColor turn) {
        this.chessboardView.setTurn(turn);
    }
}
