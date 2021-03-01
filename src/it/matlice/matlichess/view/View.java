package it.matlice.matlichess.view;

import javax.swing.*;

public class View {

    public View() {
        initialize();
    }

    ChessboardView chessboardView = new ChessboardView();

    private void initialize(){
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("MatliChess");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chessboardView);
    }


}
