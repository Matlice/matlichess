package it.matlice.matlichess.view;

import it.matlice.matlichess.controller.PlayerInterface;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the representative of the view in the MCV architecture.
 */
public class View {

    private JFrame frame;
    private ChessboardView chessboardView;

    private static View instance = null;

    //the instance is a singleton
    public static View getInstance() {
        if(instance == null) instance = new View();
        return instance;
    }

    private View() {
        this.frame = new JFrame();
        this.chessboardView = new ChessboardView(frame);
    }

    /**
     * creates the main frame and adds the {@link ChessboardView} to it
     */
    public void initialize(){
        EventQueue.invokeLater(() -> {
            frame.setBounds(20, 20, 800, 800);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setResizable(false);
            frame.setTitle("MatliChess");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(chessboardView);
            frame.pack();
            frame.setVisible(true);
        });
    }

    public PlayerInterface getPlayerInterface(){
        return this.chessboardView;
    }
}
